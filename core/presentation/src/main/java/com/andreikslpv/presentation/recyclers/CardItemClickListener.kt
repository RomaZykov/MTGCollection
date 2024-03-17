package com.andreikslpv.presentation.recyclers

import com.andreikslpv.domain.entities.CardPreviewUiEntity


interface CardItemClickListener {
    fun click(card: CardPreviewUiEntity)
}