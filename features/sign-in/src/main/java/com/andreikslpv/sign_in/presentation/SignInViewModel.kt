package com.andreikslpv.sign_in.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.common.Response
import com.andreikslpv.sign_in.domain.usecase.CreateUserUseCase
import com.andreikslpv.sign_in.domain.usecase.SignInAnonymouslyUseCase
import com.andreikslpv.sign_in.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val router: SignInRouter,
) : ViewModel() {

    fun signInWithGoogle() = liveData(Dispatchers.Main) {
        signInUseCase.execute().collect { response ->
            when (response) {
                is Response.Loading -> emit(Response.Loading)
                is Response.Failure -> emit(Response.Failure(response.errorMessage))
                is Response.Success -> createUser(response.data).collect {
                    emit(it)
                }
            }
        }
    }

    fun signInAnonymously() = liveData(Dispatchers.Main) {
        signInAnonymouslyUseCase.execute().collect { response ->
            when (response) {
                is Response.Loading -> emit(Response.Loading)
                is Response.Failure -> emit(Response.Failure(response.errorMessage))
                is Response.Success -> createUser(response.data).collect {
                    emit(it)
                }
            }
        }
    }

    private fun createUser(isNewUser: Boolean) = flow {
        if (isNewUser) {
            createUserUseCase.execute().collect { response2 ->
                when (response2) {
                    is Response.Loading -> emit(Response.Loading)
                    is Response.Failure -> emit(Response.Failure(response2.errorMessage))
                    is Response.Success -> router.launchMain()
                    else -> {}
                }
            }

        } else router.launchMain()
    }

}