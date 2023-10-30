package com.andreikslpv.cards.presentation


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