package com.andreikslpv.data.sets

import com.andreikslpv.data.sets.dto.cards.ResultCards
import com.andreikslpv.data.sets.dto.sets.ResultSets

interface SetsDataRepository {

    fun getTypesOfSet(): List<String>

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

    fun getSetsByType(type: String): ResultSets

    fun getCardsInSet(codeOfSet: String): ResultCards

}