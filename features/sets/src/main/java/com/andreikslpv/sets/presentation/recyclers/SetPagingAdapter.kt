package com.andreikslpv.sets.presentation.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.andreikslpv.sets.databinding.ItemSetBinding
import com.andreikslpv.sets.domain.entities.SetFeatureModel

class SetPagingAdapter(
    private val setClickListener: SetItemClickListener,
) : PagingDataAdapter<SetFeatureModel, SetViewHolder>(SetItemDiff()) {

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }

        holder.binding.itemContainer.setOnClickListener {
            getItem(position)?.let { set ->
                setClickListener.click(set)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        return SetViewHolder(
            ItemSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    }
}