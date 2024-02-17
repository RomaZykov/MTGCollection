package com.andreikslpv.datasource_room_sets

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreikslpv.datasource_room_sets.TypeOfSetRoomConstants.TABLE_TYPES_OF_SETS
import com.andreikslpv.domain_sets.entities.TypeOfSetEntity

/**
 * Database (Room) entity for storing types of set in the local storage.
 */

@Entity(tableName = TABLE_TYPES_OF_SETS)
data class TypeOfSetRoomEntity(
    @PrimaryKey override val code: String = "",
    override val name: String = "",
    override val countOfSet: Int = 0,
): TypeOfSetEntity {

    constructor(typeOfSet: TypeOfSetEntity?) : this(
        code = typeOfSet?.code ?: "",
        name = typeOfSet?.name ?: "",
        countOfSet = typeOfSet?.countOfSet ?: 0,
    )
}
