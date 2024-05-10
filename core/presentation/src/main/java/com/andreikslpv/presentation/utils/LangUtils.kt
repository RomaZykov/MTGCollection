package com.andreikslpv.presentation.utils

import android.content.Context
import com.andreikslpv.domain.entities.CardLanguage

object LangUtils {

    /**
     * Selects the [CardLanguage] according to the language of the operating system
     */
    fun chooseLanguage(context: Context): CardLanguage {
        val systemLang =
            context.resources.configuration.locales.get(0).displayLanguage.lowercase()
        var result = CardLanguage.ENGLISH
        CardLanguage.entries.forEach {
            if (it.systemLang == systemLang) result = it
        }
        return result
    }

}