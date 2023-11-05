package com.andreikslpv.domain_auth.entities

data class AccountDataEntity(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?,
    val isAnonymous: Boolean,
)
