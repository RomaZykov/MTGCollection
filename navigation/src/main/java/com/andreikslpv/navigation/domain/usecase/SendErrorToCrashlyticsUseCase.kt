package com.andreikslpv.navigation.domain.usecase

import com.andreikslpv.navigation.domain.repositories.MainRepository
import javax.inject.Inject

class SendErrorToCrashlyticsUseCase @Inject constructor(
    private val mainRepository: MainRepository,
) {

    operator fun invoke(error: Throwable) = mainRepository.sendErrorToCrashlytics(error)
}