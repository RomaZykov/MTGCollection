package com.andreikslpv.datasource_room_sets

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SetsDao {

    /** Get sets by type */
    @Query("SELECT * FROM ${TypeOfSetRoomConstants.TABLE_OF_SETS} WHERE `setType` = :type")
    fun getSetsByType(type: String): Flow<List<SetRoomEntity>>

    /** Clear all local records */
    @Query("DELETE FROM ${TypeOfSetRoomConstants.TABLE_OF_SETS}")
    fun clear()

    /** Insert (or replace by code) a list of sets. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(sets: List<SetRoomEntity>)

    /** Clear old records and place new records from the list. */
    @Transaction
    suspend fun refresh(sets: List<SetRoomEntity>) {
        clear()
        save(sets)
    }
}