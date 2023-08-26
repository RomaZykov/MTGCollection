package com.andreikslpv.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.andreikslpv.presentation.databinding.MergeProfileDialogBinding

class ProfileDialog(context: Context, attributeSet: AttributeSet?) :
    ConstraintLayout(context, attributeSet) {
    val binding = MergeProfileDialogBinding.inflate(LayoutInflater.from(context), this)
    val actionButton = binding.dialogActionButton
    val cancelButton = binding.dialogCancelButton
    val dialogTitle = binding.dialogTitle
    val dialogText = binding.dialogText

    init {
        binding.dialogCancelButton.setOnClickListener {
            this.visible(false)
        }
    }

}