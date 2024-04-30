package com.andreikslpv.domain_sets.entities

interface SetEntity {
    val arenaCode: String?
    val block: String?
    val blockCode: String?
    val cardCount: Int
    val code: String
    val digital: Boolean
    val foilOnly: Boolean
    val iconSvgUri: String
    val id: String
    val mtgoCode: String?
    val name: String
    val nonfoilOnly: Boolean
    val objectX: String
    val parentSetCode: String?
    val printedSize: Int?
    val releasedAt: String?
    val scryfallUri: String
    val searchUri: String
    val setType: String
    val tcgplayerId: Int?
    val uri: String
}