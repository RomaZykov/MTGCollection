package com.andreikslpv.data.settings

import com.andreikslpv.common.BaseSettingsValue
import com.andreikslpv.common_impl.IntSettingsValue
import com.andreikslpv.common_impl.StringSettingsValue

enum class ProjectSettings(val value: BaseSettingsValue) {
    START_SETS_TYPE(StringSettingsValue("start_sets_type", "core")),
    VERSION_SETS_TYPE(IntSettingsValue("version_sets_type", 0))
}