package com.andreikslpv.navigation.presentation

import com.andreikslpv.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: MainRouter,
) : BaseViewModel() {


}