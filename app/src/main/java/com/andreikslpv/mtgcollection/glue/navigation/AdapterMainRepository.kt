package com.andreikslpv.mtgcollection.glue.navigation

import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.andreikslpv.domain_users.UsersRepository
import com.andreikslpv.navigation.domain.repositories.MainRepository
import javax.inject.Inject

class AdapterMainRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository,
) : MainRepository {

    override fun getCurrentUserUid() = authRepository.getCurrentUser()?.uid

    override fun getAuthState() = authRepository.getAuthState()

    override fun startObserveUserInDb(uid: String) = usersRepository.startObserveUserInDb(uid)

}