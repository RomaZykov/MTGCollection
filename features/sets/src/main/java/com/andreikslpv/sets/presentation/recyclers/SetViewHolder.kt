package com.andreikslpv.sets.presentation.recyclers

import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.sets.databinding.ItemSetBinding
import com.andreikslpv.sets.domain.entities.SetFeatureModel

class SetViewHolder(val binding: ItemSetBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(set: SetFeatureModel) {
        binding.itemTitle.text = set.name

//        binding.itemTitle.text = recipe.name
//        binding.itemWarning.visible(recipe.isContainExclude)
//        Glide.with(itemView)
//            .load(recipe.imagePreview)
//            .centerCrop()
//            .into(binding.itemImage)
//        binding.itemTimerValue.text = binding.root.context.getString(R.string.time, recipe.time)
//        val kKal = recipe.caloriesCount.toDouble()
//        val portionCount = recipe.portions
//        val result = (kKal / portionCount).roundToInt()
    }
}