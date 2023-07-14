package com.andreikslpv.common_impl

import androidx.fragment.app.FragmentActivity

/**
 * This interface indicates that the implementation needs to be aware of
 * activity lifecycle.
 */
interface ActivityRequired {

    fun onActivityCreated(activity: FragmentActivity)

    fun onActivityStarted()

    fun onActivityStopped()

    fun onActivityDestroyed()

}