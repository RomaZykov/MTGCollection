package com.andreikslpv.mtgcollection.glue.auth

import com.andreikslpv.domain.entities.CardPreviewUiEntity
import com.andreikslpv.domain_auth.repositories.AuthRouter
import com.andreikslpv.mtgcollection.R
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.presentation_cards.DetailsFragment
import javax.inject.Inject

class AdapterAuthRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : AuthRouter {

    override fun launchMain() {
        globalNavComponentRouter.startTabs()
    }

    override fun launchSettings() {
        globalNavComponentRouter.launch(R.id.settingsFragment)
    }

    override fun launchDetails(card: CardPreviewUiEntity) {
        val screen = DetailsFragment.Screen(card)
        globalNavComponentRouter.launch(R.id.detailsFragment, screen)
    }

}