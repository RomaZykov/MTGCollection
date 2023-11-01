package com.andreikslpv.domain_cards.entities

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