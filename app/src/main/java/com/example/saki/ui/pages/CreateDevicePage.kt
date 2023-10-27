package com.example.saki.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.saki.domain.models.DeviceDataModel
import com.example.saki.domain.models.ListItemDataModel
import com.example.saki.ui.components.DeviceThumbnails
import com.example.saki.ui.theme.LogoBlue
import com.example.saki.ui.viewmodels.FirebaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreateDevicePage(
    navHostController: NavHostController = NavHostController(LocalContext.current),
    firebaseViewModel: FirebaseViewModel = viewModel()
) {

    var deviceName by remember {
        mutableStateOf("")
    }

    var devices = firebaseViewModel.getDefinitions()

    var thumbnails = mutableListOf<ListItemDataModel>()
    devices.forEach {
        thumbnails.add( ListItemDataModel( it.id ) )
    }

    Surface(
        modifier = Modifier.fillMaxSize(1f),
        color = LogoBlue
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(10.dp, 25.dp, 10.dp, 10.dp),
            verticalAlignment = Alignment.Top
        ) {
            ElevatedCard(
                modifier = Modifier
                    .height(96.dp)
                    .width(96.dp)
                    .padding(10.dp),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(0.dp),
                colors = CardDefaults.cardColors(containerColor = LogoBlue)
            ) {
                Icon(
                    Icons.Filled.Add, "Device Def Icon",
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .padding(10.dp),
                    tint = Color.White)
            }
            Column(verticalArrangement = Arrangement.Center) {

                Text(
                    text = "Add Device",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(666)
                )
                Text(
                    text = "Add a new device to your current network",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(300)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(10.dp, 140.dp, 10.dp, 10.dp),
                shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column( modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(20.dp, 30.dp) ) {
                    Text(text = "Basic Details", fontSize = 30.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(value = deviceName, onValueChange = {deviceName = it},
                        shape = RoundedCornerShape(20),
                        placeholder = { Text(text = "Device Type") },
                        modifier = Modifier.fillMaxWidth(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors( textColor = LogoBlue , focusedBorderColor = LogoBlue , unfocusedBorderColor = LogoBlue, placeholderColor = Color.Gray )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Device Type", fontSize = 20.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(10.dp))
                    Card( modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(250.dp),
                        colors = CardDefaults.cardColors( containerColor = Color.White ),
                        border = BorderStroke(1.dp, LogoBlue)
                    ) {

                        DeviceGrid(devices = devices, thumbnails =thumbnails)

                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                                     var filtered = thumbnails.filter { it.selected.value }
                        if( filtered.size > 0 )
                        {
                            var success = firebaseViewModel.createDevice(deviceName, devices[filtered[0].id] )
                            if( success )
                            {
                                navHostController.navigate("dashboard")
                            }
                        }
                    },
                    modifier = Modifier
                        .height(54.dp)
                        .fillMaxWidth(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = LogoBlue)
                    ) {
                        Text(text = "Add Device", fontSize = 18.sp)
                    }

                }
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList(){
    var mExpanded by remember { mutableStateOf(false) }

    val mCities = listOf("Delhi", "Mumbai", "Chennai", "Kolkata", "Hyderabad", "Bengaluru", "Pune")
    var mSelectedText by remember { mutableStateOf("") }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(0.dp)) {
        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Label")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            },
            colors = TextFieldDefaults.outlinedTextFieldColors( textColor = LogoBlue , focusedBorderColor = LogoBlue , unfocusedBorderColor = LogoBlue, placeholderColor = Color.Gray )
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            mCities.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) }
                    ,onClick = {
                    mSelectedText = label
                    mExpanded = false
                })
            }
        }
    }
}

@Composable
fun DeviceGrid(devices:MutableList<DeviceDataModel>, thumbnails:MutableList<ListItemDataModel>, navHostController: NavController? = null, navigable:Boolean = false, firebaseViewModel: FirebaseViewModel = viewModel())
{
    LazyVerticalGrid(
        columns = GridCells.FixedSize(150.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(devices.size) { index ->
            DeviceThumbnails(devices[index], thumbnails[index].selected ,thumbnails, navigable ,navHostController = navHostController, firebaseViewModel)
        }
    }
}