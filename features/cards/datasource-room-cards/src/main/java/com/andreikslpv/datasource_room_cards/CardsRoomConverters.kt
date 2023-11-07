package com.andreikslpv.datasource_room_cards

import androidx.room.TypeConverter
import com.andreikslpv.domain.entities.AvailableCardEntity
import com.andreikslpv.domain.entities.ForeignNameEntity

class CardsRoomConverters {

    @TypeConverter
    fun foreignNamesListToString(foreignNamesList: List<ForeignNameEntity>): String {
        var result = ""
        foreignNamesList.forEach { fn ->
            val temp = "${fn.language}##${fn.name}##${fn.imageUrl}##${fn.multiverseid}"
            result += "$temp@@"
        }
        return result.trimEnd('@').trimEnd('@')
    }

    @TypeConverter
    fun stringToForeignNamesList(foreignNamesString: String): List<ForeignNameEntity> {
        val result = mutableListOf<ForeignNameEntity>()
        val temp = foreignNamesString.split("@@")
        temp.forEach {
            val fn = it.split("##")
            if (fn.size >= 4) {
                val foreignName = ForeignNameEntity(
                    language = fn[0],
                    name = fn[1],
                    imageUrl = fn[2],
                    multiverseid = fn[3].toInt()
                )
                result.add(foreignName)
            }
        }
        return result
    }

    // -----------------------------------------------------------

    @TypeConverter
    fun availableCardsMLToString (availableCards: MutableList<AvailableCardEntity>) = ""

    @TypeConverter
    fun stringToAvailableCardsML(string: String) = mutableListOf<AvailableCardEntity>()
}