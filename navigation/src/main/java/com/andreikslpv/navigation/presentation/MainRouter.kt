package com.andreikslpv.navigation.presentation

interface MainRouter {

    /**
     * Launch main tabs for already logged-in user.
     */
    fun launchMain()

    /**
     * Close all screens and launch the initial screen.
     */
    fun restartApp()

}