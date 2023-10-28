package com.andreikslpv.profile.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import com.andreikslpv.profile.domain.usecase.DeleteUserUseCase
import com.andreikslpv.profile.domain.usecase.TryToChangeCollectionStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val router: ProfileRouter,
) : ViewModel() {

    private val _currentUser =
        MutableLiveData<AccountFeatureEntity?>(profileRepository.getCurrentUser())
    val currentUser: LiveData<AccountFeatureEntity?> = _currentUser

    private fun refreshCurrentUser() {
        _currentUser.postValue(profileRepository.getCurrentUser())
    }

    fun getCardHistory() = liveData(Dispatchers.IO) {
        profileRepository.getHistory().collect { response ->
            emit(response)
        }
    }

    fun tryToChangeCollectionStatus(card: CardFeatureModel) =
        tryToChangeCollectionStatusUseCase.execute(card)

    fun signOut() {
        CoroutineScope(Dispatchers.IO).launch {
            profileRepository.signOut().collect { response ->
                withContext(Dispatchers.Main) {
                    Core.loadStateHandler.setLoadState(response)
                }
            }
        }
    }

    fun deleteUser(idToken: String) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteUserUseCase.execute(idToken).collect { response ->
                withContext(Dispatchers.Main) {
                    Core.loadStateHandler.setLoadState(response)
                }
            }
        }
    }

    fun linkAnonymousWithCredential(idToken: String) = liveData(Dispatchers.IO) {
        profileRepository.linkAnonymousWithCredential(idToken).collect { response ->
            Core.loadStateHandler.setLoadState(response)
            emit(response)
        }
    }

    // --------------- all for users photo & name

    fun editUserName(newName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            profileRepository.editUserName(newName).collect { response ->
                withContext(Dispatchers.Main) {
                    refreshCurrentUser()
                    Core.loadStateHandler.setLoadState(response)
                }
            }
        }
    }

    fun changeUserPhoto(localUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            profileRepository.changeUserPhoto(localUri).collect { response ->
                withContext(Dispatchers.Main) {
                    if (response is Response.Success) refreshCurrentUser()
                    Core.loadStateHandler.setLoadState(response)
                }
            }
        }
    }

    // --------------- routing

    fun launchSettings() = router.launchSettings()

    fun launchDetails(card: CardFeatureModel) = router.launchDetails(card)

}