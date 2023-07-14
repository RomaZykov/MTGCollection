package com.andreikslpv.mtgcollection.glue.sets

import com.andreikslpv.common.AppRestarter
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.sets.presentation.SetsRouter
import javax.inject.Inject

class AdapterSetsRouter @Inject constructor(
    private val appRestarter: AppRestarter,
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : SetsRouter {
}