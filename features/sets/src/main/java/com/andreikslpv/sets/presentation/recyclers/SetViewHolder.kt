package com.andreikslpv.sets.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.andreikslpv.sets.databinding.ItemSetBinding
import com.andreikslpv.sets.domain.entities.SetFeatureModel

class SetViewHolder(val binding: ItemSetBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val imageLoader = ImageLoader.Builder(binding.root.context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    fun bind(set: SetFeatureModel) {
        binding.itemTitle.text = set.name
        val request = ImageRequest.Builder(binding.root.context)
            .data(set.symbolUrl)
            .target(binding.itemImage)
            .build()
        imageLoader.enqueue(request)
        binding.totalCount.text = set.cardCount.toString()
    }
}