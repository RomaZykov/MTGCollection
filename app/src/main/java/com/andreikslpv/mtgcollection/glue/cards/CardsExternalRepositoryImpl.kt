package com.andreikslpv.mtgcollection.glue.cards

import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_users.UsersRepository
import javax.inject.Inject

class CardsExternalRepositoryImpl @Inject constructor(
    private val usersRepository: UsersRepository,
    private val authRepository: AuthRepository,
) : CardsExternalRepository {

    override fun getCurrentUserUid() = authRepository.getCurrentUser()?.uid

    override fun getCollection() = usersRepository.getCollection()

    override suspend fun addToUsersCollection(uid: String, cardId: String) =
        usersRepository.addToCollection(uid, cardId)

    override suspend fun removeFromUsersCollection(uid: String, cardId: String) =
        usersRepository.removeFromCollection(uid, cardId)

    override fun getHistory() = usersRepository.getHistory()

    override suspend fun setHistory(uid: String, newHistory: List<CardEntity>) =
        usersRepository.setHistory(uid, newHistory)
}