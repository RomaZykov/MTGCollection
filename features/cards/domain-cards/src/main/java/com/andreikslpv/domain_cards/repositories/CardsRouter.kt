package com.andreikslpv.domain_cards.repositories

import com.andreikslpv.domain.entities.CardPreviewUiEntity


interface CardsRouter {

    /**
     * Launch card details screen.
     */
    fun launchDetails(card: CardPreviewUiEntity)

    /**
     * Go back to the previous screen.
     */
    fun goBack()

}