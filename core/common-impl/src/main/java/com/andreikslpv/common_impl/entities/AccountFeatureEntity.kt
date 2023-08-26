package com.andreikslpv.common_impl.entities

import android.net.Uri

data class AccountFeatureEntity(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: Uri?,
)
