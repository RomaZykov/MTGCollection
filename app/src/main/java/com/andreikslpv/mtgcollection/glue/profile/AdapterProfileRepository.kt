package com.andreikslpv.mtgcollection.glue.profile

import android.net.Uri
import com.andreikslpv.common.Constants
import com.andreikslpv.common.Response
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.data.auth.AuthDataRepository
import com.andreikslpv.data.cards.CardsDataRepository
import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.mtgcollection.glue.cards.CardFeatureToDataModelMapper
import com.andreikslpv.mtgcollection.glue.cards.CardsListDataToFeatureModelMapper
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val authDataRepository: AuthDataRepository,
    private val usersDataRepository: UsersDataRepository,
    private val cardsDataRepository: CardsDataRepository,
) : ProfileRepository {

    override fun signOut() = authDataRepository.signOut()

    override fun getAuthState() = authDataRepository.getAuthState()

    override suspend fun deleteUser(idToken: String) = flow {
        try {
            emit(Response.Loading)
            val uid = authDataRepository.getCurrentUser()?.uid ?: ""
            if (uid.isNotBlank()) {
                usersDataRepository.deleteUserInDb(uid).collect { emit(it) }
                cardsDataRepository.removeAllFromCollection(uid).collect { emit(it) }
                authDataRepository.deleteUsersPhotoInDb(uid).collect { emit(it) }
            }
            authDataRepository.deleteUserInAuth(idToken).collect { emit(it) }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_AUTH))
        }
    }

    override fun getCurrentUser(): AccountFeatureEntity? {
        val currentUser = authDataRepository.getCurrentUser()
        return if (currentUser == null) null
        else AccountFeatureEntity(
            uid = currentUser.uid,
            email = currentUser.email,
            displayName = currentUser.displayName,
            photoUrl = currentUser.photoUrl
        )
    }

    override fun getCollection() = usersDataRepository.getCollection()

    override fun getHistory() = MutableStateFlow(
        CardsListDataToFeatureModelMapper.map(usersDataRepository.getHistory().value)
    )

    override fun addToUsersCollection(uid: String, cardId: String) =
        usersDataRepository.addToCollection(uid, cardId)

    override fun removeFromUsersCollection(uid: String, cardId: String) =
        usersDataRepository.removeFromCollection(uid, cardId)

    override fun addToCardsCollection(uid: String, card: CardFeatureModel) =
        cardsDataRepository.addToCollection(uid, CardFeatureToDataModelMapper.map(card))

    override fun removeFromCardsCollection(uid: String, card: CardFeatureModel) =
        cardsDataRepository.removeFromCollection(uid, CardFeatureToDataModelMapper.map(card))

    override suspend fun editUserName(newName: String) = authDataRepository.editUserName(newName)

    override suspend fun changeUserPhoto(localUri: Uri) =
        authDataRepository.changeUserPhoto(localUri)

}