package com.andreikslpv.data_users

import com.andreikslpv.common.Response
import com.andreikslpv.data_users.entities.UserModel
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_users.UsersRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore,
) : UsersRepository {

    private val collection = MutableStateFlow(emptyList<String>())
    private val history = MutableStateFlow(emptyList<CardEntity>())
    private lateinit var userListener: ListenerRegistration

    override suspend fun createUserInDb(uid: String) = flow {
        emit(Response.Loading)
        val user = UserModel(uid = uid)
        database.collection(FirestoreConstants.PATH_USERS).document(user.uid).set(user).await()
            .also { emit(Response.Success(true)) }
    }.flowOn(Dispatchers.IO)

    override fun startObserveUserInDb(uid: String) {
        userListener = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            .addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    val user = value.toObject(UserModel::class.java) ?: UserModel()
                    collection.tryEmit(user.collection)
                    history.tryEmit(user.history)
                }
            }
    }

    override fun stopObserveUserInDb() {
        if (::userListener.isInitialized)
            userListener.remove()
    }

    override suspend fun deleteUserInDb(uid: String): Unit =
        withContext(Dispatchers.IO) {
            database.collection(FirestoreConstants.PATH_USERS).document(uid).delete()
        }

    override fun getCollection() = collection

    override suspend fun addToCollection(uid: String, cardId: String): Unit =
        withContext(Dispatchers.IO) {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_COLLECTION, FieldValue.arrayUnion(cardId))
        }

    override suspend fun removeFromCollection(uid: String, cardId: String): Unit =
        withContext(Dispatchers.IO) {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_COLLECTION, FieldValue.arrayRemove(cardId))
        }

    override suspend fun removeAllFromCollection(uid: String): Unit =
        withContext(Dispatchers.IO) {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_COLLECTION, arrayListOf<String>())
        }

    override fun getHistory() = history

    override suspend fun setHistory(uid: String, newHistory: List<CardEntity>): Unit =
        withContext(Dispatchers.IO) {
            val user = database.collection(FirestoreConstants.PATH_USERS).document(uid)
            user.update(FirestoreConstants.FIELD_HISTORY, newHistory)
        }

}