package com.andreikslpv.domain.entities

interface CardPreviewEntity {

    /** The name of the illustrator of this card. Newly spoiled cards may not have this field yet.
     *
     * Сортировка карточек по имени исполнителя на лицевой стороне: A → Z
     */
    val artist: String?

    /** The Scryfall ID for the card back design present on this card. */
    val cardBackId: String

    /** The card’s mana value. Note that some funny cards have fractional mana costs.
     *
     * Сортировка карт по их значению маны: 0 → наибольшее
     */
    val cmc: Double

    /** This card’s collector number. Note that collector numbers can contain non-numeric characters,
     * such as letters or ★.
     *
     * Доступна сортировка карт по их набору и коллекционному номеру: AAA / # 1 → ZZZ / # 999
     */
    val collectorNumber: String

    /** This card’s color identity.
     *
     * Сортировка карт по color и color identity: WUBRG → многоцветный → бесцветный
     */
    val colorIdentity: List<String>

    /** This card’s colors, if the overall card has colors defined by the rules.
     * Otherwise the colors will be on the card_faces objects, see below.
     *
     * Сортировка карт по color и color identity: WUBRG → многоцветный → бесцветный
     */
    val colors: List<String>?

    /** This card’s overall rank/popularity on EDHREC. Not all cards are ranked.
     *
     * Сортировка карт по их рейтингу EDHREC: самый низкий → самый высокий
     */
    val edhrecRank: Int?

    /** An array of computer-readable flags that indicate if this card can come in foil, nonfoil, or etched finishes. */
    val finishes: List<String>

    /**  */
    val foil: Boolean?

    /** True if this card’s imagery is high resolution. */
    val highresImage: Boolean

    /** A unique ID for this card in Scryfall’s database. */
    val id: String

    /** A computer-readable indicator for the state of this card’s image, one of:
     *
     * missing На карте нет изображения, или изображение обрабатывается. Это значение должно
     * быть временным только для очень новых карт.
     *
     * placeholder У Scryfall нет изображения этой карты, но мы знаем, что она существует,
     * и тем временем мы загрузили заполнитель. Это значение чаще всего встречается на локализованных картах.
     *
     * lowres Изображение карты некачественное, либо потому, что оно было просто испорчено,
     * либо у нас пока нет лучшей фотографии для него.
     *
     * highres_scan На этой карте есть изображение от сканера в полном разрешении. Четкое и глянцевое!
     */
    val imageStatus: String

    /** 745 × 1040 PNG
    A transparent, rounded full card PNG. This is the best image to use for videos or other high-quality content. */
    val imageDetailUri: String

    /** 488 × 680 JPG
    A medium-sized full card image */
    val imagePreviewUri: String

    /** A language code for this printing. */
    val lang: String

    /** The mana cost for this card. This value will be any empty string "" if the cost is absent.
     * Remember that per the game rules, a missing mana cost and a mana cost of {0} are different values. Multi-faced cards will report this value in card faces. */
    val manaCost: String?

    /** The name of this card. If this card has multiple faces, this field will contain both names separated by ␣//␣.
     *
     * Доступна сортировка карт по названию, A → Z
     */
    val name: String

    /**  */
    val nonfoil: Boolean?

    /** This card’s rank/popularity on Penny Dreadful. Not all cards are ranked.
     *
     * Сортировка карт по их ужасному рейтингу: самый низкий → самый высокий
     */
    val pennyRank: Int?

    /** This card’s power, if any. Note that some cards have powers that are not numeric, such as *.
     *
     * Сортировка карт по их силе: null → наивысшая
     */
    val power: String?

    /** Daily price information for this card in eur, as string.
     *
     * Сортировка карт по самой низкой известной цене в евро: 0.01 → самая высокая, null Последние
     */
    val priceInEur: String?

    /** Daily price information for this card in tix as string.
     *
     * Сортировка карт по самой низкой известной цене TIX: 0.01 → самая высокая, null последняя
     */
    val priceInTix: String?

    /** Daily price information for this card in usd as string.
     *
     * Сортировка карт по самой низкой известной цене в долларах США: 0.01 → самая высокая, null последняя
     */
    val priceInUsd: String?

    /** Имя карты на не английском, если карта доступна на других языках */
    val printedName: String?

    /** An object providing URIs to this card’s listing on major marketplaces. Omitted if the card is unpurchaseable. */
    val cardPurchaseUrisEntity: CardPurchaseUrisEntity?

    /** This card’s rarity. One of common, uncommon, rare, special, mythic, or bonus.
     *
     * Сортировка карт по их редкости: common → mythic
     */
    val rarity: String

    /** An object providing URIs to this card’s listing on other Magic: The Gathering online resources. */
    val cardRelatedUrisEntity: CardRelatedUrisEntity

    /** The date this card was first released.
     *
     * Сортировка карт по дате выпуска: Самые новые → Самые старые
     */
    val releasedAt: String

    /** This card’s set code.
     *
     * Доступна сортировка карт по их набору и коллекционному номеру: AAA / # 1 → ZZZ / # 999
     */
    val setCode: String

    /** This card’s toughness, if any. Note that some cards have toughnesses that are not numeric, such as *.
     *
     * Сортировка карт по их прочности: null → наивысшая
     */
    val toughness: String?

    /** A URI where you can retrieve a full object describing this card on Scryfall’s API. */
    val uri: String

}