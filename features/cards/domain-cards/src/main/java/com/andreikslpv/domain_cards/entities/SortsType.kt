package com.andreikslpv.domain_cards.entities

import com.andreikslpv.domain_cards.CardsConstants.COLUMN_ARTIST
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_CMC
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_COLLECTOR_NUMBER
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_EDHREC_RANK
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_NAME
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_PENNY_RANK
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_POWER
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_RARITY
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_RELEASED_AT
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_TOUGHNESS

enum class SortsType(val typeApi: String, val columnRoom: String) {
    // Сортировка карт по названию, A → Z
    NAME("name", COLUMN_NAME),

    // Сортировка карт по их набору и коллекционному номеру: AAA / # 1 → ZZZ / # 999
    SET("set", COLUMN_COLLECTOR_NUMBER),

    // Сортировка карт по дате выпуска: Самые новые → Самые старые
    RELEASED("released", COLUMN_RELEASED_AT),

    // Сортировка карт по их редкости: Распространенные → Мифические
    RARITY("rarity", COLUMN_RARITY),

    // Сортировка карт по цвету и цветовой идентичности: WUBRG → многоцветный → бесцветный
    //COLOR("color"),

    // Сортировка карт по самой низкой известной цене в долларах США: 0.01 → самая высокая, null последняя
    //USD("usd"),

    // Сортировка карт по самой низкой известной цене TIX: 0.01 → самая высокая, null последняя
    //TIX("tix"),

    // Сортировка карт по самой низкой известной цене в евро: 0.01 → самая высокая, null Последние
    //EUR("eur"),

    // Сортировка карт по их значению маны: 0 → наибольшее
    CMC("cmc", COLUMN_CMC),

    // Сортировка карт по их силе: null → наивысшая
    POWER("power", COLUMN_POWER),

    // Сортировка карт по их прочности: null → наивысшая
    TOUGHNESS("toughness", COLUMN_TOUGHNESS),

    // Сортировка карт по их рейтингу EDHREC: самый низкий → самый высокий
    EDHREC("edhrec", COLUMN_EDHREC_RANK),

    // Сортировка карт по их ужасному рейтингу: самый низкий → самый высокий
    PENNY("penny", COLUMN_PENNY_RANK),

    // Сортировка карточек по имени исполнителя на лицевой стороне: A → Z
    ARTIST("artist", COLUMN_ARTIST),

    // Сортируйте карточки, как подкасты просматривают наборы, обычно по цвету и CMC,
    // по наименьшей → по наибольшей величине, с карточками Booster Fun в конце
    //REVIEW("review"),

}