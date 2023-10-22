package com.andreikslpv.data.cards.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreikslpv.data.cards.entities.CardRoomModel
import com.andreikslpv.data.constants.RoomConstants

@Dao
interface CardsDao {

    @Query("SELECT * FROM ${RoomConstants.TABLE_CACHED_CARDS} WHERE [${RoomConstants.COLUMN_CARD_SET}] = :codeOfSet")
    fun getCardsInSet(codeOfSet: String): List<CardRoomModel>

    @Query("DELETE FROM ${RoomConstants.TABLE_CACHED_CARDS}")
    fun deleteAllCards(): Int

    @Insert(entity = CardRoomModel::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertCards(sets: List<CardRoomModel>): List<Long>
}