package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.constants.ApiConstants
import com.andreikslpv.data.constants.FirestoreConstants
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CardsFirebaseDataSource @Inject constructor(
    private val database: FirebaseFirestore,
) : CardsCacheDataSource {

    override fun getCardsByIds(ids: List<String>) : PagingSource<QuerySnapshot, CardDataModel> {
        val query = database.collection(FirestoreConstants.PATH_CARDS)
            .whereIn("id", ids)
            .orderBy("name")
            .limit(ApiConstants.DEFAULT_PAGE_SIZE.toLong())
        return CardsFirebasePagingSource(query)
    }

    override suspend fun saveCardsToDb(cards: List<CardDataModel>) {
        val collection = database.collection(FirestoreConstants.PATH_CARDS)
        var document: DocumentReference
        cards.forEach {card->
            document = collection.document(card.id)
            document.set(card).await()
        }
    }
}