package com.andreikslpv.profile.presentation

import androidx.lifecycle.liveData
import com.andreikslpv.presentation.BaseViewModel
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val router: ProfileRouter,
) : BaseViewModel() {

    fun signOut() = liveData(Dispatchers.IO) {
        profileRepository.signOut().collect { response ->
            emit(response)
        }
    }

    @ExperimentalCoroutinesApi
    fun getAuthState() = liveData(Dispatchers.Main) {
        profileRepository.getAuthState().collect { isUserSignedOut ->
            if (isUserSignedOut) router.restartApp()
            emit(isUserSignedOut)
        }
    }

    fun deleteUser() = liveData(Dispatchers.IO) {
        profileRepository.deleteUser().collect { response ->
            emit(response)
        }
    }

}