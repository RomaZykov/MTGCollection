package com.andreikslpv.domain_sets.entities

data class TypeOfSetFirebaseEntity(
    override val code: String = "",
    override val name: String = "",
    override val countOfSet: Int = 0,
): TypeOfSetEntity {

    constructor(typeOfSet: TypeOfSetEntity?) : this(
        code = typeOfSet?.code ?: "",
        name = typeOfSet?.name ?: "",
        countOfSet = typeOfSet?.countOfSet ?: 0,
    )
}