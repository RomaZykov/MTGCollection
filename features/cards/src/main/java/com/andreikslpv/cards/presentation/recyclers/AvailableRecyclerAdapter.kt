package com.andreikslpv.cards.presentation.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.cards.databinding.ItemAvailableBinding
import com.andreikslpv.domain.entities.AvailableCardFeatureModel
import com.andreikslpv.presentation.recyclers.ItemDiff
import kotlinx.coroutines.flow.MutableStateFlow

class AvailableRecyclerAdapter(
    private val editItemClickListener: AvailableItemClickListener,
    private val selectItemClickListener: AvailableItemClickListener,
    private val selectedItem: MutableStateFlow<MutableList<com.andreikslpv.domain.entities.AvailableCardFeatureModel>>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val items = mutableListOf<com.andreikslpv.domain.entities.AvailableCardFeatureModel>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AvailableViewHolder(
            ItemAvailableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AvailableViewHolder -> {
                val isLastItem = (position == itemCount - 1)
                holder.bind(items[position], isLastItem, selectedItem)
                holder.binding.itemCheckBox.setOnClickListener {
                    selectItemClickListener.click(items[position])
                }
                holder.binding.itemPencil.setOnClickListener {
                    editItemClickListener.click(items[position])
                }
            }
        }
    }

    fun changeItems(list: List<com.andreikslpv.domain.entities.AvailableCardFeatureModel>) {
        val diff = ItemDiff(items, list)
        val diffResult = DiffUtil.calculateDiff(diff)
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}