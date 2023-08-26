@file:Suppress("DEPRECATION", "UNCHECKED_CAST")

package com.andreikslpv.presentation

import androidx.fragment.app.Fragment

/**
 * Default arg name for holding screen args in fragments. Use this name
 * if you want to integrate your navigation with the core.
 */
const val ARG_SCREEN = "screen"

/**
 * Get screen args attached to the [Fragment].
 */
fun <T : BaseScreen> Fragment.args(): T? {
    return try {
        requireArguments().getSerializable(ARG_SCREEN) as? T
    } catch (e: Exception) {
        null
    }
}
