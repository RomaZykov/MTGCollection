package com.andreikslpv.navigation.domain.usecase

import com.andreikslpv.navigation.domain.repositories.MainRepository
import com.andreikslpv.navigation.presentation.MainRouter
import javax.inject.Inject

class LaunchMainIfUserAuthenticatedUseCase @Inject constructor(
    private val mainRepository: MainRepository,
    private val router: MainRouter,
) {

    fun execute() {
        if (mainRepository.getCurrentUserUid() != null) router.launchMain()
    }
}