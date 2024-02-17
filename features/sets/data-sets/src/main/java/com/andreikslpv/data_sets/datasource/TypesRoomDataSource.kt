package com.andreikslpv.data_sets.datasource

import com.andreikslpv.datasource_room_sets.TypeOfSetDao
import com.andreikslpv.datasource_room_sets.TypeOfSetRoomEntity
import com.andreikslpv.domain_sets.entities.TypeOfSetEntity
import javax.inject.Inject

class TypesRoomDataSource @Inject constructor(
    private val typeOfSetDao: TypeOfSetDao,
) : TypesDataSource {

    override suspend fun getNamesOfTypes() = typeOfSetDao.getNamesOfTypes()

    override suspend fun getAllTypes() = typeOfSetDao.getTypes()

    override fun getTypeCodeByName(name: String) = typeOfSetDao.getTypeCodeByName(name)

    override suspend fun updateTypesInDb(types: List<TypeOfSetEntity>) = typeOfSetDao.refresh(
        types.map { TypeOfSetRoomEntity(it) }
    )

}