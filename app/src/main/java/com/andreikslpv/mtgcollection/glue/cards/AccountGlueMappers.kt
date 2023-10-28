package com.andreikslpv.mtgcollection.glue.cards

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import com.andreikslpv.data.auth.entities.AccountDataEntity

object AccountDataToFeatureModelMapper : BaseMapper<AccountDataEntity, AccountFeatureEntity> {
    override fun map(type: AccountDataEntity?): AccountFeatureEntity {
        return AccountFeatureEntity(
            uid = type?.uid ?: "",
            email = type?.email,
            displayName = type?.displayName,
            photoUrl = type?.photoUrl,
            isAnonymous = type?.isAnonymous ?: true,
        )
    }
}