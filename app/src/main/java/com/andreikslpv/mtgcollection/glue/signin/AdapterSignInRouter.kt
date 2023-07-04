package com.andreikslpv.mtgcollection.glue.signin

import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.sign_in.presentation.SignInRouter
import javax.inject.Inject

class AdapterSignInRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : SignInRouter {

    override fun launchSignUp(email: String) {
//        val screen = SignUpFragment.Screen(email)
//        globalNavComponentRouter.launch(R.id.signUpFragment, screen)
    }

    override fun launchMain() {
        globalNavComponentRouter.startTabs()
    }

}