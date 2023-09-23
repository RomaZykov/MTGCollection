package com.andreikslpv.cards.presentation

import com.andreikslpv.common_impl.entities.CardFeatureModel

interface CardsRouter {

    /**
     * Launch card details screen.
     */
    fun launchDetails(card: CardFeatureModel)

    /**
     * Go back to the previous screen.
     */
    fun goBack()

}