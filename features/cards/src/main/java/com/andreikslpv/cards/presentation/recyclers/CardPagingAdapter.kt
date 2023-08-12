package com.andreikslpv.cards.presentation.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.andreikslpv.presentation.recyclers.ItemClickListener

class CardPagingAdapter(
    private val cardClickListener: CardItemClickListener,
    private val availableClickListener: ItemClickListener,
) : PagingDataAdapter<CardFeatureModel, CardPreviewViewHolder>(CardItemDiff()) {

    override fun onBindViewHolder(holder: CardPreviewViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }

        holder.binding.itemContainer.setOnClickListener {
            getItem(position)?.let { card ->
                cardClickListener.click(card)
            }
        }

        holder.binding.itemButtonHaving.setOnClickListener {
            getItem(position)?.let { card ->
                availableClickListener.click(card.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPreviewViewHolder {
        return CardPreviewViewHolder(
            ItemCardPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    }
}