package com.andreikslpv.cards.domain.usecase

import com.andreikslpv.cards.domain.repositories.CardsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetAvailableUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(): MutableStateFlow<List<String>> {
        val user = cardsRepository.getCurrentUser()
        return if (user != null) cardsRepository.getAvailable()
        else MutableStateFlow(emptyList())
    }
}