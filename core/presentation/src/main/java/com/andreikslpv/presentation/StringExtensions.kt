package com.andreikslpv.presentation

import android.content.Context
import android.widget.Toast

fun String.makeToast(context: Context) {
    Toast.makeText(
        context,
        this,
        Toast.LENGTH_LONG
    ).show()
}
