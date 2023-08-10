package com.andreikslpv.data.users

import com.andreikslpv.common.Response
import com.andreikslpv.data.constants.ApiConstants
import com.andreikslpv.data.constants.FirestoreConstants
import com.andreikslpv.data.users.entities.UserDataModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersDataRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore,
) : UsersDataRepository {

    private val availableCards = MutableStateFlow(emptyList<String>())
    private val history = MutableStateFlow(emptyList<String>())

    override suspend fun createUserInDb(uid: String) = flow {
        try {
            emit(Response.Loading)
            val user = UserDataModel(uid = uid)
            database.collection(FirestoreConstants.PATH_USERS).document(user.uid).set(user).await()
                .also {
                    emit(Response.Success(true))
                }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ApiConstants.ERROR_MESSAGE))
        }
    }

    override fun startObserveUser(uid: String) {
        database.collection(FirestoreConstants.PATH_USERS).document(uid)
            .addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    val user = value.toObject(UserDataModel::class.java) ?: UserDataModel()
                    availableCards.tryEmit(user.available)
                    history.tryEmit(user.history)
                }
            }
    }

    override suspend fun deleteUserInDb(uid: String) = flow {
        try {
            emit(Response.Loading)
            database.collection(FirestoreConstants.PATH_USERS).document(uid).delete().await()
                .also { emit(Response.Success(true)) }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ApiConstants.ERROR_MESSAGE))
        }
    }

    override fun getAvailable(): MutableStateFlow<List<String>> {
        return availableCards
    }

    override fun addToAvailable(uid: String, cardId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_AVAILABLE, FieldValue.arrayUnion(cardId))
        }
    }

    override fun removeFromAvailable(uid: String, cardId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_AVAILABLE, FieldValue.arrayRemove(cardId))
        }
    }

    override fun removeAllFromAvailable(uid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_AVAILABLE, arrayListOf<String>())
        }
    }

    override fun getHistory(): MutableStateFlow<List<String>> {
        return history
    }

    override fun setHistory(uid: String, newHistory: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_HISTORY, newHistory)
        }
    }

}