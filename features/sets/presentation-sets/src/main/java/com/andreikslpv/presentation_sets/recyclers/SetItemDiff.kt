package com.andreikslpv.presentation_sets.recyclers

import androidx.recyclerview.widget.DiffUtil
import com.andreikslpv.domain_sets.entities.SetEntity

class SetItemDiff: DiffUtil.ItemCallback<SetEntity>() {
    override fun areItemsTheSame(oldItem: SetEntity, newItem: SetEntity): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: SetEntity, newItem: SetEntity): Boolean {
        return oldItem == newItem
    }
}