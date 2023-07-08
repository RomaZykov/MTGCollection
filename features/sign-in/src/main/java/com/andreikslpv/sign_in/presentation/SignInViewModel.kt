package com.andreikslpv.sign_in.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.common.Response
import com.andreikslpv.sign_in.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val router: SignInRouter,
) : ViewModel() {

    fun signInWithGoogle() = liveData(Dispatchers.Main) {
        signInUseCase.execute().collect { response ->
            if (response is Response.Success)
                router.launchMain()
            emit(response)
        }
    }

    fun signOut() {
        signInUseCase.signOut()
    }

}