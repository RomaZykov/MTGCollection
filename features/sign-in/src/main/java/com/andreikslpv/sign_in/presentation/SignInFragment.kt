package com.andreikslpv.sign_in.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreikslpv.common.Response
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.sign_in.R
import com.andreikslpv.sign_in.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>()

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        binding.anonymousButton.setOnClickListener {
            signInAnonymously()
        }

        collectPrivacyPolicy()
    }

    private fun signInWithGoogle() {
        viewModel.signInWithGoogle().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> binding.progressBar.show()
                is Response.Success<*> -> binding.progressBar.hide()
                is Response.Failure -> binding.progressBar.hide()
            }
        }
    }

    private fun signInAnonymously() {
        viewModel.signInAnonymously().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> binding.progressBar.show()
                is Response.Success<*> -> binding.progressBar.hide()
                is Response.Failure -> binding.progressBar.hide()

            }
        }
    }

    private fun collectPrivacyPolicy() {
        viewModel.getPrivacyPolicy().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> binding.progressBar.show()
                is Response.Success -> {
                    if (response.data.isNotBlank()) {
                        binding.authCopyrightText.setOnClickListener {
                            val i = Intent(Intent.ACTION_VIEW, Uri.parse(response.data))
                            startActivity(i)
                        }
                    }
                    binding.progressBar.hide()
                }
                is Response.Failure -> binding.progressBar.hide()
            }
        }
    }

}