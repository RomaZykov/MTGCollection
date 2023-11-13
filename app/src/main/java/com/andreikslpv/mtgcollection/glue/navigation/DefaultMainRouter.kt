package com.andreikslpv.mtgcollection.glue.navigation

import com.andreikslpv.common.AppRestarter
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.navigation.presentation.MainRouter
import javax.inject.Inject

class DefaultMainRouter @Inject constructor(
    private val appRestarter: AppRestarter,
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : MainRouter {

    override fun launchMain() = globalNavComponentRouter.startTabs()

    override fun restartApp() = appRestarter.restartApp()

}