package com.andreikslpv.cards.presentation.utils

import android.content.Context
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.entities.CardLanguage

object LangUtils {

    fun getCardNameByLanguage(card: CardFeatureModel, systemLang: CardLanguage): String {
        var result = card.name
        card.foreignNames.forEach {
            if (it.language == systemLang.cardLang) result = it.name
        }
        return result
    }

    fun getCardImageByLanguage(card: CardFeatureModel, systemLang: CardLanguage): String {
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