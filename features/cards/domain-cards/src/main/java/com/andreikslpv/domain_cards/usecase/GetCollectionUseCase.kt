package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val cardsExternalRepository: CardsExternalRepository,
) {

    fun execute(): MutableStateFlow<List<String>> {
        val uid = cardsExternalRepository.getCurrentUserUid()
        return if (uid != null) cardsExternalRepository.getCollection()
        else MutableStateFlow(emptyList())
    }
}