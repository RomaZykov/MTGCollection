package com.andreikslpv.navigation.domain.usecase

import com.andreikslpv.navigation.domain.repositories.MainRepository
import com.andreikslpv.navigation.presentation.MainRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartObserveUserUseCase @Inject constructor(
    private val mainRepository: MainRepository,
    private val router: MainRouter,
) {

    fun execute() = flow {
        mainRepository.getAuthState().collect { isUserSignedOut ->
            mainRepository.getCurrentUserUid()?.let { uid ->
                if (uid.isNotBlank()) mainRepository.startObserveUserInDb(uid)
            }
            if (isUserSignedOut)
                withContext(Dispatchers.Main) {
                    router.restartApp()
                }
            emit(isUserSignedOut)
        }
    }
}