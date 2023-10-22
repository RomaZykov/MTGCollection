package com.andreikslpv.mtgcollection.glue.profile

import com.andreikslpv.cards.presentation.DetailsFragment
import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.mtgcollection.R
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.profile.presentation.ProfileRouter
import javax.inject.Inject

class AdapterProfileRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : ProfileRouter {

    override fun goBack() {
        globalNavComponentRouter.pop()
    }

    override fun launchSettings() {
        globalNavComponentRouter.launch(R.id.settingsFragment)
    }

    override fun launchDetails(card: CardFeatureModel) {
        val screen = DetailsFragment.Screen(card)
        globalNavComponentRouter.launch(R.id.detailsFragment, screen)
    }

}