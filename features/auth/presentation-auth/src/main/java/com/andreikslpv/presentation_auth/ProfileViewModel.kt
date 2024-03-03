package com.andreikslpv.presentation_auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.domain.usecase.GetCollectionUseCase
import com.andreikslpv.domain.usecase.TryToChangeCollectionStatusUseCase
import com.andreikslpv.domain_auth.entities.AccountDataEntity
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.andreikslpv.domain_auth.repositories.AuthRouter
import com.andreikslpv.domain_auth.usecase.profile.DeleteUserUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    authExternalRepository: AuthExternalRepository,
    getCollectionUseCase: GetCollectionUseCase,
    private val router: AuthRouter,
    private val googleSignInClient: GoogleSignInClient,
    private val coroutineContext: CoroutineContext,
) : ViewModel() {

    private val _currentUser =
        MutableLiveData<AccountDataEntity?>(authRepository.getCurrentUser())
    val currentUser: LiveData<AccountDataEntity?> = _currentUser

    private fun refreshCurrentUser() {
        _currentUser.postValue(authRepository.getCurrentUser())
    }

    val history = combine(
        authExternalRepository.getHistory(),
        getCollectionUseCase(),
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

    fun tryToChangeCollectionStatus(card: CardUiEntity) {
        viewModelScope.launch(coroutineContext) {
            tryToChangeCollectionStatusUseCase(card as CardEntity)
        }
    }

    fun signOut() {
        googleSignInClient.signOut()
        viewModelScope.launch(coroutineContext) {
            authRepository.signOut().collect { response ->
                Core.loadStateHandler.setLoadState(response)
            }
        }
    }

    fun deleteUser(idToken: String) {
        googleSignInClient.signOut()
        viewModelScope.launch(coroutineContext) {
            deleteUserUseCase(idToken).collect { response ->
                Core.loadStateHandler.setLoadState(response)
            }
        }
    }

    fun linkAnonymousWithCredential(idToken: String) = liveData(coroutineContext) {
        authRepository.linkAnonymousWithCredential(idToken).collect { response ->
            Core.loadStateHandler.setLoadState(response)
            emit(response)
        }
    }

    // --------------- all for users photo & name

    fun editUserName(newName: String) {
        viewModelScope.launch(coroutineContext) {
            authRepository.editUserName(newName).collect { response ->
                refreshCurrentUser()
                Core.loadStateHandler.setLoadState(response)
            }
        }
    }

    fun changeUserPhoto(localUri: Uri) {
        viewModelScope.launch(coroutineContext) {
            authRepository.changeUserPhoto(localUri.toString()).collect { response ->
                if (response is Response.Success) refreshCurrentUser()
                Core.loadStateHandler.setLoadState(response)
            }
        }
    }

    // --------------- routing

    fun launchSettings() = router.launchSettings()

    fun launchDetails(card: CardUiEntity) = router.launchDetails(card)

}