package com.andreikslpv.data.auth.entities

import android.net.Uri

data class AccountDataEntity(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: Uri?,
)
