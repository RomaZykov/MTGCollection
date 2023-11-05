package com.andreikslpv.mtgcollection.glue.cards

import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_cards.repositories.CardsRouter
import com.andreikslpv.mtgcollection.R
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.presentation_cards.DetailsFragment
import javax.inject.Inject

class AdapterCardsRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : CardsRouter {

    override fun launchDetails(card: CardModel) {
        val screen = DetailsFragment.Screen(card)
        globalNavComponentRouter.launch(R.id.detailsFragment, screen)
    }

    override fun goBack() {
        globalNavComponentRouter.pop()
    }
}