package com.andreikslpv.mtgcollection.glue.core

import com.andreikslpv.domain.entities.CardPreviewEntity
import com.andreikslpv.domain.repositories.CoreExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import com.andreikslpv.domain_users.UsersRepository
import javax.inject.Inject

class CoreExternalRepositoryImpl @Inject constructor(
    private val usersRepository: UsersRepository,
    private val authRepository: AuthRepository,
    private val cardsRepository: CardsRepository,
): CoreExternalRepository {

    override fun getCurrentUserUid() = authRepository.getCurrentUser()?.uid

    override fun getCollection() = usersRepository.getCollection()

    override suspend fun addToUsersCollection(uid: String, cardId: String) =
        usersRepository.addToCollection(uid, cardId)

    override suspend fun removeFromUsersCollection(uid: String, cardId: String) =
        usersRepository.removeFromCollection(uid, cardId)

    override suspend fun addToCardsCollection(uid: String, card: CardPreviewEntity) =
        cardsRepository.addToCardsCollection(uid, card)

    override suspend fun removeFromCardsCollection(uid: String, card: CardPreviewEntity) =
        cardsRepository.removeFromCardsCollection(uid, card)
}