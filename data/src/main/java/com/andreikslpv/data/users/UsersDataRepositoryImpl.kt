package com.andreikslpv.data.users

import com.andreikslpv.common.Response
import com.andreikslpv.data.cards.entities.CardDataModel
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

    private val collection = MutableStateFlow(emptyList<String>())
    private val history = MutableStateFlow(emptyList<CardDataModel>())

    override suspend fun createUserInDb(uid: String) = flow {
        try {
            emit(Response.Loading)
            val user = UserDataModel(uid = uid)
            database.collection(FirestoreConstants.PATH_USERS).document(user.uid).set(user).await()
                .also { emit(Response.Success(true)) }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ApiConstants.ERROR_MESSAGE))
        }
    }

    override fun startObserveUserInDb(uid: String) {
        database.collection(FirestoreConstants.PATH_USERS).document(uid)
            .addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    val user = value.toObject(UserDataModel::class.java) ?: UserDataModel()
                    collection.tryEmit(user.collection)
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

    override fun getCollection(): MutableStateFlow<List<String>> {
        return collection
    }

    override fun addToCollection(uid: String, cardId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_COLLECTION, FieldValue.arrayUnion(cardId))
        }
    }

    override fun removeFromCollection(uid: String, cardId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_COLLECTION, FieldValue.arrayRemove(cardId))
        }
    }

    override fun removeAllFromCollection(uid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_COLLECTION, arrayListOf<String>())
        }
    }

    override fun getHistory(): MutableStateFlow<List<CardDataModel>> {
        return history
    }

    override fun setHistory(uid: String, newHistory: List<CardDataModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_HISTORY, newHistory)
        }
    }

}