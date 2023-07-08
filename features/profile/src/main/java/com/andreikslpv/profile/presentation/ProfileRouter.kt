package com.andreikslpv.profile.presentation

interface ProfileRouter {

    /**
     * Go back to the previous screen.
     */
    fun goBack()

    /**
     * Close all screens and launch the initial screen.
     */
    fun restartApp()

}