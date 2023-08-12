package com.andreikslpv.cards.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.usecase.GetAvailableUseCase
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.bumptech.glide.RequestManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardPreviewViewHolder(val binding: ItemCardPreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface CardPreviewEntryPoint {
        fun provideGlide(): RequestManager
        fun provideGetAvailableUseCase(): GetAvailableUseCase
    }

    fun bind(card: CardFeatureModel) {
        //println("AAA lang = ${binding.root.context.resources.configuration.locales.get(0).displayName}")
        binding.itemTitle.text = card.name

        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(binding.root.context, CardPreviewEntryPoint::class.java)

        val glide = hiltEntryPoint.provideGlide()
        glide.load(card.imageUrl)
            .placeholder(com.andreikslpv.presentation.R.drawable.cover_small)
            .centerCrop()
            .into(binding.itemImage)

        CoroutineScope(Dispatchers.Main).launch {
            hiltEntryPoint.provideGetAvailableUseCase().execute().collect {
                if (it.contains(card.id))
                    binding.itemButtonHaving.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having)
                else
                    binding.itemButtonHaving.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having_not)
            }
        }
    }
}