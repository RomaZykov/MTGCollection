package com.andreikslpv.common_impl

import com.andreikslpv.common.BaseSettingsValue

data class StringSettingsValue(
    override val key: String,
    override val defaultValue: String,
) : BaseSettingsValue(key, defaultValue)
