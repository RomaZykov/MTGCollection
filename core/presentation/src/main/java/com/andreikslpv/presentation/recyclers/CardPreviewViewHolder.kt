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
        binding.itemTitle.text = LangUtils.getCardNameByLanguage(card, systemLang)
        binding.itemButtonCollection.setImageResource(
            if (card.isInCollection) R.drawable.ic_having
            else R.drawable.ic_having_not
        )
        glide.load(LangUtils.getCardImageByLanguage(card, systemLang))
            .placeholder(R.drawable.cover_small)
            .centerCrop()
            .into(binding.itemImage)
    }

}

