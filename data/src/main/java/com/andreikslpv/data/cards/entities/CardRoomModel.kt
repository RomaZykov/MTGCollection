package com.andreikslpv.data.cards.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.andreikslpv.data.constants.RoomConstants

@Entity(
    tableName = RoomConstants.TABLE_CACHED_CARDS,
    indices = [Index(value = [RoomConstants.COLUMN_CARD_ID], unique = true)]
)
data class CardRoomModel(
    @PrimaryKey
    @ColumnInfo(name = RoomConstants.COLUMN_CARD_ID) val id: String,
    @ColumnInfo(name = RoomConstants.COLUMN_CARD_IMAGE_URL) val imageUrl: String,
    @ColumnInfo(name = RoomConstants.COLUMN_CARD_NAME) val name: String,
    @ColumnInfo(name = RoomConstants.COLUMN_CARD_NUMBER) val number: String,
    @ColumnInfo(name = RoomConstants.COLUMN_CARD_SET) val set: String,
    @ColumnInfo(name = RoomConstants.COLUMN_CARD_SET_NAME) val setName: String,
    @ColumnInfo(name = RoomConstants.COLUMN_CARD_FOREIGN_NAMES) val foreignNames: String,
)
