package com.andreikslpv.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andreikslpv.data.db.dao.CardsDao
import com.andreikslpv.data.db.dao.SetsDao
import com.andreikslpv.data.db.entities.CardRoomEntity
import com.andreikslpv.data.db.entities.CardRoomModel
import com.andreikslpv.data.db.entities.SetRoomModel

@Database(
    entities = [SetRoomModel::class, CardRoomModel::class, CardRoomEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun setsDao(): SetsDao

    abstract fun cardsDao(): CardsDao

}