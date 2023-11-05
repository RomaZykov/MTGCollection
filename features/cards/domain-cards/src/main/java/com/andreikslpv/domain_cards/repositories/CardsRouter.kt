package com.andreikslpv.domain_cards.repositories

import com.andreikslpv.domain.entities.CardModel


interface CardsRouter {

    /**
     * Launch card details screen.
     */
    fun launchDetails(card: CardModel)

    /**
     * Go back to the previous screen.
     */
    fun goBack()

}