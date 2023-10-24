package com.andreikslpv.mtgcollection.glue.signin.repositories

import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import com.andreikslpv.data.auth.AuthDataRepository
import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.sign_in.domain.repositories.SignInRepository
import javax.inject.Inject

class AdapterAuthRepository @Inject constructor(
    private val authDataRepository: AuthDataRepository,
    private val usersDataRepository: UsersDataRepository,
) : SignInRepository {

    override suspend fun signIn(idToken: String) = authDataRepository.signIn(idToken)

    override suspend fun signInAnonymously() = authDataRepository.signInAnonymously()

    override fun getCurrentUser(): AccountFeatureEntity? {
        val currentUser = authDataRepository.getCurrentUser()
        return if (currentUser == null) null
        else AccountFeatureEntity(
            uid = currentUser.uid,
            email = currentUser.email,
            displayName = currentUser.displayName,
            photoUrl = currentUser.photoUrl
        )
    }

    override suspend fun createUser(uid: String) = usersDataRepository.createUserInDb(uid)

    override suspend fun getPrivacyPolicy() = authDataRepository.getPrivacyPolicy()
}