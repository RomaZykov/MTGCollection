package com.andreikslpv.mtgcollection.glue.auth

import com.andreikslpv.data.cards.CardsDataRepository
import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.domain.entities.CardFeatureModel
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.mtgcollection.glue.cards.CardFeatureToDataModelMapper
import com.andreikslpv.mtgcollection.glue.cards.CardsListDataToFeatureModelMapper
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AdapterAuthExternalRepository @Inject constructor(
    private val usersDataRepository: UsersDataRepository,
    private val cardsDataRepository: CardsDataRepository,
) : AuthExternalRepository {

    override suspend fun createUser(uid: String) = usersDataRepository.createUserInDb(uid)

    override suspend fun deleteUserInDb(uid: String) = usersDataRepository.deleteUserInDb(uid)

    override fun removeAllFromCollection(uid: String) =
        cardsDataRepository.removeAllFromCollection(uid)

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

}