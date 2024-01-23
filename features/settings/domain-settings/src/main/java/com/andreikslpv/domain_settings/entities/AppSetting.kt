package com.andreikslpv.domain_settings.entities

/**
 * The parameter [defaultValue] can only be of the following types:
 * String, Int, Long, Boolean, Float, Set<*>, MutableSet<*>
 */
open class AppSetting(val key: String, val defaultValue: Any)

class SettingStartSetsType(key: String = "start_sets_type", defaultValue: String = "Core") :
    AppSetting(key, defaultValue)

class SettingVersionSetsType(key: String = "version_types_of_set", defaultValue: Int = 0) :
    AppSetting(key, defaultValue)

class SettingPrivacyPolicy(key: String = "privacy_policy", defaultValue: String = "") :
    AppSetting(key, defaultValue)