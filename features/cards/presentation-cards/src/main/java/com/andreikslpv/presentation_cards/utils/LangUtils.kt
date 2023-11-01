package com.andreikslpv.presentation_cards.utils

import android.content.Context
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_cards.entities.CardLanguage

object LangUtils {

    fun getCardNameByLanguage(card: CardModel, systemLang: CardLanguage): String {
        var result = card.name
        card.foreignNames.forEach {
            if (it.language == systemLang.cardLang) result = it.name
        }
        return result
    }

    fun getCardImageByLanguage(card: CardModel, systemLang: CardLanguage): String {
        var result = card.imageUrl
        card.foreignNames.forEach {
            if (it.language == systemLang.cardLang) result = it.imageUrl
        }
        return result
    }

    fun chooseLanguage(context: Context): CardLanguage {
        val systemLang =
            context.resources.configuration.locales.get(0).displayLanguage.lowercase()
        var result = CardLanguage.ENGLISH
        CardLanguage.values().forEach {
            if (it.systemLang == systemLang) result = it
        }
        return result
    }

}