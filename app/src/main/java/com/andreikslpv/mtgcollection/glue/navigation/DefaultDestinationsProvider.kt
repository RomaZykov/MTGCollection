package com.andreikslpv.mtgcollection.glue.navigation

import android.content.Context
import com.andreikslpv.mtgcollection.R
import com.andreikslpv.navigation.DestinationsProvider
import com.andreikslpv.navigation.presentation.navigation.NavTab
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultDestinationsProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : DestinationsProvider {

    override fun provideStartDestinationId(): Int {
        return R.id.signInFragment
    }

    override fun provideNavigationGraphId(): Int {
        return R.navigation.nav_graph
    }

    override fun provideMainTabs(): List<NavTab> {
        return listOf(
            NavTab(
                destinationId = R.id.setsFragment,
                title = context.getString(R.string.title_sets),
                iconRes = R.drawable.ic_sets,
            ),
            NavTab(
                destinationId = R.id.cardsFragment,
                title = context.getString(R.string.title_cards),
                iconRes = R.drawable.ic_cards,
            ),
            NavTab(
                destinationId = R.id.profileFragment,
                title = context.getString(R.string.title_profile),
                iconRes = R.drawable.ic_profile,
            )
        )
    }

    override fun provideTabsDestinationId(): Int {
        return R.id.tabsFragment
    }

//    override fun provideCartDestinationId(): Int {
//        return R.id.cartListFragment
//    }
}