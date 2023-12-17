package com.andreikslpv.presentation_cards.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.CardPreviewViewHolder
import com.bumptech.glide.RequestManager

class CardPagingAdapter(
    private val cardClickListener: CardItemClickListener,
    private val collectionClickListener: CardItemClickListener,
    private val glide: RequestManager,
) : PagingDataAdapter<CardUiEntity, CardPreviewViewHolder>(
    CardItemDiff()
) {

    override fun onBindViewHolder(holder: CardPreviewViewHolder, position: Int) {
        getItem(position)?.let { card ->
            holder.bind(card)
        }

        holder.binding.itemContainer.setOnClickListener {
            try {
                getItem(position)?.let { card ->
                    cardClickListener.click(card)
                }
            } catch (_: Exception) {
            }
        }

        holder.binding.itemButtonCollection.setOnClickListener {
            try {
                getItem(position)?.let { card ->
                    collectionClickListener.click(card)
                }
            } catch (_: Exception) {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPreviewViewHolder {
        return CardPreviewViewHolder(
            ItemCardPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            glide
        )


    }
}