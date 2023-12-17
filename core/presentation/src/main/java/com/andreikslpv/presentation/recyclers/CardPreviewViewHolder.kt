package com.andreikslpv.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.presentation.R
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.andreikslpv.presentation.utils.LangUtils
import com.bumptech.glide.RequestManager

class CardPreviewViewHolder(
    val binding: ItemCardPreviewBinding,
    private val glide: RequestManager
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(card: CardUiEntity) {
        val systemLang = LangUtils.chooseLanguage(binding.root.context)
        val cardNames = LangUtils.getCardNameByLanguage(card, systemLang)
        binding.itemTitle.text = cardNames
        binding.itemImage.contentDescription =
            binding.root.context.getString(R.string.description_name_of_card, cardNames)
        binding.itemButtonCollection.apply {
            contentDescription = if (card.isInCollection) {
                setImageResource(R.drawable.ic_having)
                binding.root.context.getString(
                    R.string.description_button_remove_card,
                    cardNames
                )
            } else {
                setImageResource(R.drawable.ic_having_not)
                binding.root.context.getString(
                    R.string.description_button_add_card,
                    cardNames
                )
            }
        }
        glide.load(LangUtils.getCardImageByLanguage(card, systemLang))
            .placeholder(R.drawable.cover_small)
            .into(binding.itemImage)
    }

}

