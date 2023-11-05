package com.andreikslpv.presentation_sets.recyclers

import androidx.recyclerview.widget.DiffUtil
import com.andreikslpv.domain_sets.entities.SetModel

class SetItemDiff: DiffUtil.ItemCallback<SetModel>() {
    override fun areItemsTheSame(oldItem: SetModel, newItem: SetModel): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: SetModel, newItem: SetModel): Boolean {
        return oldItem == newItem
    }
}