package com.andreikslpv.domain_sets

import com.andreikslpv.domain_sets.entities.SetEntity

interface SetsRouter {

    /**
     * Launch Cards screen.
     * @param set pre-fill code set field on the cards screen
     */
    fun launchCards(set: SetEntity)

}