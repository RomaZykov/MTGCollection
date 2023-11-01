package com.andreikslpv.presentation_auth.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_auth.usecase.profile.GetCollectionUseCase
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.CardPreviewViewHolder
import com.andreikslpv.presentation.recyclers.ItemDiff
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardHistoryRecyclerAdapter(
    private val cardClickListener: CardItemClickListener,
    private val collectionClickListener: CardItemClickListener,
    private val getCollectionUseCase: GetCollectionUseCase,
    private val glide: RequestManager,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CardModel>()

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
                CoroutineScope(Dispatchers.Main).launch {
                    getCollectionUseCase.execute().collect {
                        if (it.contains(items[position].id))
                            holder.binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having)
                        else
                            holder.binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having_not)
                    }
                }
                holder.binding.itemContainer.setOnClickListener {
                    cardClickListener.click(items[position])
                }
                holder.binding.itemButtonCollection.setOnClickListener {
                    collectionClickListener.click(items[position])
                }
            }
        }
    }

    fun changeItems(list: List<CardModel>) {
        val diff = ItemDiff(items, list)
        val diffResult = DiffUtil.calculateDiff(diff)
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

}