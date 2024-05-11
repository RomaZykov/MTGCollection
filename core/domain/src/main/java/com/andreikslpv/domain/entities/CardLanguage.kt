package com.andreikslpv.domain.entities

/**
 * A class designed to store the card language and the corresponding operating system language
 */
enum class CardLanguage(val systemLang: String, val cardLang: String) {
    NONE("", ""),
    ENGLISH("english", "en"),
    RUSSIAN("русский", "ru"),
    GERMAN("deutsch", "de"),
    SPANISH("español", "es"),
    FRENCH("français", "fr"),
    ITALIAN("italiano", "it"),
    PORTUGUESE("português", "pt"),
    KOREAN("한국어", "ko"),
    JAPANESE("日本語", "ja"),
    CHINESE_SIMPL("中文", "zhs"),
    CHINESE_TRAD("none", "zht"),
    FUN_LATIN("none", "la"),
    FUN_GREEK("none", "grc"),
    FUN_SANSKRIT("none", "sa"),
    FUN_ARABIC("none", "ar"),
    FUN_HEBREW("none", "he"),
    FUN_PHYREXIAN("none", "ph"),
    FUN_PIG_LATIN("none", "Pig Latin"),
}

