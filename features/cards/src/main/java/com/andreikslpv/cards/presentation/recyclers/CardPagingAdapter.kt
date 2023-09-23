package com.andreikslpv.cards.presentation.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.cards.domain.usecase.GetCollectionUseCase
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.CardPreviewViewHolder
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardPagingAdapter(
    private val cardClickListener: CardItemClickListener,
    private val collectionClickListener: CardItemClickListener,
    private val getCollectionUseCase: GetCollectionUseCase,
    private val glide: RequestManager,
) : PagingDataAdapter<CardFeatureModel, CardPreviewViewHolder>(CardItemDiff()) {

    override fun onBindViewHolder(holder: CardPreviewViewHolder, position: Int) {
        getItem(position)?.let {card->
            holder.bind(card)

            CoroutineScope(Dispatchers.Main).launch {
                getCollectionUseCase.execute().collect {
                    if (it.contains(card.id))
                        holder.binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having)
                    else
                        holder.binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having_not)
                }
            }
        }

        holder.binding.itemContainer.setOnClickListener {
            getItem(position)?.let { card ->
                cardClickListener.click(card)
            }
        }

        holder.binding.itemButtonCollection.setOnClickListener {
            getItem(position)?.let { card ->
                collectionClickListener.click(card)
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