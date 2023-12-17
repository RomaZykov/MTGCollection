package com.andreikslpv.mtgcollection.glue.settings

import com.andreikslpv.domain_settings.repositories.SettingsRouter
import com.andreikslpv.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterSettingsRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter,
) : SettingsRouter {

    override fun goBack() = globalNavComponentRouter.pop()

}