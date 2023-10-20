package com.example.saki

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saki.ui.pages.DashboardPage
import com.example.saki.ui.pages.LandingPage
import com.example.saki.ui.theme.SAKITheme
import com.example.saki.ui.viewmodels.GoogleUserViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.auth
import com.google.firebase.auth.internal.IdTokenListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    lateinit var navController: NavController
    lateinit var oneTapClient: SignInClient
    lateinit var signInRequest: BeginSignInRequest
    private val REQ_ONE_TAP = 420
    val googleUserViewModel by viewModels<GoogleUserViewModel>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(
            "ABC",
            "Activity Result : " + requestCode + " | " + resultCode + " | " + data.toString()
        )

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with your backend.
                            Log.d("ABC", "Got ID token.")
                            Log.d("ABC", "Token = " + idToken.toString())
                            Log.d("ABC", "Token Class = " + idToken)
                            googleUserViewModel.updateUserData(credential)
                            navController.navigate("dashboard")
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d("ABC", "No ID token!")
                        }
                    }
                } catch (e: ApiException) {
                    // ...
                }
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idTokenListener: IdTokenListener = IdTokenListener {
            Log.d("ABC", "Token Change : " + it.token)
            if( it.token != null )
            {

            }
            else
            {
                navController.popBackStack(navController.graph.startDestinationId, false)
            }
        }

        Firebase.auth.addIdTokenListener(idTokenListener)

        setContent {
            SAKITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    navController  = rememberNavController()

                    NavHost(navController = navController as NavHostController, startDestination = "landing") {
                        composable("landing") { LandingPage(navController as NavHostController, ::beginSignIn) }
                        composable("dashboard") { DashboardPage(googleUserViewModel) }
                    }

                }
            }
        }
    }






    fun beginSignIn() {

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("547956355528-pdqkftq5mvnd3ndtlv29c4jnacdmu5tl.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .setAutoSelectEnabled(true)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    ActivityCompat.startIntentSenderForResult(
                        findActivity()
                        ,result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("ABC", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d("ABC", "Sign Ip : "+ e.localizedMessage)

                var signUpRequest = BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            // Your server's client ID, not your Android client ID.
                            .setServerClientId("547956355528-pdqkftq5mvnd3ndtlv29c4jnacdmu5tl.apps.googleusercontent.com")
                            // Show all accounts on the device.
                            .setFilterByAuthorizedAccounts(false)
                            .build()
                    )
                    .build()


                oneTapClient.beginSignIn(signUpRequest)
                    .addOnSuccessListener(this) { result ->
                        try {
                            ActivityCompat.startIntentSenderForResult(
                                findActivity(),
                                result.pendingIntent.intentSender, REQ_ONE_TAP,
                                null, 0, 0, 0, null
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e("ABC", "Couldn't start One Tap UI: ${e.localizedMessage}")
                        }
                    }
                    .addOnFailureListener(this) { e ->
                        // No Google Accounts found. Just continue presenting the signed-out UI.
                        Log.d("ABC", "Sign Up : "+ e.localizedMessage)
                    }
            }


    }


    fun Context.findActivity(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("no activity")
    }


}
