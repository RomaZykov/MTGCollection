package com.andreikslpv.data.sets.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.andreikslpv.data.sets.constants.RoomConstants

@Entity(
    tableName = RoomConstants.TABLE_CACHED_SETS,
    indices = [Index(value = [RoomConstants.COLUMN_SET_CODE], unique = true)]
)
data class SetRoomModel(
    @PrimaryKey
    @ColumnInfo(name = RoomConstants.COLUMN_SET_CODE) val code: String,
    @ColumnInfo(name = RoomConstants.COLUMN_SET_BLOCK) val block: String,
    @ColumnInfo(name = RoomConstants.COLUMN_SET_NAME) val name: String,
    @ColumnInfo(name = RoomConstants.COLUMN_SET_ONLINE_ONLY) val onlineOnly: Boolean,
    @ColumnInfo(name = RoomConstants.COLUMN_SET_RELEASE_DATE) val releaseDate: String,
    @ColumnInfo(name = RoomConstants.COLUMN_SET_TYPE) val type: String,
    @ColumnInfo(name = RoomConstants.COLUMN_SET_SYMBOL_URL) val symbolUrl: String = "",
    @ColumnInfo(name = RoomConstants.COLUMN_SET_CARD_COUNT) val cardCount: Int = 0,
)
