package com.andreikslpv.sign_in.domain.entities

import android.net.Uri

data class AccountFeatureEntity(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: Uri?,
)
