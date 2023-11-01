package com.andreikslpv.data_users

import com.andreikslpv.common.Response
import com.andreikslpv.data.constants.ApiConstants
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_users.UserModel
import com.andreikslpv.domain_users.UsersRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore,
) : UsersRepository {

    private val collection = MutableStateFlow(emptyList<String>())
    private val history = MutableStateFlow(emptyList<CardModel>())

    override suspend fun createUserInDb(uid: String) = flow {
        try {
            emit(Response.Loading)
            val user = UserModel(uid = uid)
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
                    val user = value.toObject(UserModel::class.java) ?: UserModel()
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

    override fun getHistory(): MutableStateFlow<List<CardModel>> {
        return history
    }

    override fun setHistory(uid: String, newHistory: List<CardModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_HISTORY, newHistory)
        }
    }

}