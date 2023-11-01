package com.andreikslpv.data_cards

import com.andreikslpv.domain.entities.CardModel

interface CardsApiCallback {
    fun onSuccess(items: List<CardModel>)
    fun onFailure()
}