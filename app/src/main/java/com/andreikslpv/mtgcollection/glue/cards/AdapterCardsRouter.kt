package com.andreikslpv.mtgcollection.glue.cards

import com.andreikslpv.cards.presentation.CardsRouter
import com.andreikslpv.common.AppRestarter
import com.andreikslpv.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterCardsRouter @Inject constructor(
    private val appRestarter: AppRestarter,
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : CardsRouter {

    override fun launchDetails(cardId: String) {
        TODO("Not yet implemented")
    }

    override fun goBack() {
        globalNavComponentRouter.pop()
    }
}