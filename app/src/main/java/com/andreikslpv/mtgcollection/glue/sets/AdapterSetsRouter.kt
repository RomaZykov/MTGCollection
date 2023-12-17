package com.andreikslpv.mtgcollection.glue.sets

import com.andreikslpv.domain_sets.SetsRouter
import com.andreikslpv.domain_sets.entities.SetEntity
import com.andreikslpv.mtgcollection.R
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.presentation_cards.CardsFragment
import javax.inject.Inject

class AdapterSetsRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : SetsRouter {

    override fun launchCards(set: SetEntity) {
        val screen = CardsFragment.Screen(
            setCode = set.code,
            setName = set.name,
        )
        globalNavComponentRouter.launch(R.id.cardsFragment, screen)
    }
}