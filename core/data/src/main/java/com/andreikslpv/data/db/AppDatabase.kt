package com.andreikslpv.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andreikslpv.data.db.dao.SetsDao
import com.andreikslpv.data.db.entities.SetRoomModel
import com.andreikslpv.datasource_room_cards.CardRoomEntity
import com.andreikslpv.datasource_room_cards.CardsDao
import com.andreikslpv.datasource_room_cards.CardsRoomConverters

@Database(
    entities = [SetRoomModel::class, CardRoomEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class, CardsRoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun setsDao(): SetsDao

    abstract fun cardsDao(): CardsDao

}