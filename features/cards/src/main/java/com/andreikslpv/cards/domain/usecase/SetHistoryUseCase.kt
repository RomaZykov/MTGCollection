package com.andreikslpv.cards.domain.usecase

import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.cards.domain.repositories.CardsRepository
import javax.inject.Inject

class SetHistoryUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {
    private val historyMaxCount = 10

    fun execute(card: CardFeatureModel) {
        val user = cardsRepository.getCurrentUser()
        if (user != null) {
            // получаем текущую историю
            var currentHistory = cardsRepository.getHistory().value
            // удаляем из истории переданный рецепт, чтобы не было повторов в истории
            currentHistory = currentHistory.filter { it != card }
            // добавляем в начало списка переданный рецепт
            var newHistory = listOf(card)
            // добавляем к списку sublist из истории без 0 элемента
            if (currentHistory.isNotEmpty())
                newHistory = newHistory.plus(currentHistory.subList(0, currentHistory.count()))
            // если длина списка больше максимума, то берем только первые до максимума
            if (newHistory.count() > historyMaxCount)
                newHistory = newHistory.take(historyMaxCount)
            cardsRepository.setHistory(user.uid, newHistory)
        }
    }
}