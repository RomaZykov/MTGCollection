package com.andreikslpv.data_sets.datasource

import com.andreikslpv.data_sets.entities.TypeOfSet
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TypesInMemoryDataSource @Inject constructor() : TypesDataSource {

    private val typesOfSet2 = listOf(
        TypeOfSet("core", "Core"),
        TypeOfSet("expansion", "Expansion"),
        // Supplemental sets
        TypeOfSet("vanguard", "Vanguard"),
        TypeOfSet("draft_innovation", "Draft innovation"),
        TypeOfSet("archenemy", "Archenemy"),
        TypeOfSet("commander", "Commander"),
        TypeOfSet("planechase", "Planechase"),
        TypeOfSet("un", "Un-sets"),
        // Introductory sets
        TypeOfSet("starter", "Starter"),
        // Compilations/reprint sets
        TypeOfSet("duel_deck", "Duel Decks"),
        TypeOfSet("from_the_vault", "From the Vault"),
        TypeOfSet("premium_deck", "Premium Deck Series"),
        TypeOfSet("masters", "Masters Series"),
        TypeOfSet("reprint", "Reprint"),
        // Masterpiece Series
        TypeOfSet("masterpiece", "Masterpiece Series"),
        //
        TypeOfSet("box", "Box"),
        TypeOfSet("promo", "Promo"),
        TypeOfSet("memorabilia", "Memorabilia"),
    )

    private val typesOfSet = MutableStateFlow(getNames())

    private fun getNames() = typesOfSet2.map { it.name }

    override suspend fun getTypeNames() = typesOfSet

    override fun getTypeCodeByName(name: String): String {
        val type = typesOfSet2.filter { it.name == name }
        return if (type.isNotEmpty()) type[0].code else ""
    }
}