package com.andreikslpv.data_cards

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.data.ApiConstants
import com.andreikslpv.data.ApiConstants.INITIAL_PAGE_SIZE
import com.andreikslpv.data.CardFirebaseEntity
import com.andreikslpv.data_cards.datasource.CardsFirebasePagingSource
import com.andreikslpv.datasource_room_cards.CardsDao
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_cards.entities.CardFilters
import com.andreikslpv.domain_cards.repositories.CardsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val remoteMediatorFactory: CardsRemoteMediator.Factory,
    private val cardsDao: CardsDao,
    private val database: FirebaseFirestore,
) : CardsRepository {

    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalPagingApi::class)
    override fun getCardsInSet(filters: CardFilters) = Pager(
        config = PagingConfig(
            pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_PAGE_SIZE
        ),
        remoteMediator = remoteMediatorFactory.create(filters),
        pagingSourceFactory = {
            cardsDao.getPagingSource(
                filters.codeOfSet.lowercase(Locale.ROOT),
                filters.lang.cardLang,
                filters.sortsType.columnRoom,
                filters.sortsTypeDir.dirRoom
            )
        }
    )
        .flow
        .map { it as PagingData<CardEntity> }
        .flowOn(Dispatchers.IO)

    override fun getCardsInCollection(uid: String) = Pager(
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
        }
    )
        .flow
        .flowOn(Dispatchers.IO)

    override suspend fun addToCardsCollection(uid: String, card: CardEntity): Unit =
        withContext(Dispatchers.IO) {
            database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
                .document(card.id)
                .set(CardFirebaseEntity(card))
                .await()
        }

    override suspend fun removeFromCardsCollection(uid: String, card: CardEntity): Unit =
        withContext(Dispatchers.IO) {
            database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
                .document(card.id)
                .delete()
                .await()
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
                        if (tempList.isNotEmpty()) trySend(tempList[0])
                        else trySend(CardFirebaseEntity())
                    } else trySend(CardFirebaseEntity())
                }
            awaitClose { cardStateListener.remove() }
        }
            .flowOn(Dispatchers.IO)

    // TODO переписать с использованием команды сервера
    override suspend fun removeAllFromCollection(uid: String): Unit = withContext(Dispatchers.IO) {
        database.collection(FirestoreConstants.PATH_CARDS)
            .document(uid)
            .delete()
            .addOnFailureListener { }
    }
}