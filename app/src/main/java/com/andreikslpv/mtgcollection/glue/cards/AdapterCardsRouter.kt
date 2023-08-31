package com.andreikslpv.mtgcollection.glue.cards

import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.presentation.CardsRouter
import com.andreikslpv.cards.presentation.DetailsFragment
import com.andreikslpv.common.AppRestarter
import com.andreikslpv.mtgcollection.R
import com.andreikslpv.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterCardsRouter @Inject constructor(
    private val appRestarter: AppRestarter,
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : CardsRouter {

    override fun launchDetails(card: CardFeatureModel) {
        val screen = DetailsFragment.Screen(card)
        globalNavComponentRouter.launch(R.id.detailsFragment, screen)
    }

    override fun goBack() {
        globalNavComponentRouter.pop()
    }
}