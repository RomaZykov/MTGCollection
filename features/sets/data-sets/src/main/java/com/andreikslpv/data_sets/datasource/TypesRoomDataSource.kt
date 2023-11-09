package com.andreikslpv.data_sets.datasource

import com.andreikslpv.datasource_room_sets.TypeOfSetDao
import com.andreikslpv.datasource_room_sets.TypeOfSetRoomEntity
import com.andreikslpv.domain_sets.entities.TypeOfSetEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TypesRoomDataSource @Inject constructor(
    private val typeOfSetDao: TypeOfSetDao,
) : TypesDataSource {

    override suspend fun getNamesOfTypes() = typeOfSetDao.getNamesOfTypes()

    override suspend fun getAllTypes() = typeOfSetDao.getTypes().map { types ->
        types.map {
            TypeOfSetEntity(
                code = it.code,
                name = it.name,
                countOfSet = it.countOfSet
            )
        }
    }

    override fun getTypeCodeByName(name: String) = typeOfSetDao.getTypeCodeByName(name)

    override suspend fun updateTypesInDb(types: List<TypeOfSetEntity>) = typeOfSetDao.refresh(
        types.map {
            TypeOfSetRoomEntity(
                code = it.code,
                name = it.name,
                countOfSet = it.countOfSet
            )
        }
    )

}