package com.example.saki.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.saki.domain.models.GoogleUser
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.MutableStateFlow

class GoogleUserViewModel: ViewModel() {
    private val userData = MutableStateFlow(GoogleUser())

    fun updateUserData(credentials:SignInCredential)
    {
        userData.value = GoogleUser(credentials.displayName!!, credentials.id, credentials.profilePictureUri.toString())
    }

    fun getDisplayName() : String
    {
        return userData.value.username
    }

    fun getIconUrl() : String
    {
        return userData.value.iconURL
    }

}