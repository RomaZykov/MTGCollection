package com.andreikslpv.presentation.utils

import android.content.Context
import com.andreikslpv.domain.entities.CardLanguage
import com.andreikslpv.domain.entities.CardUiEntity

object LangUtils {

    /**
     * @return the name of the card by [CardLanguage]
     */
    fun getCardNameByLanguage(card: CardUiEntity, systemLang: CardLanguage): String {
        var result = card.name
        card.foreignNamesList.forEach {
            if (it.language == systemLang.cardLang) result = it.name
        }
        return result
    }

    /**
     * @return the image of the card by [CardLanguage]
     */
    fun getCardImageByLanguage(card: CardUiEntity, systemLang: CardLanguage): String {
        var result = card.imageUrl
        card.foreignNamesList.forEach {
            if (it.language == systemLang.cardLang) result = it.imageUrl
        }
        return result
    }

    /**
     * Selects the [CardLanguage] according to the language of the operating system
     */
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