package com.andreikslpv.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.common_impl.utils.LangUtils
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.bumptech.glide.RequestManager

class CardPreviewViewHolder(
    val binding: ItemCardPreviewBinding,
    private val glide: RequestManager
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(card: CardFeatureModel) {
        val systemLang = LangUtils.chooseLanguage(binding.root.context)
        binding.itemTitle.text = LangUtils.getCardNameByLanguage(card, systemLang)
        glide.load(LangUtils.getCardImageByLanguage(card, systemLang))
            .placeholder(com.andreikslpv.presentation.R.drawable.cover_small)
            .centerCrop()
            .into(binding.itemImage)
    }

}