package com.andreikslpv.navigation.presentation

import androidx.lifecycle.liveData
import com.andreikslpv.common.Core
import com.andreikslpv.navigation.domain.repositories.MainRepository
import com.andreikslpv.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: MainRouter,
    private val mainRepository: MainRepository,
) : BaseViewModel() {

    val isUserAuthenticated get() = mainRepository.isUserAuthenticatedInFirebase()

    val loadState = liveData(Dispatchers.Main) {
        Core.loadStateHandler.getLoadState().collect { response ->
            emit(response)
        }
    }

    @ExperimentalCoroutinesApi
    fun getAuthState() = liveData(Dispatchers.Main) {
        mainRepository.getAuthState().collect { isUserSignedOut ->
            if (isUserSignedOut) router.restartApp()
            emit(isUserSignedOut)
        }
    }

    fun launchMain() = router.launchMain()

    fun startObserveUser() {
        mainRepository.startObserveUser()
    }

}