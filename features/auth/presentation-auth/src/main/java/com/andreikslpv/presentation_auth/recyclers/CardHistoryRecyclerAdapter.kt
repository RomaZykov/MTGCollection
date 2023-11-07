package com.andreikslpv.presentation_auth.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.CardPreviewViewHolder
import com.andreikslpv.presentation.recyclers.ItemDiff
import com.bumptech.glide.RequestManager

class CardHistoryRecyclerAdapter(
    private val cardClickListener: CardItemClickListener,
    private val collectionClickListener: CardItemClickListener,
    private val glide: RequestManager,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CardUiEntity>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CardPreviewViewHolder(
            ItemCardPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            glide
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardPreviewViewHolder -> {
                holder.bind(items[position])
                holder.binding.itemContainer.setOnClickListener {
                    cardClickListener.click(items[position])
                }
                holder.binding.itemButtonCollection.setOnClickListener {
                    collectionClickListener.click(items[position])
                }
            }
        }
    }

    fun changeItems(list: List<CardUiEntity>) {
        val diff = ItemDiff(items, list)
        val diffResult = DiffUtil.calculateDiff(diff)
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

}