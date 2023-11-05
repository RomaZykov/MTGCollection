package com.andreikslpv.domain_auth.repositories

import com.andreikslpv.domain.entities.CardModel

interface AuthRouter {

    /**
     * Launch main tabs for already logged-in user.
     */
    fun launchMain()

    /**
     * Launch Settings screen.
     */
    fun launchSettings()

    /**
     * Launch card details screen.
     */
    fun launchDetails(card: CardModel)

}