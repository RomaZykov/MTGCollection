package com.andreikslpv.navigation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.andreikslpv.common.Core
import com.andreikslpv.navigation.domain.usecase.LaunchMainIfUserAuthenticatedUseCase
import com.andreikslpv.navigation.domain.usecase.SendErrorToCrashlyticsUseCase
import com.andreikslpv.navigation.domain.usecase.StartObserveUserUseCase
import com.andreikslpv.navigation.domain.usecase.UpdateSetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val launchMainIfUserAuthenticatedUseCase: LaunchMainIfUserAuthenticatedUseCase,
    private val startObserveUserUseCase: StartObserveUserUseCase,
    private val sendErrorToCrashlyticsUseCase: SendErrorToCrashlyticsUseCase,
    private val updateSetsUseCase: UpdateSetsUseCase,
    private val coroutineContext: CoroutineContext,
) : ViewModel() {

    init {
        viewModelScope.launch { updateSetsUseCase() }
    }

    val loadState = liveData(Dispatchers.Main) {
        Core.loadStateHandler.getLoadState().collect { response ->
            emit(response)
        }
    }

    fun launchMainIfUserAuthenticated() = launchMainIfUserAuthenticatedUseCase.execute()

    @ExperimentalCoroutinesApi
    fun getAuthState() = liveData(coroutineContext) {
        startObserveUserUseCase().collect { emit(it) }
    }

    fun sendErrorToCrashlytics(error: Throwable) = sendErrorToCrashlyticsUseCase(error)

}