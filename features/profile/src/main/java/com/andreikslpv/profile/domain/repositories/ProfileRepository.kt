package com.andreikslpv.profile.domain.repositories

import android.net.Uri
import com.andreikslpv.common.Response
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import com.andreikslpv.common_impl.entities.CardFeatureModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ProfileRepository {

    fun signOut(): Flow<Response<Void>>

    suspend fun deleteUserInDb(uid: String): Flow<Response<Boolean>>

    fun removeAllFromCollection(uid: String): Flow<Response<Boolean>>

    suspend fun deleteUsersPhotoInDb(uid: String): Flow<Response<Boolean>>

    suspend fun deleteUserInAuth(idToken: String): Flow<Response<Boolean>>

    suspend fun linkAnonymousWithCredential(idToken: String): Flow<Response<AccountFeatureEntity?>>

    fun getCurrentUser(): AccountFeatureEntity?

    fun getCollection(): MutableStateFlow<List<String>>

    fun getHistory(): MutableStateFlow<List<CardFeatureModel>>

    fun addToUsersCollection(uid: String, cardId: String)

    fun removeFromUsersCollection(uid: String, cardId: String)

    fun addToCardsCollection(uid: String, card: CardFeatureModel)

    fun removeFromCardsCollection(uid: String, card: CardFeatureModel)

    suspend fun editUserName(newName: String): Flow<Response<Boolean>>

    suspend fun changeUserPhoto(localUri: Uri): Flow<Response<Boolean>>

}