package com.andreikslpv.mtgcollection.glue.signin.repositories

import com.andreikslpv.data.auth.AuthDataRepository
import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.mtgcollection.glue.cards.AccountDataToFeatureModelMapper
import com.andreikslpv.sign_in.domain.repositories.SignInRepository
import javax.inject.Inject

class AdapterAuthRepository @Inject constructor(
    private val authDataRepository: AuthDataRepository,
    private val usersDataRepository: UsersDataRepository,
) : SignInRepository {

    override suspend fun signIn(idToken: String) = authDataRepository.signIn(idToken)

    override suspend fun signInAnonymously() = authDataRepository.signInAnonymously()

    override fun getCurrentUser() =
        AccountDataToFeatureModelMapper.map(authDataRepository.getCurrentUser().value)

    override suspend fun createUser(uid: String) = usersDataRepository.createUserInDb(uid)

    override suspend fun getPrivacyPolicy() = authDataRepository.getPrivacyPolicy()
}