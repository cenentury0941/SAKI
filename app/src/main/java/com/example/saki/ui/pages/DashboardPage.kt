package com.example.saki.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.saki.ui.theme.LogoBlue
import com.example.saki.ui.viewmodels.GoogleUserViewModel
import coil.compose.AsyncImage
import com.example.saki.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardPage(
    googleUserViewModel: GoogleUserViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerToggle = {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { DrawerContent() }
        },
    ) {
        Scaffold(

        ) { contentPadding ->
            print(contentPadding)
            ScreenContent(googleUserViewModel, drawerToggle)

            // Screen content
        }
    }
}

@Composable
fun ScreenContent(googleUserViewModel: GoogleUserViewModel = viewModel(), drawerToggle: () -> Job)
{
    Surface(
        color = LogoBlue
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp, 60.dp, 10.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedCard( modifier = Modifier
                    .height(96.dp)
                    .width(96.dp)
                    .padding(10.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(20.dp)
                ) {
                    AsyncImage(model = googleUserViewModel.getIconUrl() , contentDescription = "Icon" ,
                        modifier = Modifier.fillMaxSize(1f))
                }
                Column( verticalArrangement = Arrangement.Center) {
                    Text(text = "Welcome", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight(300))
                    Text(text = googleUserViewModel.getDisplayName(), color = Color.White, fontSize = 32.sp, fontWeight = FontWeight(666))
                }
            }

            ElevatedCard( modifier = Modifier
                .fillMaxWidth(1f)
                .height(666.dp)
                .padding(0.dp, 30.dp, 0.dp, 0.dp),
                shape = RoundedCornerShape( 20.dp , 20.dp , 0.dp , 0.dp ),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

            }

        }
    }

    Column( modifier = Modifier
        .fillMaxSize(1f)
        .padding(0.dp, 15.dp, 10.dp, 15.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween) {
        FloatingActionButton(
            onClick = { drawerToggle() },
            shape = CircleShape,
            modifier = Modifier.padding(5.dp),
            containerColor = Color.White,
            contentColor = LogoBlue
        ) {
            Icon(Icons.Filled.Menu, "Floating action button.")
        }

        Column {
            FloatingActionButton(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier.padding(10.dp),
                containerColor = Color.White,
                contentColor = LogoBlue
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
            FloatingActionButton(
                onClick = {  },
                shape = CircleShape,
                modifier = Modifier.padding(10.dp),
                containerColor = Color.White,
                contentColor = LogoBlue
            ) {
                Icon(painterResource(id = R.drawable.mic_foreground), "Floating action button.")
            }
        }
    }
}

@Composable
fun DrawerContent(){
    Column( modifier = Modifier.fillMaxSize(1f).padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Column( modifier = Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.home), contentDescription = "Logo" )
        }
        Column( modifier = Modifier.fillMaxWidth(1f) ) {
            Button(onClick = { Firebase.auth.signOut() },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(3.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)) {
                Text(text = "Sign Out")
            }
        }
    }
}