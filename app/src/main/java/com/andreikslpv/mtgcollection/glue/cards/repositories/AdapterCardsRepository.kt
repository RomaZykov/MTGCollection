package com.andreikslpv.mtgcollection.glue.cards.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.repositories.CardsRepository
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import com.andreikslpv.data.auth.AuthDataRepository
import com.andreikslpv.data.cards.CardsDataRepository
import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.mtgcollection.glue.cards.CardDataToFeatureModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterCardsRepository @Inject constructor(
    private val cardsDataRepository: CardsDataRepository,
    private val usersDataRepository: UsersDataRepository,
    private val authDataRepository: AuthDataRepository,
) : CardsRepository {

    override fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardFeatureModel>> {
        return cardsDataRepository.getCardsInSet(codeOfSet).map { pagingData ->
            pagingData.map {
                CardDataToFeatureModelMapper.map(it)
            }
        }
    }

    override fun getCardsInCollection(ids: List<String>): Flow<PagingData<CardFeatureModel>> {
        return cardsDataRepository.getCardsInCollection(ids).map { pagingData ->
            println("AAA getCardsInCollection pagingData = $pagingData")
            pagingData.map {
                println("AAA getCardsInCollection $it")
                CardDataToFeatureModelMapper.map(it)
            }
        }
    }

    override fun changeApiAvailability(newStatus: Boolean) {
        cardsDataRepository.changeApiAvailability(newStatus)
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

    override fun addToCollection(uid: String, cardId: String) =
        usersDataRepository.addToCollection(uid, cardId)

    override fun removeFromCollection(uid: String, cardId: String) =
        usersDataRepository.removeFromCollection(uid, cardId)
}