package com.andreikslpv.mtgcollection.glue.navigation

import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.andreikslpv.domain_users.UsersRepository
import com.andreikslpv.navigation.domain.repositories.MainRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class AdapterMainRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository,
    private val crashlytics: FirebaseCrashlytics,
) : MainRepository {

    override fun getCurrentUserUid() = authRepository.getCurrentUser()?.uid

    override fun getAuthState() = authRepository.getAuthState()

    override fun startObserveUserInDb(uid: String) = usersRepository.startObserveUserInDb(uid)

    override fun stopObserveUserInDb() = usersRepository.stopObserveUserInDb()

    override fun sendErrorToCrashlytics(error: Throwable) = crashlytics.recordException(error)

}