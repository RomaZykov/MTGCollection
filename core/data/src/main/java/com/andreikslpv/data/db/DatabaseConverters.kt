package com.andreikslpv.data.db

import androidx.room.TypeConverter

class DatabaseConverters {


    @TypeConverter
    fun stringListToString(stringList: List<String>?): String {
        var result = ""
        stringList?.forEach {
            result += "$it#"
        }
        return result.trimEnd('#')
    }

    @TypeConverter
    fun stringToStringList(stringList: String): List<String> {
        return stringList.split("#")
    }

}