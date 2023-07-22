package com.andreikslpv.data.sets

import androidx.paging.PagingData
import com.andreikslpv.data.sets.dto.cards.ResultCards
import com.andreikslpv.data.sets.entities.SetLocalModel
import kotlinx.coroutines.flow.Flow

interface SetsDataRepository {

    fun getTypesOfSet(): List<String>

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

    fun getSetsByType(type: String): Flow<PagingData<SetLocalModel>>

    fun getCardsInSet(codeOfSet: String): ResultCards

}