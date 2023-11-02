package com.andreikslpv.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.andreikslpv.presentation.utils.LangUtils
import com.bumptech.glide.RequestManager

class CardPreviewViewHolder(
    val binding: ItemCardPreviewBinding,
    private val glide: RequestManager
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(card: CardModel) {
        val systemLang = LangUtils.chooseLanguage(binding.root.context)
        binding.itemTitle.text = LangUtils.getCardNameByLanguage(card, systemLang)
        glide.load(LangUtils.getCardImageByLanguage(card, systemLang))
            .placeholder(com.andreikslpv.presentation.R.drawable.cover_small)
            .centerCrop()
            .into(binding.itemImage)
    }

}