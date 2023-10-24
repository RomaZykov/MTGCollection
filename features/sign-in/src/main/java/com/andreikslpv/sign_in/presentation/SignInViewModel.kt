package com.andreikslpv.sign_in.presentation

import androidx.lifecycle.ViewModel
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.sign_in.domain.usecase.CreateUserUseCase
import com.andreikslpv.sign_in.domain.usecase.GetPrivacyPolicyUseCase
import com.andreikslpv.sign_in.domain.usecase.SignInAnonymouslyUseCase
import com.andreikslpv.sign_in.domain.usecase.SignInUseCase
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
    private val router: SignInRouter,
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