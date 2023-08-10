package com.andreikslpv.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreikslpv.common.Response
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation.views.visible
import com.andreikslpv.profile.R
import com.andreikslpv.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val dialogAnimDuration = 500L
    private val dialogAnimAlfa = 1f

    private val viewModel by viewModels<ProfileViewModel>()

    private val binding by viewBinding<FragmentProfileBinding>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSignOutButton()
        getAuthState()
        initDeleteUserButton()
    }

    private fun initSignOutButton() {
        binding.signOutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        viewModel.signOut().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> binding.progressBar.show()
                is Response.Success -> binding.progressBar.hide()
                is Response.Failure -> binding.progressBar.hide()
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun getAuthState() {
        viewModel.getAuthState().observe(viewLifecycleOwner) { }
    }


    private fun initDeleteUserButton() {
        binding.deleteUserButton.setOnClickListener {
            showDialog()
        }
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

            actionButton.setOnClickListener {
                deleteUser()
            }
        }
    }

    private fun deleteUser() {
        viewModel.deleteUser().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> binding.progressBar.show()
                is Response.Success -> binding.progressBar.hide()
                is Response.Failure -> binding.progressBar.hide()
            }
        }
    }


}