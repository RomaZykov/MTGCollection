package com.andreikslpv.sets.presentation.recyclers

import androidx.recyclerview.widget.DiffUtil
import com.andreikslpv.sets.domain.entities.SetFeatureModel

class SetItemDiff: DiffUtil.ItemCallback<SetFeatureModel>() {
    override fun areItemsTheSame(oldItem: SetFeatureModel, newItem: SetFeatureModel): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: SetFeatureModel, newItem: SetFeatureModel): Boolean {
        return oldItem == newItem
    }
}