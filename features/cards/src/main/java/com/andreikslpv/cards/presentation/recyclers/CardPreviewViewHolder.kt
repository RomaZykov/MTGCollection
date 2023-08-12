package com.andreikslpv.cards.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.bumptech.glide.RequestManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class CardPreviewViewHolder(val binding: ItemCardPreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideEntryPoint {
        fun glide(): RequestManager
    }

    fun bind(card: CardFeatureModel) {
        //println("AAA lang = ${binding.root.context.resources.configuration.locales.get(0).displayName}")
        binding.itemTitle.text = card.name
        println("AAA card.imageUrl = ${card.imageUrl}")

        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(binding.root.context, GlideEntryPoint::class.java)

        val glide = hiltEntryPoint.glide()
        glide.load(card.imageUrl)
            .placeholder(com.andreikslpv.presentation.R.drawable.cover_small)
            .centerCrop()
            .into(binding.itemImage)
    }
}