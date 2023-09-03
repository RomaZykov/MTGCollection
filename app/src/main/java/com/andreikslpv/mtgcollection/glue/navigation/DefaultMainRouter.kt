package com.andreikslpv.mtgcollection.glue.navigation

import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.navigation.presentation.MainRouter
import javax.inject.Inject

class DefaultMainRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : MainRouter {

    override fun launchMain() {
        globalNavComponentRouter.startTabs()
    }

}