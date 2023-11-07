package com.andreikslpv.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.andreikslpv.data.db.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.TABLE_CACHED_SETS,
    indices = [Index(value = [DatabaseConstants.COLUMN_SET_CODE], unique = true)]
)
data class SetRoomModel(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstants.COLUMN_SET_CODE) val code: String,
    @ColumnInfo(name = DatabaseConstants.COLUMN_SET_BLOCK) val block: String,
    @ColumnInfo(name = DatabaseConstants.COLUMN_SET_NAME) val name: String,
    @ColumnInfo(name = DatabaseConstants.COLUMN_SET_ONLINE_ONLY) val onlineOnly: Boolean,
    @ColumnInfo(name = DatabaseConstants.COLUMN_SET_RELEASE_DATE) val releaseDate: String,
    @ColumnInfo(name = DatabaseConstants.COLUMN_SET_TYPE) val type: String,
    @ColumnInfo(name = DatabaseConstants.COLUMN_SET_SYMBOL_URL) val symbolUrl: String = "",
    @ColumnInfo(name = DatabaseConstants.COLUMN_SET_CARD_COUNT) val cardCount: Int = 0,
)
