package com.example.saki.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.saki.domain.models.GoogleUserDataModel
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.MutableStateFlow

class GoogleUserViewModel: ViewModel() {
    private val userData = MutableStateFlow(GoogleUserDataModel())
    var creds:SignInCredential? = null

    fun updateUserData(credentials:SignInCredential)
    {
        creds = credentials
        userData.value = GoogleUserDataModel(credentials.displayName!!, credentials.id, credentials.profilePictureUri.toString())
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