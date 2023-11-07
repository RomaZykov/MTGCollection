package com.andreikslpv.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreikslpv.data.db.DatabaseConstants
import com.andreikslpv.data.db.entities.SetRoomModel

@Dao
interface SetsDao {

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_CACHED_SETS} WHERE ${DatabaseConstants.COLUMN_SET_TYPE} = :type")
    fun getSetsByType(type: String): List<SetRoomModel>

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_CACHED_SETS} WHERE ${DatabaseConstants.COLUMN_SET_CODE} = :code")
    fun getSetByCode(code: String): List<SetRoomModel>

    @Query("DELETE FROM ${DatabaseConstants.TABLE_CACHED_SETS}")
    fun deleteAllSets(): Int

    @Insert(entity = SetRoomModel::class, onConflict = OnConflictStrategy.ABORT)
    fun insertSets(sets: List<SetRoomModel>): List<Long>

    @Insert(entity = SetRoomModel::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertSet(set: SetRoomModel): Long

}