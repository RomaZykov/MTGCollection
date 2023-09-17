package com.andreikslpv.profile.presentation

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val router: ProfileRouter,
) : ViewModel() {

    val currentUser = MutableLiveData<AccountFeatureEntity?>(profileRepository.getCurrentUser())

    fun refreshUser() {
        currentUser.postValue(profileRepository.getCurrentUser())
    }


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

    // --------------- all for users photo & name

    fun editUserName(newName: String) = liveData(Dispatchers.IO) {
        profileRepository.editUserName(newName).collect { response ->
            emit(response)
        }
    }

    fun changeUserPhoto(localUri: Uri) = liveData(Dispatchers.IO) {
        profileRepository.changeUserPhoto(localUri).collect { response ->
            emit(response)
        }
    }

    // --------------- routing

    fun launchSettings() = router.launchSettings()

}