package com.andreikslpv.sets.presentation

import com.andreikslpv.sets.domain.entities.SetFeatureModel

interface SetsRouter {

    /**
     * Launch Cards screen.
     * @param set pre-fill code set field on the cards screen
     */
    fun launchCards(set: SetFeatureModel)

}