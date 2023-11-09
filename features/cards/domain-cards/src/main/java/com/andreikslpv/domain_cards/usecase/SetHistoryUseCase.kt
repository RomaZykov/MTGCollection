package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import javax.inject.Inject

class SetHistoryUseCase @Inject constructor(
    private val cardsExternalRepository: CardsExternalRepository,
) {
    private val historyMaxCount = 10

    fun execute(card: CardEntity) {
        val uid = cardsExternalRepository.getCurrentUserUid()
        if (uid != null) {
            // получаем текущую историю
            var currentHistory = cardsExternalRepository.getHistory().value
            // удаляем из истории переданный рецепт, чтобы не было повторов в истории
            currentHistory = currentHistory.filter { it.id != card.id }
            // добавляем в начало списка переданный рецепт
            var newHistory = listOf(card)
            // добавляем к списку sublist из истории без 0 элемента
            if (currentHistory.isNotEmpty())
                newHistory = newHistory.plus(currentHistory.subList(0, currentHistory.count()))
            // если длина списка больше максимума, то берем только первые до максимума
            if (newHistory.count() > historyMaxCount)
                newHistory = newHistory.take(historyMaxCount)
            cardsExternalRepository.setHistory(uid, newHistory)
        }
    }
}