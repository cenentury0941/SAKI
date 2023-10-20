package com.example.saki.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.saki.R
import com.example.saki.ui.theme.APP_NAME
import com.example.saki.ui.theme.LogoBlue
import com.example.saki.ui.viewmodels.GoogleUserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LandingPage(navController: NavHostController, beginSignIn: () -> Unit, googleUserViewModel: GoogleUserViewModel = viewModel()) {

    lateinit var auth: FirebaseAuth
    auth = Firebase.auth
    var currentUser = auth.getCurrentUser()

    if(currentUser!=null)
    {
        navController.navigate("dashboard")
    }

    Column( modifier = Modifier
        .fillMaxSize(1f)
        .padding(20.dp, 50.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(0.dp, 39.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.home), contentDescription = "logo")
            Text(text = APP_NAME,
                fontSize = 64.sp,
                fontWeight = FontWeight(500),
                color = LogoBlue
                )
        }

        Column ( modifier = Modifier.fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(45.dp)
                    .padding(30.dp, 0.dp),
                shape = RectangleShape,
                elevation = ButtonDefaults.buttonElevation(3.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                onClick = { beginSignIn() }) {

                Row(modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                    ) {
                    Image(painter = painterResource(id = R.drawable.google_logo), contentDescription = "")
                    Text(text = "Sign In With Google",
                        modifier = Modifier.padding(15.dp,0.dp))
                }

            }

        }

    }
}
