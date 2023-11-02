package com.andreikslpv.presentation.recyclers

import com.andreikslpv.domain.entities.CardModel


interface CardItemClickListener {
    fun click(card: CardModel)
}