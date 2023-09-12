package com.andreikslpv.common_impl

import com.andreikslpv.common.BaseSettingsValue

data class IntSettingsValue(
    override val key: String,
    override val defaultValue: Int,
) : BaseSettingsValue(key, defaultValue)
