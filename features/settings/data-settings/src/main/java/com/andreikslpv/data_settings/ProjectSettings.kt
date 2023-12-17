package com.andreikslpv.data_settings

import com.andreikslpv.common.SettingsBaseValue
import com.andreikslpv.common_impl.SettingsIntValue
import com.andreikslpv.common_impl.SettingsStringValue

enum class ProjectSettings(val value: SettingsBaseValue) {
    START_SETS_TYPE(SettingsStringValue("start_sets_type", "Core")),
    VERSION_SETS_TYPE(SettingsIntValue("version_types_of_set", 0))
}