package com.andreikslpv.presentation.recyclers

import com.andreikslpv.common_impl.entities.CardFeatureModel


interface CardItemClickListener {
    fun click(card: CardFeatureModel)
}