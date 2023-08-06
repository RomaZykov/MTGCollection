package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.constants.FirestoreConstants
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CardsFirebaseDataSource @Inject constructor(
    private val database: FirebaseFirestore,
) : CardsCacheDataSource {

    override fun getCardsByIds(ids: List<String>): PagingSource<Int, CardDataModel> {
        TODO("Not yet implemented")
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