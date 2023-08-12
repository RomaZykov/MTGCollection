package com.andreikslpv.cards.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.entities.CardLanguage
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
        val systemLang = chooseLanguage()
        binding.itemTitle.text = getCardNameByLanguage(card, systemLang)

        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(binding.root.context, CardPreviewEntryPoint::class.java)

        val glide = hiltEntryPoint.provideGlide()
        glide.load(getCardImageByLanguage(card, systemLang))
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

    private fun getCardNameByLanguage(card: CardFeatureModel, systemLang: CardLanguage): String {
        var result = card.name
        card.foreignNames.forEach {
            if (it.language == systemLang.cardLang) result = it.name
        }
        return result
    }

    private fun getCardImageByLanguage(card: CardFeatureModel, systemLang: CardLanguage): String {
        var result = card.imageUrl
        card.foreignNames.forEach {
            if (it.language == systemLang.cardLang) result = it.imageUrl
        }
        return result
    }

    private fun chooseLanguage(): CardLanguage{
        val systemLang = binding.root.context.resources.configuration.locales.get(0).displayLanguage.lowercase()
        var result = CardLanguage.ENGLISH
        CardLanguage.values().forEach {
            if(it.systemLang == systemLang) result = it
        }
        return result
    }
}