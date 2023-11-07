package com.andreikslpv.presentation_cards.recyclers

import androidx.recyclerview.widget.DiffUtil
import com.andreikslpv.domain.entities.CardUiEntity

class CardItemDiff: DiffUtil.ItemCallback<CardUiEntity>() {
    override fun areItemsTheSame(oldItem: CardUiEntity, newItem: CardUiEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardUiEntity, newItem: CardUiEntity): Boolean {
        return oldItem == newItem
    }
}