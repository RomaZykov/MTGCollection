package com.andreikslpv.mtgcollection.glue.navigation

import com.andreikslpv.data.auth.AuthDataRepository
import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.mtgcollection.glue.cards.AccountDataToFeatureModelMapper
import com.andreikslpv.navigation.domain.repositories.MainRepository
import javax.inject.Inject

class AdapterMainRepository @Inject constructor(
    private val authDataRepository: AuthDataRepository,
    private val usersDataRepository: UsersDataRepository,
) : MainRepository {

    override fun getCurrentUser() =
        AccountDataToFeatureModelMapper.map(authDataRepository.getCurrentUser())

    override fun getAuthState() = authDataRepository.getAuthState()

    override fun startObserveUserInDb(uid: String) = usersDataRepository.startObserveUserInDb(uid)

}