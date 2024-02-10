package com.andreikslpv.presentation_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain_auth.repositories.AuthRouter
import com.andreikslpv.domain_auth.usecase.signin.CreateUserUseCase
import com.andreikslpv.domain_auth.usecase.signin.GetPrivacyPolicyUseCase
import com.andreikslpv.domain_auth.usecase.signin.SignInAnonymouslyUseCase
import com.andreikslpv.domain_auth.usecase.signin.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val getPrivacyPolicyUseCase: GetPrivacyPolicyUseCase,
    private val router: AuthRouter,
    private val coroutineContext: CoroutineContext,
) : ViewModel() {

    var privacyPolicyUrl = ""

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch(coroutineContext) {
            signInUseCase(idToken).collect { response ->
                if (response is Response.Success) createUser(response.data)
                Core.loadStateHandler.setLoadState(response)
            }
        }
    }

    fun signInAnonymously() {
        viewModelScope.launch(coroutineContext) {
            signInAnonymouslyUseCase().collect { response ->
                if (response is Response.Success) createUser(response.data)
                Core.loadStateHandler.setLoadState(response)
            }
        }
    }

    private fun createUser(isNewUser: Boolean) {
        if (isNewUser) {
            viewModelScope.launch(coroutineContext) {
                createUserUseCase().collect { response ->
                    withContext(Dispatchers.Main) {
                        if (response is Response.Success) router.launchMain()
                        Core.loadStateHandler.setLoadState(response)
                    }
                }
            }
        } else router.launchMain()
    }

    fun startCollectPrivacyPolicy() {
        viewModelScope.launch(coroutineContext) {
            getPrivacyPolicyUseCase().collect { response ->
                if (response is Response.Success) privacyPolicyUrl = response.data
            }
        }
    }

}