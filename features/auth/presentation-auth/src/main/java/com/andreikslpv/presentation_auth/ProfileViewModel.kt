package com.andreikslpv.presentation_auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.domain_auth.entities.AccountDataEntity
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.andreikslpv.domain_auth.repositories.AuthRouter
import com.andreikslpv.domain_auth.usecase.profile.DeleteUserUseCase
import com.andreikslpv.domain_auth.usecase.profile.GetCollectionUseCase
import com.andreikslpv.domain_auth.usecase.profile.TryToChangeCollectionStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    authExternalRepository: AuthExternalRepository,
    getCollectionUseCase: GetCollectionUseCase,
    private val router: AuthRouter,
) : ViewModel() {

    private val _currentUser =
        MutableLiveData<AccountDataEntity?>(authRepository.getCurrentUser())
    val currentUser: LiveData<AccountDataEntity?> = _currentUser

    private fun refreshCurrentUser() {
        _currentUser.postValue(authRepository.getCurrentUser())
    }

    val history = combine(
        authExternalRepository.getHistory(),
        getCollectionUseCase.execute(),
        ::merge
    )

    private fun merge(
        history: List<CardEntity>,
        collection: List<String>,
    ): List<CardUiEntity> {
        return history.map { card ->
            CardUiEntity(
                card = card,
                isInCollection = collection.contains(card.id)
            )
        }
    }

    fun tryToChangeCollectionStatus(card: CardUiEntity) =
        tryToChangeCollectionStatusUseCase.execute(card as CardEntity)

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

    fun launchDetails(card: CardUiEntity) = router.launchDetails(card)

}