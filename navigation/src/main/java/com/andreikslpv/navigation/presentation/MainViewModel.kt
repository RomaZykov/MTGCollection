package com.andreikslpv.navigation.presentation

import androidx.lifecycle.liveData
import com.andreikslpv.common.Core
import com.andreikslpv.navigation.domain.usecase.LaunchMainIfUserAuthenticatedUseCase
import com.andreikslpv.navigation.domain.usecase.StartObserveUserUseCase
import com.andreikslpv.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val launchMainIfUserAuthenticatedUseCase: LaunchMainIfUserAuthenticatedUseCase,
    private val startObserveUserUseCase: StartObserveUserUseCase,
) : BaseViewModel() {

    val loadState = liveData(Dispatchers.Main) {
        Core.loadStateHandler.getLoadState().collect { response ->
            emit(response)
        }
    }

    fun launchMainIfUserAuthenticated() = launchMainIfUserAuthenticatedUseCase.execute()

    @ExperimentalCoroutinesApi
    fun getAuthState() = liveData(Dispatchers.IO) {
        startObserveUserUseCase.execute().collect { emit(it) }
    }

}