package com.andreikslpv.mtgcollection

import android.app.Application
import com.andreikslpv.common.Core
import com.andreikslpv.common.CoreProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var coreProvider: CoreProvider

    override fun onCreate() {
        super.onCreate()
        Core.init(coreProvider)
    }

}