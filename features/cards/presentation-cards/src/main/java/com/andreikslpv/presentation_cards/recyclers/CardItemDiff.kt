package com.andreikslpv.presentation_cards.recyclers

import androidx.recyclerview.widget.DiffUtil
import com.andreikslpv.domain.entities.CardPreviewUiEntity

class CardItemDiff: DiffUtil.ItemCallback<CardPreviewUiEntity>() {
    override fun areItemsTheSame(oldItem: CardPreviewUiEntity, newItem: CardPreviewUiEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardPreviewUiEntity, newItem: CardPreviewUiEntity): Boolean {
        return oldItem == newItem
    }
}