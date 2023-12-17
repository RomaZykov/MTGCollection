package com.andreikslpv.data_sets.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.domain_sets.entities.SetEntity
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class SetsFirebasePagingSource(private val query: Query) :
    PagingSource<QuerySnapshot, SetEntity>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, SetEntity>) = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, SetEntity> {
        return try {
            val currentPage = params.key ?: query.get().await()
            val lastVisibleCard =
                if (currentPage.size() > 0) currentPage.documents[currentPage.size() - 1] else null
            val nextPage =
                if (lastVisibleCard != null) query.startAfter(lastVisibleCard).get()
                    .await() else null
            LoadResult.Page(
                data = currentPage.toObjects(SetEntity::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}