package com.andreikslpv.data_sets.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.ApiConstants
import com.andreikslpv.data_sets.FirestoreConstants
import com.andreikslpv.domain_sets.entities.SetEntity
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SetsFirebaseDataSource @Inject constructor(
    private val database: FirebaseFirestore,
) : SetsDataSource {

    override fun getSetsByType(
        type: String
    ): PagingSource<*, SetEntity> {
        val collection = database.collection(FirestoreConstants.PATH_SETS)
        val query = collection
            .whereEqualTo("type", type)
            .orderBy("name")
            .limit(ApiConstants.DEFAULT_PAGE_SIZE.toLong())
        return SetsFirebasePagingSource(query)
    }
}