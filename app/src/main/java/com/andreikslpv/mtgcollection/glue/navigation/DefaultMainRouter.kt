package com.andreikslpv.mtgcollection.glue.navigation

import com.andreikslpv.mtgcollection.R
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.navigation.presentation.MainRouter
import javax.inject.Inject

class DefaultMainRouter @Inject constructor(
    private val navComponentRouter: GlobalNavComponentRouter
) : MainRouter {

    override fun launchCart() {
//        navComponentRouter.launch(R.id.cartListFragment)
    }

}