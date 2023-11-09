package com.andreikslpv.datasource_room_sets

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.andreikslpv.datasource_room_sets.TypeOfSetRoomConstants.TABLE_TYPES_OF_SETS
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeOfSetDao {

    /**
     * Get names of all types in local database
     */
    @Query("SELECT name FROM $TABLE_TYPES_OF_SETS")
    fun getNamesOfTypes(): Flow<List<String>>

    /**
     * Get all types in local database
     */
    @Query("SELECT * FROM $TABLE_TYPES_OF_SETS")
    fun getTypes(): Flow<List<TypeOfSetRoomEntity>>

    /**
     * Get type of set by type's name
     */
    @Query("SELECT `code` FROM $TABLE_TYPES_OF_SETS WHERE `name` = :name")
    fun getTypeCodeByName(name: String): Flow<String>

    /**
     * Clear all local records
     */
    @Query("DELETE FROM $TABLE_TYPES_OF_SETS")
    fun clear()

    /**
     * Insert (or replace by code) a list of types.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cards: List<TypeOfSetRoomEntity>)

    /**
     * Clear old records and place new records from the list.
     */
    @Transaction
    suspend fun refresh(types: List<TypeOfSetRoomEntity>) {
        clear()
        save(types)
    }

}