package com.andreikslpv.common_impl

import com.andreikslpv.common.SettingsBaseValue

data class SettingsStringValue(
    override val key: String,
    override val defaultValue: String,
) : SettingsBaseValue(key, defaultValue)
