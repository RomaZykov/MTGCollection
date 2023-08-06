package com.andreikslpv.sign_in.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.common.Response
import com.andreikslpv.sign_in.domain.usecase.CreateUserUseCase
import com.andreikslpv.sign_in.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val router: SignInRouter,
) : ViewModel() {

    fun signInWithGoogle() = liveData(Dispatchers.Main) {
        signInUseCase.execute().collect { response ->
            when (response) {
                is Response.Loading -> emit(Response.Loading)
                is Response.Failure -> emit(Response.Failure(response.errorMessage))
                is Response.Success -> {
                    val isNewUser = response.data
                    if (isNewUser) {
                        println("AAA signInWithGoogle isNewUser")
                        createUserUseCase.execute().collect { response2 ->
                            when (response2) {
                                is Response.Loading -> emit(Response.Loading)
                                is Response.Failure -> emit(Response.Failure(response2.errorMessage))
                                is Response.Success -> router.launchMain()
                            }
                        }
                    }
                    else router.launchMain()
                }
            }
        }
    }

    fun anonymousLogin() {
        router.launchMain()
    }

}