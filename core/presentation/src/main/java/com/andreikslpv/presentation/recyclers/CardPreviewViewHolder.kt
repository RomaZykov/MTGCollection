package com.andreikslpv.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.andreikslpv.domain.entities.CardPreviewUiEntity
import com.andreikslpv.presentation.R
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding

class CardPreviewViewHolder(val binding: ItemCardPreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(card: CardPreviewUiEntity) {
        binding.itemTitle.text = card.name
        binding.itemImage.contentDescription =
            binding.root.context.getString(R.string.description_name_of_card, card.name)
        binding.itemButtonCollection.apply {
            contentDescription = if (card.isInCollection) {
                setImageResource(R.drawable.ic_having)
                binding.root.context.getString(
                    R.string.description_button_remove_card,
                    card.name
                )
            } else {
                setImageResource(R.drawable.ic_having_not)
                binding.root.context.getString(
                    R.string.description_button_add_card,
                    card.name
                )
            }
        }
        binding.itemImage.load(card.imagePreviewUri) {
            placeholder(R.drawable.cover_small)
        }
    }

}

