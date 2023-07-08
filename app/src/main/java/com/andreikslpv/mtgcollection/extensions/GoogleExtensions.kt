package com.andreikslpv.mtgcollection.extensions


import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.andreikslpv.common.Constants
import com.andreikslpv.common.LoginCancelledException
import com.andreikslpv.common.LoginFailedException
import com.andreikslpv.common.Response
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status

class GoogleSignInContract(
    private val googleSignInClient: GoogleSignInClient
) : ActivityResultContract<Unit, Response<String>>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        val client = googleSignInClient
        return client.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Response<String> {
        try {
            val googleSignInAccount = GoogleSignIn
                .getSignedInAccountFromIntent(intent)
                .getResult(ApiException::class.java)
            googleSignInAccount?.apply {
                idToken?.let { idToken ->
                    return Response.Success(idToken)
                }
            }
        } catch (e: ApiException) {
            return if (e.statusCode == 12501 /* oops, a bit of non-documented magic :) */
                || e.status == Status.RESULT_CANCELED
            ) {
                Response.Failure(
                    LoginCancelledException(e).localizedMessage ?: Constants.ERROR_AUTH
                )
            } else {
                val message = e.message
                Response.Failure(
                    LoginFailedException(
                        if (message.isNullOrBlank()) "Internal Error"
                        else message,
                        e
                    ).localizedMessage ?: Constants.ERROR_AUTH
                )
            }
        }
        return Response.Failure(Constants.ERROR_AUTH)
    }
}