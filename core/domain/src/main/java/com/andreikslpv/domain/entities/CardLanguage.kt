package com.andreikslpv.domain.entities

/**
 * A class designed to store the card language and the corresponding operating system language
 */

enum class CardLanguage(val systemLang: String, val cardLang: String) {
    NONE("", ""),
    ENGLISH("english", "English"),
    RUSSIAN("русский", "Russian"),
    GERMAN("deutsch", "German"),
    SPANISH("español", "Spanish"),
    FRENCH("français", "French"),
    ITALIAN("italiano", "Italian"),
    PORTUGUESE("português", "Portuguese (Brazil)"),
    KOREAN("한국어", "Korean"),
    JAPANESE("日本語", "Japanese"),
    CHINESE_SIMPL("中文", "Chinese Simplified"),
    CHINESE_TRAD("none", "Chinese Traditional"),
    FUN_LATIN("none", "Latin"),
    FUN_GREEK("none", "Classic Greek"),
    FUN_SANSKRIT("none", "Sanskrit"),
    FUN_ARABIC("none", "Arabic"),
    FUN_HEBREW("none", "Hebrew"),
    FUN_PHYREXIAN("none", "Phyrexian"),
    FUN_PIG_LATIN("none", "Pig Latin"),
}
enum class CardLanguageV2(val systemLang: String, val cardLang: String) {
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

