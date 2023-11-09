package com.andreikslpv.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andreikslpv.datasource_room_cards.CardRoomEntity
import com.andreikslpv.datasource_room_cards.CardsDao
import com.andreikslpv.datasource_room_cards.CardsRoomConverters
import com.andreikslpv.datasource_room_sets.TypeOfSetDao
import com.andreikslpv.datasource_room_sets.TypeOfSetRoomEntity

@Database(
    entities = [TypeOfSetRoomEntity::class, CardRoomEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class, CardsRoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun typeOfSetDao(): TypeOfSetDao

    abstract fun cardsDao(): CardsDao

}