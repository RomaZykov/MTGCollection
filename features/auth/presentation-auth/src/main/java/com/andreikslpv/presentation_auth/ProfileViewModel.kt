package com.andreikslpv.presentation_auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_auth.entities.AccountDataEntity
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.andreikslpv.domain_auth.repositories.AuthRouter
import com.andreikslpv.domain_auth.usecase.profile.DeleteUserUseCase
import com.andreikslpv.domain_auth.usecase.profile.TryToChangeCollectionStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val authExternalRepository: AuthExternalRepository,
    private val router: AuthRouter,
) : ViewModel() {

    private val _currentUser =
        MutableLiveData<AccountDataEntity?>(authRepository.getCurrentUser())
    val currentUser: LiveData<AccountDataEntity?> = _currentUser

    private fun refreshCurrentUser() {
        _currentUser.postValue(authRepository.getCurrentUser())
    }

    fun getCardHistory() = liveData(Dispatchers.IO) {
        authExternalRepository.getHistory().collect { response ->
            emit(response)
        }
    }

    fun tryToChangeCollectionStatus(card: CardModel) =
        tryToChangeCollectionStatusUseCase.execute(card)

    fun signOut() {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.signOut().collect { response ->
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
        authRepository.linkAnonymousWithCredential(idToken).collect { response ->
            Core.loadStateHandler.setLoadState(response)
            emit(response)
        }
    }

    // --------------- all for users photo & name

    fun editUserName(newName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.editUserName(newName).collect { response ->
                withContext(Dispatchers.Main) {
                    refreshCurrentUser()
                    Core.loadStateHandler.setLoadState(response)
                }
            }
        }
    }

    fun changeUserPhoto(localUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.changeUserPhoto(localUri.toString()).collect { response ->
                withContext(Dispatchers.Main) {
                    if (response is Response.Success) refreshCurrentUser()
                    Core.loadStateHandler.setLoadState(response)
                }
            }
        }
    }

    // --------------- routing

    fun launchSettings() = router.launchSettings()

    fun launchDetails(card: CardModel) = router.launchDetails(card)

}