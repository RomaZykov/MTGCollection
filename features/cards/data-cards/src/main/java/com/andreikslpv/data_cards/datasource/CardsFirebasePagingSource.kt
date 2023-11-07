package com.andreikslpv.data_cards.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.db.entities.CardFirebaseEntity
import com.andreikslpv.domain.entities.CardEntity
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class CardsFirebasePagingSource(private val query: Query) :
    PagingSource<QuerySnapshot, CardEntity>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, CardEntity>) = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, CardEntity> {
        return try {
            val currentPage = params.key ?: query.get().await()
            val lastVisibleCard =
                if (currentPage.size() > 0) currentPage.documents[currentPage.size() - 1] else null
            val nextPage =
                if (lastVisibleCard != null) query.startAfter(lastVisibleCard).get()
                    .await() else null
            LoadResult.Page(
                data = currentPage.toObjects(CardFirebaseEntity::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}