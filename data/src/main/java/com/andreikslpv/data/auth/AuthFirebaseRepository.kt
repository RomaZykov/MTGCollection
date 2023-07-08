package com.andreikslpv.data.auth

import android.net.Uri
import com.andreikslpv.common.Constants.ERROR_AUTH
import com.andreikslpv.common.Response
import com.andreikslpv.data.auth.entities.AccountDataEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthFirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthDataRepository {

    override suspend fun signIn(idToken: String?) = flow {
        try {
            emit(Response.Loading)
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            authResult.additionalUserInfo?.apply {
                emit(Response.Success(isNewUser))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_AUTH))
        }
    }

    override fun signOut() = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
            //emit(Response.Success())
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_AUTH))
        }
    }

    override fun getAuthState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun getCurrentUser() = auth.currentUser.toAccount()

    override suspend fun deleteUser(idToken: String?) = flow {
        try {
            emit(Response.Loading)
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.currentUser?.reauthenticate(credential)?.await().also {
                auth.currentUser?.delete()!!.await().also {
                    emit(Response.Success(true))
                }
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_AUTH))
        }
    }

    override suspend fun editUserName(newName: String) = flow {
        try {
            emit(Response.Loading)
            val profileUpdates = userProfileChangeRequest { displayName = newName }
            auth.currentUser?.updateProfile(profileUpdates)?.await().also {
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_AUTH))
        }
    }

    override suspend fun changeUserPhoto(uri: Uri) = flow {
        try {
            emit(Response.Loading)
            val profileUpdates = userProfileChangeRequest { photoUri = uri }
            auth.currentUser?.updateProfile(profileUpdates)?.await().also {
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_AUTH))
        }
    }

    private fun FirebaseUser?.toAccount(): AccountDataEntity? {
        return if (this == null) null
        else {
            AccountDataEntity(
                uid = this.uid,
                email = this.email,
                displayName = this.displayName,
                photoUrl = this.photoUrl
            )
        }
    }

}