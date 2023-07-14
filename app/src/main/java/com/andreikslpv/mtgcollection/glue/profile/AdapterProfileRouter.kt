package com.andreikslpv.mtgcollection.glue.profile

import com.andreikslpv.common.AppRestarter
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.profile.presentation.ProfileRouter
import javax.inject.Inject

class AdapterProfileRouter @Inject constructor(
    private val appRestarter: AppRestarter,
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : ProfileRouter {

    override fun goBack() {
        globalNavComponentRouter.pop()
    }

    override fun restartApp() {
        appRestarter.restartApp()
    }
}