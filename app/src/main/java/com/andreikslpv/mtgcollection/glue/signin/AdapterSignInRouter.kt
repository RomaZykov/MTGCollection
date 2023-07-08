package com.andreikslpv.mtgcollection.glue.signin

import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.sign_in.presentation.SignInRouter
import javax.inject.Inject

class AdapterSignInRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : SignInRouter {

    override fun launchMain() {
        globalNavComponentRouter.startTabs()
    }

}