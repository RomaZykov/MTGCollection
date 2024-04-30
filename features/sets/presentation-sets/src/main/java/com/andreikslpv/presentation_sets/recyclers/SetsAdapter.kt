package com.andreikslpv.presentation_sets.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.presentation.recyclers.ItemDiff
import com.andreikslpv.presentation_sets.databinding.ItemSetBinding
import com.andreikslpv.presentation_sets.entities.SetUiEntity

class SetsAdapter(
    private val setClickListener: SetItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<SetUiEntity>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SetViewHolder(
            ItemSetBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SetViewHolder -> {
                holder.bind(items[position])
                holder.binding.itemContainer.setOnClickListener {
                    setClickListener.click(items[position])
                }
            }
        }
    }

    fun changeItems(list: List<SetUiEntity>) {
        val diff = ItemDiff(items, list)
        val diffResult = DiffUtil.calculateDiff(diff)
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}