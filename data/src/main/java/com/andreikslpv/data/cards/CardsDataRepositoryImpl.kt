package com.andreikslpv.data.cards

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.data.cards.datasource.CardsApiPagingSource
import com.andreikslpv.data.cards.datasource.CardsCacheDataSource
import com.andreikslpv.data.cards.datasource.CardsFirebasePagingSource
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.cards.services.CardsInSetService
import com.andreikslpv.data.constants.ApiConstants
import com.andreikslpv.data.constants.FirestoreConstants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import javax.inject.Inject

class CardsDataRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val database: FirebaseFirestore,
    private val cacheDataSource: CardsCacheDataSource,
) : CardsDataRepository {
    private var isApiAvailable = true

    override fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardDataModel>> {
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
                            override fun onSuccess(items: List<CardDataModel>) {
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

    override fun getCardsInCollection(uid: String): Flow<PagingData<CardDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
//                val collection =
//                    if (uid.isNotBlank()) database.collection(uid) else database.collection("invalid")
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

    override fun addToCollection(uid: String, card: CardDataModel) {
        CoroutineScope(Dispatchers.IO).launch {
            val collection = database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
            val document = collection.document(card.id)
            document.set(card).await()


//            val collection = database.collection(uid)
//            val document = collection.document(card.id)
//            document.set(card).await()
        }
    }

    override fun removeFromCollection(uid: String, card: CardDataModel) {
        CoroutineScope(Dispatchers.IO).launch {
            val collection = database.collection(FirestoreConstants.PATH_CARDS)
                .document(uid)
                .collection(FirestoreConstants.PATH_COLLECTION)
            val document = collection.document(card.id)
            document.delete().await()
//            val collection = database.collection(uid)
//            val document = collection.document(card.id)
//            document.delete().await()
        }
    }

    override fun removeAllFromCollection(uid: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = database.collection(FirestoreConstants.PATH_CARDS).document(uid)
//            user.update(FirestoreConstants.FIELD_COLLECTION, arrayListOf<String>())
//        }
    }
}