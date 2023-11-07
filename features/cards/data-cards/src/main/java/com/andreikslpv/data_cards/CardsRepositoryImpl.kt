package com.andreikslpv.data_cards

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.common.Response
import com.andreikslpv.data.ApiConstants
import com.andreikslpv.data.db.dao.CardsDao
import com.andreikslpv.data.db.entities.CardFirebaseEntity
import com.andreikslpv.data_cards.datasource.CardsFirebasePagingSource
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_cards.repositories.CardsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val remoteMediatorFactory: CardsRemoteMediator.Factory,
    private val cardsDao: CardsDao,
    private val database: FirebaseFirestore,
) : CardsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.DEFAULT_PAGE_SIZE
            ),
            remoteMediator = remoteMediatorFactory.create(codeOfSet = codeOfSet),
            pagingSourceFactory = { cardsDao.getPagingSource(codeOfSet) }
        )
            .flow
            .map { it as PagingData<CardEntity> }
    }

    override fun getCardsInCollection(uid: String): Flow<PagingData<CardEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                val collection = database.collection(FirestoreConstants.PATH_CARDS)
                    .document(uid)
                    .collection(FirestoreConstants.PATH_COLLECTION)
                val query = collection
                    .orderBy("name")
                    .limit(ApiConstants.DEFAULT_PAGE_SIZE.toLong())
                CardsFirebasePagingSource(query)
            }).flow
    }

    override fun addToCardsCollection(uid: String, card: CardEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
                .document(card.id)
                .set(CardFirebaseEntity(card))
                .await()
        }
    }

    override fun removeFromCardsCollection(uid: String, card: CardEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
                .document(card.id)
                .delete()
                .await()
        }
    }

    override fun getCardFromCollection(uid: String, cardId: String): Flow<CardEntity> =
        callbackFlow {
            val cardStateListener = database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
                .whereEqualTo("id", cardId)
                .addSnapshotListener { value, error ->
                    if (error == null && value != null) {
                        val tempList = value.documents.mapNotNull {
                            it.toObject(CardFirebaseEntity::class.java)
                        }
                        if (tempList.isNotEmpty()) trySend(tempList[0]) else trySend(
                            CardFirebaseEntity()
                        )
                    } else trySend(CardFirebaseEntity())
                }
            awaitClose { cardStateListener.remove() }
        }

    // TODO перписать с использованием команды сервера
    override fun removeAllFromCollection(uid: String) = flow {
        try {
            emit(Response.Loading)
            database.collection(FirestoreConstants.PATH_CARDS).document(uid).delete().await()
                .also { emit(Response.Success(true)) }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}