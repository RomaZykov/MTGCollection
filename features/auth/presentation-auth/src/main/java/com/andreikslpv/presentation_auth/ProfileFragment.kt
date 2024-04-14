package com.andreikslpv.presentation_auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.domain.usecase.GetCollectionUseCase
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation.visible
import com.andreikslpv.presentation_auth.databinding.FragmentProfileBinding
import com.andreikslpv.presentation_auth.recyclers.CardHistoryRecyclerAdapter
import com.andreikslpv.presentation_auth.utils.StringToIconConverter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel by viewModels<ProfileViewModel>()

    private val binding by viewBinding<FragmentProfileBinding>()

    private lateinit var cardHistoryAdapter: CardHistoryRecyclerAdapter

    @Inject
    lateinit var getCollectionUseCase: GetCollectionUseCase

    @Inject
    lateinit var signInIntent: Intent

    private val signInResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    val googleSignInAccount = task.getResult(ApiException::class.java)
                    googleSignInAccount?.apply {
                        idToken?.let { idToken ->
                            when (viewModel.currentUser.value?.isAnonymous) {
                                true, null -> linkAnonymousWithCredential(idToken)
                                false -> viewModel.deleteUser(idToken)
                            }
                        }
                    }
                } catch (e: ApiException) {
                    //crashlytics.recordException(e)
                }
            }
        }

    // Registers a photo picker activity launcher in single-select mode.
    private val pickMediaResultLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { localUri ->
            // Callback is invoked after the user selects a media item or closes the photo picker.
            if (localUri != null) viewModel.changeUserPhoto(localUri)
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclers()
        initCollectHistory()
        initButtons()
        // --------------- all for users photo & name
        initCurrentUserCollect()
        initEditNameFunction()
        initChangeAvatarFunction()
    }

    private fun initToolbar() {
        binding.profileToolbar.title = getString(R.string.title_profile)
        binding.profileToolbar.menu.findItem(R.id.settingsButton).setOnMenuItemClickListener {
            viewModel.launchSettings()
            true
        }
    }

    private fun initRecyclers() {
        val decorator = SpaceItemDecoration(
            paddingBottomInDp = 16,
            paddingRightInDp = 4,
            paddingLeftInDp = 4,
        )

        binding.profileRecyclerHistory.apply {
            val containerWidthPx =
                requireContext().resources.getDimension(R.dimen.item_set_dimen).toInt()

            cardHistoryAdapter = CardHistoryRecyclerAdapter(
                object : CardItemClickListener {
                    override fun click(card: CardUiEntity) {
                        viewModel.launchDetails(card)
                    }
                },
                object : CardItemClickListener {
                    override fun click(card: CardUiEntity) {
                        viewModel.tryToChangeCollectionStatus(card)
                    }
                },
                containerWidthPx,
            )
            adapter = cardHistoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            //Применяем декоратор для отступов
            addItemDecoration(decorator)
        }
    }

    private fun initCollectHistory() {
        this.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.history.collectLatest { cards ->
                        if (cards.isEmpty()) {
                            binding.profileRecyclerHistory.visible(false)
                            binding.historyEmptyView.visible(true)
                        } else {
                            cardHistoryAdapter.changeItems(cards)
                            binding.profileRecyclerHistory.visible(true)
                            binding.historyEmptyView.visible(false)
                        }
                    }
                }
            }
        }
    }

    private fun initButtons() {
        binding.signOutButton.setOnClickListener { viewModel.signOut() }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.profile_dialog_title))
            .setMessage(getString(R.string.profile_dialog_text))
            .setNegativeButton(getString(R.string.profile_dialog_action_cancel)) { _, _ ->
                // Respond to negative button press
            }
            .setPositiveButton(getString(R.string.profile_dialog_action_auth)) { _, _ ->
                // Respond to positive button press
                signInResultLauncher.launch(signInIntent)
            }
            .show()
    }

    // --------------- all for users photo & name

    private fun initCurrentUserCollect() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            when (user?.isAnonymous) {
                true, null -> {
                    binding.profileAvatar.load(R.drawable.anonim)
                    binding.profileName.setText(R.string.profile_name_anonymous)
                    binding.deleteUserButton.text = getString(R.string.sign_in_with_google_button)
                    binding.deleteUserButton.setOnClickListener {
                        signInResultLauncher.launch(signInIntent)
                    }
                }

                false -> {
                    binding.profileAvatar.load(user.photoUrl)
                    binding.profileName.setText(user.displayName)
                    binding.profileEmail.text = user.email
                    binding.deleteUserButton.text = getString(R.string.profile_delete_user)
                    binding.deleteUserButton.setOnClickListener { showDialog() }
                }
            }
        }
    }

    private fun initEditNameFunction() {
        binding.profilePencil.setOnClickListener {
            binding.profileName.apply {
                isEnabled = true
                requestFocus()
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                selectAll()
            }
        }

        binding.profileName.setOnKeyListener { v, keyCode, event ->
            // if the event is a key down event on the enter button
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                (v as EditText).apply {
                    viewModel.editUserName(text.toString())
                    clearFocus()
                    isCursorVisible = false
                    isEnabled = false
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun initChangeAvatarFunction() {
        binding.profileCamera.setOnClickListener {
            // Launch the photo picker and let the user choose only images.
            pickMediaResultLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun linkAnonymousWithCredential(idToken: String) {
        viewModel.linkAnonymousWithCredential(idToken).observe(viewLifecycleOwner) { response ->
            if (response is Response.Success) {
                response.data?.let { user ->
                    user.email?.let { email ->
                        viewModel.editUserName(email.substringBefore("@"))
                        val uri = StringToIconConverter.convert(requireContext(), user.uid, email)
                        viewModel.changeUserPhoto(uri)
                    }
                }
            }
        }
    }

}