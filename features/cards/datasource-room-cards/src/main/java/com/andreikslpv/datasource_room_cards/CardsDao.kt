package com.andreikslpv.datasource_room_cards

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.andreikslpv.datasource_room_cards.CardsRoomConstants.TABLE_CARDS
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_LANG
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_SET_CODE

@Dao
interface CardsDao {

    /**
     * Note that orderBy and ASC/DESC order should be the same as
     * in the network request.
     */
    @Query(
        "SELECT * FROM $TABLE_CARDS WHERE $COLUMN_SET_CODE = :codeOfSet AND $COLUMN_LANG = :lang ORDER BY " +
                "CASE WHEN :isAsc = 1 THEN :sortType END ASC, " +
                "CASE WHEN :isAsc = 0 THEN :sortType END DESC"
    )
    fun getPagingSource(
        codeOfSet: String,
        lang: String,
        sortType: String,
        isAsc: Boolean
    ): PagingSource<Int, CardRoomEntity>

    /**
     * Insert (or replace by ID) a list of Cards.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cards: List<CardRoomEntity>)

    /**
     * Clear local records for the specified set (or clear all
     * local records if year is NULL)
     */
    @Query("DELETE FROM $TABLE_CARDS WHERE :codeOfSet IS NULL OR $COLUMN_SET_CODE = :codeOfSet")
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