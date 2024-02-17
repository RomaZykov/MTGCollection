package com.andreikslpv.domain_sets.entities

data class TypeOfSetUiEntity(
    override val code: String = "",
    override val name: String = "",
    override val countOfSet: Int = 0,
    val uiName: String = "",
) : TypeOfSetEntity {

    constructor(typeOfSet: TypeOfSetEntity?) : this(
        code = typeOfSet?.code ?: "",
        name = typeOfSet?.name ?: "",
        countOfSet = typeOfSet?.countOfSet ?: 0,
        uiName = if (typeOfSet != null) "${typeOfSet.name} (${typeOfSet.countOfSet})"
        else "",
    )
}
