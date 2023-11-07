package com.andreikslpv.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.andreikslpv.data.db.RoomConstants.TABLE_CARDS
import com.andreikslpv.data.db.entities.CardRoomEntity

@Dao
interface CardsDao {

    /**
     * Note that orderBy and ASC/DESC order should be the same as
     * in the network request.
     */
    @Query("SELECT * FROM $TABLE_CARDS WHERE `set` = :codeOfSet ORDER BY orderedNumber")
    fun getPagingSource(codeOfSet: String): PagingSource<Int, CardRoomEntity>

    /**
     * Insert (or replace by ID) a list of Cards.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cards: List<CardRoomEntity>)

    /**
     * Clear local records for the specified set (or clear all
     * local records if year is NULL)
     */
    @Query("DELETE FROM $TABLE_CARDS WHERE :codeOfSet IS NULL OR `set` = :codeOfSet")
    suspend fun clear(codeOfSet: String?)

    /**
     * Clear old records and place new records from the list.
     */
    @Transaction
    suspend fun refresh(codeOfSet: String, cards: List<CardRoomEntity>) {
        clear(codeOfSet)
        save(cards)
    }

    /**
     * Convenient call for saving one Card entity.
     */
    suspend fun save(card: CardRoomEntity) {
        save(listOf(card))
    }

}