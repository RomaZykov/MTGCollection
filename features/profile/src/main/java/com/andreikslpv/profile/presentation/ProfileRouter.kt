package com.andreikslpv.profile.presentation

import com.andreikslpv.common_impl.entities.CardFeatureModel

interface ProfileRouter {

    /**
     * Go back to the previous screen.
     */
    fun goBack()

    /**
     * Close all screens and launch the initial screen.
     */
    fun restartApp()

    /**
     * Launch Settings screen.
     */
    fun launchSettings()

    /**
     * Launch card details screen.
     */
    fun launchDetails(card: CardFeatureModel)

}