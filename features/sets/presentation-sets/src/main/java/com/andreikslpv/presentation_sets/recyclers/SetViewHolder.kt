package com.andreikslpv.presentation_sets.recyclers

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.andreikslpv.domain_sets.entities.SetEntity
import com.andreikslpv.presentation_sets.databinding.ItemSetBinding

class SetViewHolder(val binding: ItemSetBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val imageLoader = ImageLoader.Builder(binding.root.context)
        .components { add(SvgDecoder.Factory()) }
        .build()

    fun bind(set: SetEntity) {
        binding.itemTitle.text = set.name
        val request = ImageRequest.Builder(binding.root.context)
            .data(set.iconSvgUri)
            .target(binding.itemImage)
            .build()
        imageLoader.enqueue(request)
        binding.totalCount.text = set.cardCount.toString()
    }
}