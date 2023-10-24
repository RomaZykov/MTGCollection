package com.andreikslpv.sign_in.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.sign_in.R
import com.andreikslpv.sign_in.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>()

    private val viewModel by viewModels<SignInViewModel>()

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
                            viewModel.signInWithGoogle(idToken)
                        }
                    }
                } catch (e: ApiException) {
                    //crashlytics.recordException(e)
                }
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startCollectPrivacyPolicy()
        initButtons()
    }

    private fun initButtons() {
        binding.googleSignInButton.setOnClickListener { resultLauncher.launch(signInIntent) }
        binding.anonymousButton.setOnClickListener { viewModel.signInAnonymously() }
        binding.authCopyrightText.setOnClickListener {
            if (viewModel.privacyPolicyUrl.isNotBlank()) {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.privacyPolicyUrl))
                startActivity(i)
            }
        }
    }

}