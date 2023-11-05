package com.andreikslpv.presentation_cards.recyclers

import androidx.recyclerview.widget.DiffUtil
import com.andreikslpv.domain.entities.CardModel

class CardItemDiff: DiffUtil.ItemCallback<CardModel>() {
    override fun areItemsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem == newItem
    }
}