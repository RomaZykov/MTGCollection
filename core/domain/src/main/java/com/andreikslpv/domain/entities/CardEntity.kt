package com.andreikslpv.domain.entities

interface CardEntity {
    // Converted mana cost. Always a number
    val cmc: Double?

    // The card’s color identity, by color code. [“Red”, “Blue”] becomes [“R”, “U”].
    // A card’s color identity includes colors from the card’s rules text.
    val colorIdentity: List<String>?

    // The card colors. Usually this is derived from the casting cost,
    // but some cards are special (like the back of dual sided cards and Ghostfire).
    val colors: List<String>?

    // Foreign language names for the card, if this card in this set was printed in another language.
    // An array of objects, each object having ‘language’, ‘name’ and ‘multiverseid’ keys.
    // Not available for all sets.
    val foreignNamesList: List<ForeignNameEntity>

    // A unique id for this card.
    val id: String

    // The image url for a card.
    val imageUrl: String?

    // The mana cost of this card. Consists of one or more mana symbols. (use cmc and colors to query)
    val manaCost: String?

    // The multiverseid of the card on Wizard’s Gatherer web page.
    val multiverseid: String?

    // The card name. For split, double-faced and flip cards, just the name of one side of the card.
    // Basically each ‘sub-card’ has its own record.
    val name: String?

    // The card number, in the following format:
    // 1  -> 00001
    // 25 -> 00025
    val orderedNumber: String

    // The power of the card. This is only present for creatures.
    // This is a string, not an integer, because some cards have powers like: “1+*”
    val power: String?

    // The sets that this card was printed in, expressed as an array of set codes.
    val printings: List<String>?

    // The rarity of the card. Examples: Common, Uncommon, Rare, Mythic Rare, Special, Basic Land
    val rarity: String?

    // The set the card belongs to (set code).
    val set: String?

    // The set the card belongs to.
    val setName: String?

    // The subtypes of the card. These appear to the right of the dash in a card type.
    // Usually each word is its own subtype.
    // Example values: Trap, Arcane, Equipment, Aura, Human, Rat, Squirrel, etc.
    val subtypes: List<String>?

    // The oracle text of the card.
    val text: String?

    // The toughness of the card.
    val toughness: String?

    // The card type. This is the type you would see on the card if printed today.
    // Note: The dash is a UTF8 ‘long dash’ as per the MTG rules
    val type: String?

    // The types of the card. These appear to the left of the dash in a card type.
    // Example values: Instant, Sorcery, Artifact, Creature, Enchantment, Land, Planeswalker
    val types: List<String>?

    // A user can have several cards of the same type,
    // but in different languages and in different conditions.
    // This field contains a list of available cards of this type.
    val availableCards: MutableList<AvailableCardEntity>

}