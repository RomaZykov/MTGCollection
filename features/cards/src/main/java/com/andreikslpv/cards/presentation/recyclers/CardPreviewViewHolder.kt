package com.andreikslpv.cards.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.usecase.GetCollectionUseCase
import com.andreikslpv.cards.presentation.utils.LangUtils
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
        fun provideGetCollectionUseCase(): GetCollectionUseCase
    }

    fun bind(card: CardFeatureModel) {
        val systemLang = LangUtils.chooseLanguage(binding.root.context)
        binding.itemTitle.text = LangUtils.getCardNameByLanguage(card, systemLang)

        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                binding.root.context,
                CardPreviewEntryPoint::class.java
            )

        val glide = hiltEntryPoint.provideGlide()
        glide.load(LangUtils.getCardImageByLanguage(card, systemLang))
            .placeholder(com.andreikslpv.presentation.R.drawable.cover_small)
            .centerCrop()
            .into(binding.itemImage)

        CoroutineScope(Dispatchers.Main).launch {
            hiltEntryPoint.provideGetCollectionUseCase().execute().collect {
                if (it.contains(card.id))
                    binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having)
                else
                    binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having_not)
            }
        }
    }


}