package com.andreikslpv.presentation_auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.andreikslpv.common.AppException
import com.andreikslpv.common.Core
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation_auth.databinding.FragmentSignInBinding
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>()

    private val viewModel by viewModels<SignInViewModel>()

    @Inject
    lateinit var credentialRequest: GetCredentialRequest

    @Inject
    lateinit var credentialManager: CredentialManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startCollectPrivacyPolicy()
        initButtons()
    }

    private fun initButtons() {
        binding.googleSignInButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = credentialRequest,
                        context = requireContext(),
                    )
                    handleSignIn(result)
                } catch (e: GetCredentialException) {
                    Core.errorHandler.handleError(e)
                }
            }
        }
        binding.anonymousButton.setOnClickListener { viewModel.signInAnonymously() }
        binding.authCopyrightText.setOnClickListener {
            if (viewModel.privacyPolicyUrl.isNotBlank()) {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.privacyPolicyUrl))
                startActivity(i)
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential
        if (credential is CustomCredential
            && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                // Use googleIdTokenCredential and extract the ID to validate and
                // authenticate on your server.
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                viewModel.signInWithGoogle(googleIdTokenCredential.idToken)
            } catch (e: GoogleIdTokenParsingException) {
                Core.errorHandler.handleError(e)
            }
        } else {
            // Catch any unrecognized credential type here.
            Core.errorHandler.handleError(AppException("Unexpected type of credential"))
        }
    }
}
