package com.andreikslpv.data.sets.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.constants.ApiConstants
import com.andreikslpv.data.constants.FirestoreConstants
import com.andreikslpv.data.sets.SetsApiCallback
import com.andreikslpv.data.sets.entities.SetDataModel
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SetsFirebaseDataSource @Inject constructor(
    private val database: FirebaseFirestore,
) : SetsApiDataSource {

    override fun getSetsByType(
        type: String,
        callback: SetsApiCallback
    ): PagingSource<*, SetDataModel> {
        val collection = database.collection(FirestoreConstants.PATH_SETS)
        val query = collection
            .whereEqualTo("type", type)
            .orderBy("name")
            .limit(ApiConstants.DEFAULT_PAGE_SIZE.toLong())
        return SetsFirebasePagingSource(query)
    }
}