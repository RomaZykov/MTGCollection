package com.andreikslpv.datasource_room_cards

import androidx.room.TypeConverter
import com.andreikslpv.domain.entities.AvailableCardEntity

class CardsRoomConverters {

    @TypeConverter
    fun availableCardsMLToString (availableCards: MutableList<AvailableCardEntity>) = ""

    @TypeConverter
    fun stringToAvailableCardsML(string: String) = mutableListOf<AvailableCardEntity>()
}