package com.andreikslpv.presentation_cards.recyclers

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andreikslpv.domain.entities.AvailableCardModel
import com.andreikslpv.presentation_cards.R
import com.andreikslpv.presentation_cards.databinding.ItemAvailableBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AvailableViewHolder(val binding: ItemAvailableBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        availableItem: AvailableCardModel,
        isLastItem: Boolean,
        selectedItem: MutableStateFlow<MutableList<AvailableCardModel>>
    ) {
        binding.itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.itemName.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(
                        binding.root.context.resources.getColor(
                            R.color.text_menu_select,
                            binding.root.context.theme
                        )
                    )
                }
            } else {
                binding.itemName.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    setTextColor(
                        binding.root.context.resources.getColor(
                            R.color.text_card_title,
                            binding.root.context.theme
                        )
                    )
                }
            }
        }

        binding.itemName.text = if (availableItem.foiled)
            binding.root.context.getString(
                R.string.available_item_name_foil,
                availableItem.language,
                availableItem.condition,
            )
        else
            binding.root.context.getString(
                R.string.available_item_name_not_foil,
                availableItem.language,
                availableItem.condition,
            )

        binding.itemCount.text = binding.root.context.getString(
            R.string.available_item_count,
            availableItem.count,
        )

        CoroutineScope(Dispatchers.IO).launch {
            selectedItem.collect {
                withContext(Dispatchers.Main) {
                    binding.itemCheckBox.isChecked = it.contains(availableItem)
                }
            }
        }
        if (isLastItem) binding.itemDivider.visibility = View.INVISIBLE
    }

}