package com.andreikslpv.common_impl

import com.andreikslpv.common.SettingsBaseValue

data class SettingsIntValue(
    override val key: String,
    override val defaultValue: Int,
) : SettingsBaseValue(key, defaultValue)
