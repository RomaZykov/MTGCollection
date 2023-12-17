package com.andreikslpv.common

/**
 * Configuration of alert dialog to be displayed via [CommonUi.alertDialog]
 */
data class AlertDialogConfig(
    val title: String,
    val message: String,
    val positiveButton: String,
    val negativeButton: String? = null,
)