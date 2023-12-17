package com.andreikslpv.data_sets

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.andreikslpv.data.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data_sets.datasource.SetsDataSource
import com.andreikslpv.data_sets.datasource.TypesDataSource
import com.andreikslpv.domain_sets.SetsRepository
import com.andreikslpv.domain_sets.entities.TypeOfSetEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SetsRepositoryImpl @Inject constructor(
    private val apiDataSource: SetsDataSource,
    private val typesDataSource: TypesDataSource,
    private val remoteDatabase: FirebaseFirestore,
) : SetsRepository {

    override suspend fun getNamesOfAllTypesOfSet() = typesDataSource.getNamesOfTypes()

    override suspend fun getAllTypes() = typesDataSource.getAllTypes()

    override fun getTypeCodeByName(nameOfType: String) =
        typesDataSource.getTypeCodeByName(nameOfType)

    override fun getSetsByType(codeOfType: String) = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = DEFAULT_PAGE_SIZE
        ),
        pagingSourceFactory = { apiDataSource.getSetsByType(codeOfType) }
    ).flow

    override suspend fun updateTypesInDb(types: List<TypeOfSetEntity>) =
        typesDataSource.updateTypesInDb(types)

    override suspend fun getTypesOfSetInRemoteDb() = flow {
        try {
            remoteDatabase.collection(FirestoreConstants.PATH_TYPES_OF_SET).get().await().also {
                emit(it.toObjects(TypeOfSetEntity::class.java))
            }
        } catch (_: Exception) {
        }
    }

}