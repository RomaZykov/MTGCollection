package com.andreikslpv.mtgcollection.glue.utils

import android.content.Context
import com.andreikslpv.domain.entities.CardLanguage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    fun provideSystemLanguage(
        @ApplicationContext context: Context
    ): CardLanguage {
        val systemLang = context.resources.configuration.locales.get(0).displayLanguage.lowercase()
        var result = CardLanguage.ENGLISH
        CardLanguage.entries.forEach {
            if (it.systemLang == systemLang) result = it
        }
        return result
    }
}