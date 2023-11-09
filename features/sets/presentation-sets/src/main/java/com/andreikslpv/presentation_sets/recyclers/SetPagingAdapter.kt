package com.andreikslpv.presentation_sets.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.andreikslpv.presentation_sets.databinding.ItemSetBinding
import com.andreikslpv.domain_sets.entities.SetEntity

class SetPagingAdapter(
    private val setClickListener: SetItemClickListener,
) : PagingDataAdapter<SetEntity, SetViewHolder>(SetItemDiff()) {

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