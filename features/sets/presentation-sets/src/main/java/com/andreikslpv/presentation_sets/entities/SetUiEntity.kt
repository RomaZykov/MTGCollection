package com.andreikslpv.presentation_sets.entities

import com.andreikslpv.domain_sets.entities.SetEntity

data class SetUiEntity(
    override val arenaCode: String?,
    override val block: String?,
    override val blockCode: String?,
    override val cardCount: Int,
    override val code: String,
    override val digital: Boolean,
    override val foilOnly: Boolean,
    override val iconSvgUri: String,
    override val id: String,
    override val mtgoCode: String?,
    override val name: String,
    override val nonfoilOnly: Boolean,
    override val objectX: String,
    override val parentSetCode: String?,
    override val printedSize: Int?,
    override val releasedAt: String?,
    override val scryfallUri: String,
    override val searchUri: String,
    override val setType: String,
    override val tcgplayerId: Int?,
    override val uri: String,
) : SetEntity {

    constructor(set: SetEntity) : this(
        arenaCode = set.arenaCode,
        block = set.block,
        blockCode = set.blockCode,
        cardCount = set.cardCount,
        code = set.code,
        digital = set.digital,
        foilOnly = set.foilOnly,
        iconSvgUri = set.iconSvgUri,
        id = set.id,
        mtgoCode = set.mtgoCode,
        name = set.name,
        nonfoilOnly = set.nonfoilOnly,
        objectX = set.objectX,
        parentSetCode = set.parentSetCode,
        printedSize = set.printedSize,
        releasedAt = set.releasedAt,
        scryfallUri = set.scryfallUri,
        searchUri = set.searchUri,
        setType = set.setType,
        tcgplayerId = set.tcgplayerId,
        uri = set.uri,
    )
}