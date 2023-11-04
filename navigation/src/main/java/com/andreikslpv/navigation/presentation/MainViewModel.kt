package com.andreikslpv.navigation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.common.Core
import com.andreikslpv.navigation.domain.usecase.LaunchMainIfUserAuthenticatedUseCase
import com.andreikslpv.navigation.domain.usecase.SendErrorToCrashlyticsUseCase
import com.andreikslpv.navigation.domain.usecase.StartObserveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val launchMainIfUserAuthenticatedUseCase: LaunchMainIfUserAuthenticatedUseCase,
    private val startObserveUserUseCase: StartObserveUserUseCase,
    private val sendErrorToCrashlyticsUseCase: SendErrorToCrashlyticsUseCase,
) : ViewModel() {

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

    fun sendErrorToCrashlytics(error: Throwable) = sendErrorToCrashlyticsUseCase.execute(error)

}