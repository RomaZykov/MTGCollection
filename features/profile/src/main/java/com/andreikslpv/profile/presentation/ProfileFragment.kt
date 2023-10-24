package com.andreikslpv.profile.presentation

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation.views.visible
import com.andreikslpv.profile.R
import com.andreikslpv.profile.databinding.FragmentProfileBinding
import com.andreikslpv.profile.domain.usecase.GetCollectionUseCase
import com.andreikslpv.profile.presentation.recyclers.CardHistoryRecyclerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val dialogAnimDuration = 500L
    private val dialogAnimAlfa = 1f

    private val viewModel by viewModels<ProfileViewModel>()

    private val binding by viewBinding<FragmentProfileBinding>()

    private lateinit var cardHistoryAdapter: CardHistoryRecyclerAdapter
    private val decorator = SpaceItemDecoration(
        paddingBottomInDp = 16,
        paddingRightInDp = 4,
        paddingLeftInDp = 4,
    )

    @Inject
    lateinit var getCollectionUseCase: GetCollectionUseCase

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var signInIntent: Intent

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    val googleSignInAccount = task.getResult(ApiException::class.java)
                    googleSignInAccount?.apply {
                        idToken?.let { idToken ->
                            viewModel.deleteUser(idToken)
                        }
                    }
                } catch (e: ApiException) {
                    //crashlytics.recordException(e)
                }
            }
        }

    // Registers a photo picker activity launcher in single-select mode.
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { localUri ->
            // Callback is invoked after the user selects a media item or closes the photo picker.
            if (localUri != null) viewModel.changeUserPhoto(localUri)
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclers()
        initRecipeHistoryCollect()
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
        binding.profileRecyclerHistory.apply {
            cardHistoryAdapter = CardHistoryRecyclerAdapter(
                object : CardItemClickListener {
                    override fun click(card: CardFeatureModel) {
                        viewModel.launchDetails(card)
                    }
                },
                object : CardItemClickListener {
                    override fun click(card: CardFeatureModel) {
                        viewModel.tryToChangeCollectionStatus(card)
                    }
                },
                getCollectionUseCase,
                glide
            )
            adapter = cardHistoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            //Применяем декоратор для отступов
            addItemDecoration(decorator)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecipeHistoryCollect() {
        viewModel.getCardHistory().observe(viewLifecycleOwner) { cards ->
            if (cards.isNullOrEmpty()) {
                binding.profileRecyclerHistory.visible(false)
                binding.historyEmptyView.visible(true)
            } else {
                cardHistoryAdapter.changeItems(cards)
                cardHistoryAdapter.notifyDataSetChanged()
                binding.profileRecyclerHistory.visible(true)
                binding.historyEmptyView.visible(false)
            }
        }
    }

    private fun initButtons() {
        binding.signOutButton.setOnClickListener { viewModel.signOut() }
        binding.deleteUserButton.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        binding.profileDialog.apply {
            dialogTitle.text = getString(R.string.profile_dialog_title)
            dialogText.text = getString(R.string.profile_dialog_text)
            actionButton.text = getString(R.string.profile_dialog_action_auth)
            cancelButton.text = getString(R.string.profile_dialog_action_cancel)
            this.visible(true)
            animate()
                .setDuration(dialogAnimDuration)
                .alpha(dialogAnimAlfa)
                .start()
            actionButton.setOnClickListener { resultLauncher.launch(signInIntent) }
        }
    }

    // --------------- all for users photo & name

    private fun initCurrentUserCollect() {
        viewModel.currentUser.observe(viewLifecycleOwner) {
            it?.let { user ->
                if (user.photoUrl != null)
                    Glide.with(binding.profileAvatar)
                        .load(user.photoUrl)
                        .centerCrop()
                        .into(binding.profileAvatar)
                if (!user.displayName.isNullOrBlank())
                    binding.profileName.setText(user.displayName)
                binding.profileEmail.text = user.email
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
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

}