package com.andreikslpv.domain_cards.repositories

import com.andreikslpv.domain.entities.CardUiEntity


interface CardsRouter {

    /**
     * Launch card details screen.
     */
    fun launchDetails(card: CardUiEntity)

    /**
     * Go back to the previous screen.
     */
    fun goBack()

}