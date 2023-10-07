package com.andreikslpv.mtgcollection.glue.signin.repositories

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.andreikslpv.common.AlreadyInProgressException
import com.andreikslpv.common.CalledNotFromUiException
import com.andreikslpv.common.Constants
import com.andreikslpv.common.Response
import com.andreikslpv.common_impl.ActivityRequired
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import com.andreikslpv.data.auth.AuthDataRepository
import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.mtgcollection.extensions.GoogleSignInContract
import com.andreikslpv.sign_in.domain.repositories.SignInRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdapterAuthRepository @Inject constructor(
    private val authDataRepository: AuthDataRepository,
    private val googleSignInClient: GoogleSignInClient,
    private val usersDataRepository: UsersDataRepository,
) : SignInRepository, ActivityRequired {

    private var isActivityStarted = false
    private var signInLauncher: ActivityResultLauncher<Unit>? = null
    private var completableDeferred: CompletableDeferred<String>? = null

    override suspend fun signIn() = flow {
        try {
            emit(Response.Loading)
            if (!isActivityStarted) throw CalledNotFromUiException()
            val signInLauncher = signInLauncher ?: throw CalledNotFromUiException()
            if (completableDeferred != null) throw AlreadyInProgressException()
            signInLauncher.launch(Unit)

            CompletableDeferred<String>().let {
                completableDeferred = it
                it.await()
            }.also { token ->
                authDataRepository.signIn(token).collect { response ->
                    when (response) {
                        is Response.Loading -> emit(Response.Loading)
                        is Response.Failure -> emit(Response.Failure(response.errorMessage))
                        is Response.Success -> emit(Response.Success(response.data))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_AUTH))
        }

    }

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

    override suspend fun createUser(uid: String) = flow {
        usersDataRepository.createUserInDb(uid).collect { response ->
            when (response) {
                is Response.Loading -> emit(Response.Loading)
                is Response.Failure -> emit(Response.Failure(response.errorMessage))
                is Response.Success -> emit(Response.Success(response.data))
            }
        }
    }

    override suspend fun getPrivacyPolicy() = authDataRepository.getPrivacyPolicy()

    // ----- ActivityRequired impl

    override fun onActivityCreated(activity: FragmentActivity) {
        signInLauncher =
            activity.registerForActivityResult(GoogleSignInContract(googleSignInClient)) {
                if (it is Response.Success) {
                    completableDeferred?.complete(it.data)
                } else if (it is Response.Failure) {
                    completableDeferred?.completeExceptionally(Throwable(it.errorMessage))
                }
                completableDeferred = null
            }
    }

    override fun onActivityStarted() {
        isActivityStarted = true
    }

    override fun onActivityStopped() {
        isActivityStarted = false
    }

    override fun onActivityDestroyed() {
        this.signInLauncher = null
    }

    // --- equals/hash-code for correct working of Activity Result API

    override fun equals(other: Any?): Boolean {
        return other?.javaClass?.name?.equals(javaClass.name) ?: false
    }

    override fun hashCode(): Int {
        return javaClass.name.hashCode()
    }
}