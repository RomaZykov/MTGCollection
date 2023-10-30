package com.andreikslpv.cards.presentation.recyclers

import androidx.recyclerview.widget.DiffUtil
import com.andreikslpv.domain.entities.CardFeatureModel

class CardItemDiff: DiffUtil.ItemCallback<com.andreikslpv.domain.entities.CardFeatureModel>() {
    override fun areItemsTheSame(oldItem: com.andreikslpv.domain.entities.CardFeatureModel, newItem: com.andreikslpv.domain.entities.CardFeatureModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: com.andreikslpv.domain.entities.CardFeatureModel, newItem: com.andreikslpv.domain.entities.CardFeatureModel): Boolean {
        return oldItem == newItem
    }
}