package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain_cards.repositories.CardsRepository
import javax.inject.Inject

class ChangeApiAvailabilityUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(newStatus: Boolean) = cardsRepository.changeApiAvailability(newStatus)
}