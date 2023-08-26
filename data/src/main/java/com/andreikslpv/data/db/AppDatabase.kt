package com.andreikslpv.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreikslpv.data.cards.dao.CardsDao
import com.andreikslpv.data.cards.entities.CardRoomModel
import com.andreikslpv.data.sets.dao.SetsDao
import com.andreikslpv.data.sets.entities.SetRoomModel

@Database(
    entities = [SetRoomModel::class, CardRoomModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun setsDao(): SetsDao

    abstract fun cardsDao(): CardsDao

}