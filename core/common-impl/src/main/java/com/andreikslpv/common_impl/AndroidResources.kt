package com.andreikslpv.common_impl

import android.content.Context
import com.andreikslpv.common.Resources

/**
 * Default implementation of [Resources] which fetches strings from an application context.
 */
class AndroidResources(
    private val applicationContext: Context,
) : Resources {

    override fun getString(id: Int): String {
        return applicationContext.getString(id)
    }

    override fun getString(id: Int, vararg placeholders: Any): String {
        return applicationContext.getString(id, placeholders)
    }

}