package com.andreikslpv.mtgcollection.glue.sets

import com.andreikslpv.cards.presentation.CardsFragment
import com.andreikslpv.common.AppRestarter
import com.andreikslpv.mtgcollection.R
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.sets.domain.entities.SetFeatureModel
import com.andreikslpv.sets.presentation.SetsRouter
import javax.inject.Inject

class AdapterSetsRouter @Inject constructor(
    private val appRestarter: AppRestarter,
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : SetsRouter {

    override fun launchCards(set: SetFeatureModel) {
//        val screen = CardsFragment.Screen(setCode)
//        globalNavComponentRouter.startTabs(startTabDestinationId = R.id.cardsFragment, screen)

        val screen = CardsFragment.Screen(
            setCode = set.code,
            setName = set.name,
        )
        globalNavComponentRouter.launch(R.id.cardsFragment, screen)
    }
}