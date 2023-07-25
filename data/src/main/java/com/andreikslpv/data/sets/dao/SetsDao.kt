package com.andreikslpv.data.sets.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreikslpv.data.sets.constants.RoomConstants
import com.andreikslpv.data.sets.entities.SetRoomModel

@Dao
interface SetsDao {

    @Query("SELECT * FROM ${RoomConstants.TABLE_CACHED_SETS} WHERE ${RoomConstants.COLUMN_SET_TYPE} = :type")
    suspend fun getSetsByType(type: String): List<SetRoomModel>

    @Query("SELECT * FROM ${RoomConstants.TABLE_CACHED_SETS} WHERE ${RoomConstants.COLUMN_SET_CODE} = :code")
    fun getSetByCode(code: String): List<SetRoomModel>

    @Query("DELETE FROM ${RoomConstants.TABLE_CACHED_SETS}")
    fun deleteAllSets(): Int

    @Insert(entity = SetRoomModel::class, onConflict = OnConflictStrategy.ABORT)
    fun insertSets(sets: List<SetRoomModel>): List<Long>

    @Insert(entity = SetRoomModel::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertSet(set: SetRoomModel): Long

}