package com.andreikslpv.data_sets.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.ApiConstants
import com.andreikslpv.data_sets.FirestoreConstants
import com.andreikslpv.data_sets.SetsApiCallback
import com.andreikslpv.domain_sets.entities.SetModel
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SetsFirebaseDataSource @Inject constructor(
    private val database: FirebaseFirestore,
) : SetsApiDataSource {

    override fun getSetsByType(
        type: String,
        callback: SetsApiCallback
    ): PagingSource<*, SetModel> {
        val collection = database.collection(FirestoreConstants.PATH_SETS)
        val query = collection
            .whereEqualTo("type", type)
            .orderBy("name")
            .limit(ApiConstants.DEFAULT_PAGE_SIZE.toLong())
        return SetsFirebasePagingSource(query)
    }
}