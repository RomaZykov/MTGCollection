package com.andreikslpv.data.cards

import com.andreikslpv.data.cards.entities.CardDataModel

interface CardsApiCallback {
    fun onSuccess(items: List<CardDataModel>)
    fun onFailure()
}