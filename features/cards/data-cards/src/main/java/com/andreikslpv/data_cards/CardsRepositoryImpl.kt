package com.andreikslpv.data_cards

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.common.Response
import com.andreikslpv.data.constants.ApiConstants
import com.andreikslpv.data_cards.datasource.CardsApiPagingSource
import com.andreikslpv.data_cards.datasource.CardsCacheDataSource
import com.andreikslpv.data_cards.datasource.CardsFirebasePagingSource
import com.andreikslpv.data_cards.services.CardsInSetService
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_cards.repositories.CardsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val database: FirebaseFirestore,
    private val cacheDataSource: CardsCacheDataSource,
) : CardsRepository {
    private var isApiAvailable = true

    override fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                if (isApiAvailable) {
                    CardsApiPagingSource(
                        retrofit.create(CardsInSetService::class.java),
                        codeOfSet,
                        object : CardsApiCallback {
                            override fun onSuccess(items: List<CardModel>) {
                                if (isApiAvailable) CoroutineScope(Dispatchers.IO).launch {
                                    cacheDataSource.saveCardsToDb(items)
                                }
                            }

                            override fun onFailure() {}
                        })
                } else {
                    // загружаем данные из кэша и меняем статус доступности апи на true,
                    // чтобы в следующий раз снова сначала была попытка получить данные из апи
                    isApiAvailable = true
                    cacheDataSource.getCardsInSet(codeOfSet)
                }
            }
        ).flow
    }

    override fun getCardsInCollection(uid: String): Flow<PagingData<CardModel>> {
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

    override fun changeApiAvailability(newStatus: Boolean) {
        isApiAvailable = newStatus
    }

    override fun addToCardsCollection(uid: String, card: CardModel) {
        CoroutineScope(Dispatchers.IO).launch {
            database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
                .document(card.id)
                .set(card)
                .await()
        }
    }

    override fun removeFromCardsCollection(uid: String, card: CardModel) {
        CoroutineScope(Dispatchers.IO).launch {
            database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
                .document(card.id)
                .delete()
                .await()
        }
    }

    override fun getCardFromCollection(uid: String, cardId: String) = callbackFlow {
        val cardStateListener = database.collection(FirestoreConstants.PATH_CARDS)
            .document(uid)
            .collection(FirestoreConstants.PATH_COLLECTION)
            .whereEqualTo("id", cardId)
            .addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    val tempList = value.documents.mapNotNull {
                        it.toObject(CardModel::class.java)
                    }
                    if (tempList.isNotEmpty()) trySend(tempList[0]) else trySend(CardModel())
                } else trySend(CardModel())
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