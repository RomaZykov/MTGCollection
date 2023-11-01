package com.andreikslpv.data.db

object RoomConstants {

    const val DATABASE_NAME = "room_db"

    const val TABLE_CACHED_SETS = "cached_sets"
    const val COLUMN_SET_BLOCK = "block"
    const val COLUMN_SET_CODE = "code"
    const val COLUMN_SET_NAME = "name"
    const val COLUMN_SET_ONLINE_ONLY = "onlineOnly"
    const val COLUMN_SET_RELEASE_DATE = "releaseDate"
    const val COLUMN_SET_TYPE = "type"
    const val COLUMN_SET_SYMBOL_URL = "symbolUrl"
    const val COLUMN_SET_CARD_COUNT = "cardCount"

    const val TABLE_CACHED_CARDS = "cached_cards"
    const val COLUMN_CARD_ID = "id"
    const val COLUMN_CARD_IMAGE_URL = "imageUrl"
    const val COLUMN_CARD_NAME = "name"
    const val COLUMN_CARD_NUMBER = "number"
    const val COLUMN_CARD_SET = "set"
    const val COLUMN_CARD_SET_NAME = "setName"
    const val COLUMN_CARD_FOREIGN_NAMES = "foreignNames"
}