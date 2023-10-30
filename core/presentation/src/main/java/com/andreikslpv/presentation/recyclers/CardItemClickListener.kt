package com.andreikslpv.presentation.recyclers

import com.andreikslpv.domain.entities.CardFeatureModel


interface CardItemClickListener {
    fun click(card: com.andreikslpv.domain.entities.CardFeatureModel)
}