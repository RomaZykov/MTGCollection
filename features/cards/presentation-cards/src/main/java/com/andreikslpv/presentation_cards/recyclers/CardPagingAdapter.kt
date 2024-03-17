package com.andreikslpv.presentation_cards.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.andreikslpv.domain.entities.CardPreviewUiEntity
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.CardPreviewViewHolder

class CardPagingAdapter(
    private val cardClickListener: CardItemClickListener,
    private val collectionClickListener: CardItemClickListener,
) : PagingDataAdapter<CardPreviewUiEntity, CardPreviewViewHolder>(CardItemDiff()) {

    override fun onBindViewHolder(holder: CardPreviewViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        holder.binding.itemContainer.setOnClickListener {
            try {
                getItem(position)?.let { cardClickListener.click(it) }
            } catch (_: Exception) {
            }
        }

        holder.binding.itemButtonCollection.setOnClickListener {
            try {
                getItem(position)?.let { collectionClickListener.click(it) }
            } catch (_: Exception) {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPreviewViewHolder {
        return CardPreviewViewHolder(
            ItemCardPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}