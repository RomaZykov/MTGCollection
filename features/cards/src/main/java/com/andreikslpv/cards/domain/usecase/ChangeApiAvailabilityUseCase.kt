package com.andreikslpv.cards.domain.usecase

import com.andreikslpv.cards.domain.repositories.CardsRepository
import javax.inject.Inject

class ChangeApiAvailabilityUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(newStatus: Boolean) {
        cardsRepository.changeApiAvailability(newStatus)
    }
}