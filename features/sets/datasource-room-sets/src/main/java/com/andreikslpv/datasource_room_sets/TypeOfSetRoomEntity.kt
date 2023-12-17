package com.andreikslpv.datasource_room_sets

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreikslpv.datasource_room_sets.TypeOfSetRoomConstants.TABLE_TYPES_OF_SETS

/**
 * Database (Room) entity for storing types of set in the local storage.
 */

@Entity(tableName = TABLE_TYPES_OF_SETS)
data class TypeOfSetRoomEntity(
    @PrimaryKey val code: String = "",
    val name: String = "",
    val countOfSet: Int = 0,
)
