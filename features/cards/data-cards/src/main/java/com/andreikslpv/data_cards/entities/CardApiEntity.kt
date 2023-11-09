package com.andreikslpv.data_cards.entities

import com.andreikslpv.data_cards.dto.ForeignName
import com.andreikslpv.data_cards.dto.Legality
import com.andreikslpv.data_cards.dto.Ruling
import com.andreikslpv.domain.entities.AvailableCardEntity
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.ForeignNameEntity
import com.google.gson.annotations.SerializedName

/**
 * API entity of Card. This structure differs from the [CardEntity]
 * interface so some properties are overridden in the class body, not in the
 * constructor:
 * - Constructor of this data class specifies the network JSON structure.
 * - All properties outside constructor are overridden properties of the [CardEntity] interface
 */

data class CardApiEntity(
    @SerializedName("artist") val artist: String?,
    @SerializedName("cmc") override val cmc: Double?,
    @SerializedName("colorIdentity") override val colorIdentity: List<String>?,
    @SerializedName("colors") override val colors: List<String>?,
    @SerializedName("flavor") val flavor: String?,
    @SerializedName("foreignNames") val foreignNames: List<ForeignName>?,
    @SerializedName("id") override val id: String,
    @SerializedName("imageUrl") override val imageUrl: String?,
    @SerializedName("layout") val layout: String?,
    @SerializedName("legalities") val legalities: List<Legality>?,
    @SerializedName("manaCost") override val manaCost: String?,
    @SerializedName("multiverseid") override val multiverseid: String?,
    @SerializedName("name") override val name: String?,
    @SerializedName("number") val number: String?,
    @SerializedName("originalText") val originalText: String?,
    @SerializedName("originalType") val originalType: String?,
    @SerializedName("power") override val power: String?,
    @SerializedName("printings") override val printings: List<String>?,
    @SerializedName("rarity") override val rarity: String?,
    @SerializedName("rulings") val rulings: List<Ruling>?,
    @SerializedName("set") override val set: String?,
    @SerializedName("setName") override val setName: String?,
    @SerializedName("subtypes") override val subtypes: List<String>?,
    @SerializedName("text") override val text: String?,
    @SerializedName("toughness") override val toughness: String?,
    @SerializedName("type") override val type: String?,
    @SerializedName("types") override val types: List<String>?,
    @SerializedName("watermark") val watermark: String?,
    override val availableCards: MutableList<AvailableCardEntity> = mutableListOf(),
) : CardEntity {

    override val foreignNamesList: List<ForeignNameEntity>
        get() = foreignNames?.map {
            ForeignNameEntity(
                imageUrl = it.imageUrl ?: "",
                language = it.language,
                multiverseid = it.multiverseid,
                name = it.name,
            )
        } ?: listOf()

    override val orderedNumber
        get() = number?.toOrderedNumber() ?: ""

    private fun String.toOrderedNumber(): String {
        val result = this.dropLastWhile { !it.isDigit() }
        return "0".repeat(5 - result.length) + this
    }

}
