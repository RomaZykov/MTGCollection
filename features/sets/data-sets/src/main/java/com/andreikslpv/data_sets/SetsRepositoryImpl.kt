package com.andreikslpv.data_sets

import com.andreikslpv.data_sets.datasource.SetsDataSource
import com.andreikslpv.data_sets.datasource.TypesDataSource
import com.andreikslpv.data_sets.services.SetsApi
import com.andreikslpv.domain.entities.CardLanguage
import com.andreikslpv.domain_sets.SetsRepository
import com.andreikslpv.domain_sets.entities.TypeOfSetEntity
import com.andreikslpv.domain_sets.entities.TypeOfSetFirebaseEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SetsRepositoryImpl @Inject constructor(
    private val setsDataSource: SetsDataSource,
    private val typesDataSource: TypesDataSource,
    private val remoteDatabase: FirebaseFirestore,
    private val setsApi: SetsApi,
    private val systemLanguage: CardLanguage,
) : SetsRepository {

    override suspend fun getNamesOfAllTypesOfSet() = typesDataSource.getNamesOfTypes()

    override suspend fun getAllTypes() = typesDataSource.getAllTypes()

    override fun getTypeCodeByName(nameOfType: String) =
        typesDataSource.getTypeCodeByName(nameOfType)

    override fun getSetsByType(codeOfType: String) = setsDataSource.getSetsByType(codeOfType)

    override suspend fun updateSets() {
        try {
            val sets = setsApi.getAllSets().data
            if (sets.isNotEmpty()) setsDataSource.saveSets(sets)
        } catch (_: Exception) {
        }
    }

    override suspend fun updateTypesInDb(types: List<TypeOfSetEntity>) =
        typesDataSource.updateTypesInDb(types)

    override suspend fun getTypesOfSetInRemoteDb() = flow {
        val path = if (systemLanguage == CardLanguage.RUSSIAN) {
            FirestoreConstants.PATH_TYPES_OF_SET_RU
        } else {
            FirestoreConstants.PATH_TYPES_OF_SET_EN
        }

        remoteDatabase.collection(path).get().await().also {
            emit(it.toObjects(TypeOfSetFirebaseEntity::class.java))
        }
    }.flowOn(Dispatchers.IO)

}