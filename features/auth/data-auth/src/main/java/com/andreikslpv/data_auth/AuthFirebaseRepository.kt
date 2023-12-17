package com.andreikslpv.data_auth

import android.net.Uri
import androidx.core.net.toUri
import com.andreikslpv.common.Constants.UNKNOWN_ERROR
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.data_auth.constants.StorageConstants.PATH_USERS
import com.andreikslpv.domain_auth.entities.AccountDataEntity
import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthFirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
) : AuthRepository {

    override suspend fun signIn(idToken: String) = flow {
        try {
            emit(Response.Loading)
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            authResult.additionalUserInfo?.apply {
                emit(Response.Success(isNewUser))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override suspend fun signInAnonymously() = flow {
        try {
            emit(Response.Loading)
            val authResult = auth.signInAnonymously().await()
            authResult.additionalUserInfo?.apply {
                emit(Response.Success(isNewUser))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override suspend fun linkAnonymousWithCredential(idToken: String) = flow {
        try {
            emit(Response.Loading)
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.currentUser?.linkWithCredential(credential)?.await()
            if (authResult != null) emit(Response.Success(authResult.user.toAccount()))
            else emit(Response.Success(null))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override fun signOut() = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override fun getAuthState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
                .onClosed { error ->
                    Core.loadStateHandler.setLoadState(
                        Response.Failure(
                            error ?: Throwable(UNKNOWN_ERROR)
                        )
                    )
                }
                .onSuccess { Core.loadStateHandler.setLoadState(Response.Success(true)) }
                .onFailure { error ->
                    Core.loadStateHandler.setLoadState(
                        Response.Failure(
                            error ?: Throwable(UNKNOWN_ERROR)
                        )
                    )
                }
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun getCurrentUser() = auth.currentUser.toAccount()

    override suspend fun deleteUserInAuth(idToken: String) = flow {
        try {
            emit(Response.Loading)
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.currentUser?.reauthenticate(credential)?.await().also {
                auth.currentUser?.delete()!!.await().also {
                    emit(Response.Success(true))
                }
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override suspend fun deleteUsersPhotoInDb(uid: String) = flow {
        try {
            emit(Response.Loading)
            if (uid.isNotBlank()) {
                // формируем ссылку в storage по которой находится аватарка
                val ref = storage.reference.child("${PATH_USERS}/${uid}")
                // удаляем файл в storage по сформированной ссылке
                ref.delete().await().also {
                    emit(Response.Success(true))
                }
            } else {
                emit(Response.Failure(Throwable("Require auth")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
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
            emit(Response.Failure(e))
        }
    }

    override suspend fun changeUserPhoto(localUri: String) = flow {
        try {
            emit(Response.Loading)
            val user = auth.currentUser
            if (user != null) {
                // формируем ссылку в storage по которой будет находиться аватарка
                val ref = storage.reference.child("$PATH_USERS/${user.uid}")
                // загружаем локальный файл в storage по сформированной ссылке
                ref.putFile(localUri.toUri()).await().also { taskSnapshot ->
                    // получаем внешнюю ссылку на загруженный файл
                    taskSnapshot.metadata?.reference?.downloadUrl?.await().also { uri ->
                        if (uri != null) {
                            // если ссылка не null меняем аватарку в Firebase Auth
                            changeUserPhotoInAuth(uri).collect { emit(it) }
                        }
                    }

                }
            } else {
                emit(Response.Failure(Throwable("Require auth")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    private suspend fun changeUserPhotoInAuth(uri: Uri) = flow {
        try {
            emit(Response.Loading)
            val profileUpdates = userProfileChangeRequest { photoUri = uri }
            auth.currentUser?.updateProfile(profileUpdates)?.await().also {
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    private fun FirebaseUser?.toAccount(): AccountDataEntity? {
        return if (this == null) null
        else {
            AccountDataEntity(
                uid = this.uid,
                email = this.email,
                displayName = this.displayName,
                photoUrl = this.photoUrl.toString(),
                this.isAnonymous
            )
        }
    }

}