package com.andreikslpv.profile.presentation

import androidx.lifecycle.viewModelScope
import com.andreikslpv.common.Container
import com.andreikslpv.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
//    private val getProfileUseCase: GetProfileUseCase,
//    private val logoutUseCase: LogoutUseCase,
    private val router: ProfileRouter,
) : BaseViewModel() {

//    val profileLiveValue = getProfileUseCase.getProfile()
//        .toLiveValue(Container.Pending)

    fun reload() = debounce {
        //getProfileUseCase.reload()
    }

    fun logout() = debounce {
        viewModelScope.launch {
//            logoutUseCase.logout()
//            router.restartApp()
        }
    }

    fun editUsername() = debounce {
        router.launchEditUsername()
    }

}