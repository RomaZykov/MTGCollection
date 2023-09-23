package com.andreikslpv.profile.domain.usecase

import com.andreikslpv.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {

    fun execute(): MutableStateFlow<List<String>> {
        val user = profileRepository.getCurrentUser()
        return if (user != null) profileRepository.getCollection()
        else MutableStateFlow(emptyList())
    }
}