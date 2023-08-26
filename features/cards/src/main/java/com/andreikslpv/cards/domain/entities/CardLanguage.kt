package com.andreikslpv.cards.domain.entities

enum class CardLanguage(val systemLang: String, val cardLang: String) {
    ENGLISH("english", "default"),
    RUSSIAN("русский", "Russian"),
    GERMAN("deutsch", "German"),
    SPANISH("español", "Spanish"),
    FRENCH("français", "French"),
    ITALIAN("italiano", "Italian"),
    PORTUGUESE("português", "Portuguese (Brazil)"),
    KOREAN("한국어", "Korean"),
    JAPANESE("日本語", "Japanese"),
    CHINESE("中文", "Chinese Simplified"),
}

//Portuguese (Brazil)
//Chinese Simplified
//Chinese Traditional