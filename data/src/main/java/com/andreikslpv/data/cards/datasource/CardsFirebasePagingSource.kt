package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.constants.ApiConstants
import com.andreikslpv.data.constants.FirestoreConstants
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class CardsFirebasePagingSource(
    private val database: FirebaseFirestore,
    private val ids: List<String>,
) : PagingSource<DocumentSnapshot, CardDataModel>() {

    init {
        println("AAA init")
    }

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, CardDataModel>): DocumentSnapshot? {
        // Самый последний доступный индекс в списке
        val anchorPosition = state.anchorPosition ?: return null
        // преобразуем item index в page index
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        // page не имеет значения "текущее", поэтому вычисляем сами
        return page.prevKey ?: page.nextKey
    }

    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, CardDataModel> {
        println("AAA load $ids")
        try {
            var tempList = listOf<CardDataModel>()
            var nextKey: DocumentSnapshot? = null
            println("AAA load $ids")
            if (ids.isNotEmpty()) {
                val collection = database.collection(FirestoreConstants.PATH_CARDS)
                val result: QuerySnapshot

                if (params.key == null) {
                    result = collection
                        .whereIn("id", ids)
                        .orderBy("name")
                        .limit(ApiConstants.DEFAULT_PAGE_SIZE.toLong())
                        .get()
                        .await()
                    // Get the last visible document
                    nextKey = if (result.size() > 0) result.documents[result.size() - 1]
                    else null
                } else {
                    result = collection
                        .whereIn("id", ids)
                        .orderBy("name")
                        .startAfter(params.key)
                        .limit(ApiConstants.DEFAULT_PAGE_SIZE.toLong())
                        .get()
                        .await()
                    // Get the last visible document
                    nextKey = if (result.size() > 0) result.documents[result.size() - 1]
                    else null
                }

                tempList = result.documents
                    .asSequence()
                    .mapNotNull { it.toObject(CardDataModel::class.java) }
                    .toList()
            }

            return LoadResult.Page(
                data = tempList,
                prevKey = params.key,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            println("AAA error: ${e.message}")
            return LoadResult.Error(e)
        }
    }
}