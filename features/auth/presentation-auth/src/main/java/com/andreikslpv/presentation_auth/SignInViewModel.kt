package com.andreikslpv.presentation_auth

import androidx.lifecycle.ViewModel
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain_auth.repositories.AuthRouter
import com.andreikslpv.domain_auth.usecase.signin.CreateUserUseCase
import com.andreikslpv.domain_auth.usecase.signin.GetPrivacyPolicyUseCase
import com.andreikslpv.domain_auth.usecase.signin.SignInAnonymouslyUseCase
import com.andreikslpv.domain_auth.usecase.signin.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val getPrivacyPolicyUseCase: GetPrivacyPolicyUseCase,
    private val router: AuthRouter,
) : ViewModel() {

    var privacyPolicyUrl = ""

    fun signInWithGoogle(idToken: String) {
        CoroutineScope(Dispatchers.IO).launch {
            signInUseCase.execute(idToken).collect { response ->
                withContext(Dispatchers.Main) {
                    if (response is Response.Success) createUser(response.data)
                    Core.loadStateHandler.setLoadState(response)
                }
            }
        }
    }

    fun signInAnonymously() {
        CoroutineScope(Dispatchers.IO).launch {
            signInAnonymouslyUseCase.execute().collect { response ->
                withContext(Dispatchers.Main) {
                    if (response is Response.Success) createUser(response.data)
                    Core.loadStateHandler.setLoadState(response)
                }
            }
        }
    }

    private fun createUser(isNewUser: Boolean) {
        if (isNewUser) {
            CoroutineScope(Dispatchers.IO).launch {
                createUserUseCase.execute().collect { response ->
                    withContext(Dispatchers.Main) {
                        if (response is Response.Success) router.launchMain()
                        Core.loadStateHandler.setLoadState(response)
                    }
                }
            }
        } else router.launchMain()
    }

    fun startCollectPrivacyPolicy() {
        CoroutineScope(Dispatchers.IO).launch {
            getPrivacyPolicyUseCase.execute().collect { response ->
                if (response is Response.Success) privacyPolicyUrl = response.data
            }
        }
    }

}