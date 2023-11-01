package com.andreikslpv.mtgcollection.glue.auth

import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import com.andreikslpv.domain_users.UsersRepository
import javax.inject.Inject

class AdapterAuthExternalRepository @Inject constructor(
    private val usersRepository: UsersRepository,
    private val cardsRepository: CardsRepository,
) : AuthExternalRepository {

    override suspend fun createUser(uid: String) = usersRepository.createUserInDb(uid)

    override suspend fun deleteUserInDb(uid: String) = usersRepository.deleteUserInDb(uid)

    override fun removeAllFromCollection(uid: String) =
        cardsRepository.removeAllFromCollection(uid)

    override fun getCollection() = usersRepository.getCollection()

    override fun getHistory() = usersRepository.getHistory()

    override fun addToUsersCollection(uid: String, cardId: String) =
        usersRepository.addToCollection(uid, cardId)

    override fun removeFromUsersCollection(uid: String, cardId: String) =
        usersRepository.removeFromCollection(uid, cardId)

    override fun addToCardsCollection(uid: String, card: CardModel) =
        cardsRepository.addToCardsCollection(uid, card)

    override fun removeFromCardsCollection(uid: String, card: CardModel) =
        cardsRepository.removeFromCardsCollection(uid, card)

}