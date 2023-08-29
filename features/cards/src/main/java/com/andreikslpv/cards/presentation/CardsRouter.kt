package com.andreikslpv.cards.presentation

import com.andreikslpv.cards.domain.entities.CardFeatureModel

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