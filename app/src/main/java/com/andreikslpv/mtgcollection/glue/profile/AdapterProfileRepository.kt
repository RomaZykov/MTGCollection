package com.andreikslpv.mtgcollection.glue.profile

import android.net.Uri
import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.data.auth.AuthDataRepository
import com.andreikslpv.data.cards.CardsDataRepository
import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.mtgcollection.glue.cards.AccountDataToFeatureModelMapper
import com.andreikslpv.mtgcollection.glue.cards.CardFeatureToDataModelMapper
import com.andreikslpv.mtgcollection.glue.cards.CardsListDataToFeatureModelMapper
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val authDataRepository: AuthDataRepository,
    private val usersDataRepository: UsersDataRepository,
    private val cardsDataRepository: CardsDataRepository,
) : ProfileRepository {

    override fun signOut() = authDataRepository.signOut()

    override fun getAuthState() = authDataRepository.getAuthState()

    override suspend fun deleteUserInDb(uid: String) = usersDataRepository.deleteUserInDb(uid)

    override fun removeAllFromCollection(uid: String) =
        cardsDataRepository.removeAllFromCollection(uid)

    override suspend fun deleteUsersPhotoInDb(uid: String) =
        authDataRepository.deleteUsersPhotoInDb(uid)

    override suspend fun deleteUserInAuth(idToken: String) =
        authDataRepository.deleteUserInAuth(idToken)

    override fun getCurrentUser() =
        AccountDataToFeatureModelMapper.map(authDataRepository.getCurrentUser().value)

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