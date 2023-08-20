package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.cards.entities.CardDataModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class CardsFirebasePagingSource(private val query: Query) :
    PagingSource<QuerySnapshot, CardDataModel>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, CardDataModel>) = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, CardDataModel> {
        return try {
            println("AAA key = ${params.key}")
            val currentPage = params.key ?: query.get().await()
            val lastVisibleProduct =
                if (currentPage.size() > 0) currentPage.documents[currentPage.size() - 1] else null
            val nextPage =
                if (lastVisibleProduct != null) query.startAfter(lastVisibleProduct).get()
                    .await() else null
            LoadResult.Page(
                data = currentPage.toObjects(CardDataModel::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            println("AAA error: ${e.message}")
            return LoadResult.Error(e)
        }
    }
}