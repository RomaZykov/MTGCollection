package com.andreikslpv.cards.presentation.recyclers

import androidx.recyclerview.widget.DiffUtil
import com.andreikslpv.common_impl.entities.CardFeatureModel

class CardItemDiff: DiffUtil.ItemCallback<CardFeatureModel>() {
    override fun areItemsTheSame(oldItem: CardFeatureModel, newItem: CardFeatureModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardFeatureModel, newItem: CardFeatureModel): Boolean {
        return oldItem == newItem
    }
}