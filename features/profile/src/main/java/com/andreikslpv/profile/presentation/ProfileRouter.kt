package com.andreikslpv.profile.presentation

import com.andreikslpv.common_impl.entities.CardFeatureModel

interface ProfileRouter {

    /**
     * Go back to the previous screen.
     */
    fun goBack()

    /**
     * Launch Settings screen.
     */
    fun launchSettings()

    /**
     * Launch card details screen.
     */
    fun launchDetails(card: CardFeatureModel)

}