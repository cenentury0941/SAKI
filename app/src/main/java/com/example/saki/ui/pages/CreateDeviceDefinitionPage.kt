package com.example.saki.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.saki.domain.models.ControlSignalDataModel
import com.example.saki.ui.components.NumberControlSignalDefinition
import com.example.saki.ui.components.StateControlSignalDefinition
import com.example.saki.ui.components.TextControlSignalDefinition
import com.example.saki.ui.components.ToggleControlSignalDefinition
import com.example.saki.ui.theme.CLOSED_ICON
import com.example.saki.ui.theme.LogoBlue
import com.example.saki.ui.theme.OPEN_ICON
import com.example.saki.ui.viewmodels.FirebaseViewModel

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreateDeviceDefinitionPage(
    navHostController: NavHostController = NavHostController(LocalContext.current),
    firebaseViewModel: FirebaseViewModel = viewModel()
) {

    var deviceType by remember {
        mutableStateOf("")
    }

    val openAlertDialog = remember { mutableStateOf(false) }

    var controlSignals = mutableStateOf(mutableListOf<ControlSignalDataModel>())

    var iter = mutableStateOf(1)


    val removeItem:(index:Int)->Unit = {
        controlSignals.value.removeAt(it)
        iter.value -= 1
    }

    controlSignals.value.add( ControlSignalDataModel( "STATE" ) )

    Surface(
        modifier = Modifier.fillMaxSize(1f),
        color = LogoBlue
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
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
                    Icons.Filled.Create, "Device Def Icon",
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .padding(10.dp),
                    tint = Color.White)
            }
            Column(verticalArrangement = Arrangement.Center) {

                Text(
                    text = "Device Definition",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(666)
                )
                Text(
                    text = "Define a type of device's control signals",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(300)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .defaultMinSize(minHeight = 800.dp)
                    .padding(10.dp, 140.dp, 10.dp, 0.dp),
                shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column( modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(20.dp, 30.dp) ) {
                    Text(text = "Basic Details", fontSize = 30.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(value = deviceType, onValueChange = {deviceType = it},
                        shape = RoundedCornerShape(20),
                        placeholder = { Text(text = "Device Type")},
                        modifier = Modifier.fillMaxWidth(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors( textColor = LogoBlue , focusedBorderColor = LogoBlue , unfocusedBorderColor = LogoBlue, placeholderColor = Color.Gray )
                        )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Device Icons", fontSize = 24.sp, color = Color.Gray )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row( modifier = Modifier.fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly) {

                        Column( horizontalAlignment = Alignment.CenterHorizontally ) {
                            ElevatedCard(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(80.dp)
                                    .padding(3.dp),
                                shape = CircleShape,
                                elevation = CardDefaults.cardElevation(5.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                AsyncImage(model = CLOSED_ICON , contentDescription = "Icon" ,
                                    modifier = Modifier.fillMaxSize(1f))
                            }
                            Text(text = "On", fontSize = 24.sp, color = LogoBlue)
                        }


                        Column( horizontalAlignment = Alignment.CenterHorizontally ) {
                            ElevatedCard(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(80.dp)
                                    .padding(3.dp),
                                shape = CircleShape,
                                elevation = CardDefaults.cardElevation(5.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                AsyncImage(model = OPEN_ICON , contentDescription = "Icon" ,
                                    modifier = Modifier.fillMaxSize(1f))
                            }
                            Text(text = "Off", fontSize = 24.sp, color = LogoBlue)
                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider( modifier = Modifier.padding(30.dp,0.dp) )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Control Signals : " + (iter.value), fontSize = 24.sp, color = Color.Gray )
                    Spacer(modifier = Modifier.height(10.dp))

                    controlSignals.value.forEachIndexed { index, controlSignalDataModel ->
                        when(controlSignalDataModel.type)
                        {
                            "STATE" -> StateControlSignalDefinition(controlSignalDataModel)
                            "TEXT" -> TextControlSignalDefinition(index, removeItem, controlSignalDataModel)
                            "NUMBER" -> NumberControlSignalDefinition(index, removeItem, controlSignalDataModel)
                            "TOGGLE" -> ToggleControlSignalDefinition(index, removeItem, controlSignalDataModel)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(onClick = { openAlertDialog.value = true },
                        modifier = Modifier.fillMaxWidth(1f),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors( contentColor = LogoBlue, containerColor = Color.White ),
                        border = BorderStroke( 1.dp , LogoBlue )
                    ) {
                        Text(text = "+", fontSize = 25.sp, fontWeight = FontWeight(300))
                    }

                    OutlinedButton(onClick = {
                        val success = firebaseViewModel.createDeviceDefinition(deviceType, controlSignals)
                        if(success)
                        {
                            navHostController.popBackStack("dashboard", false)
                        }
                                             },
                        modifier = Modifier.fillMaxWidth(1f),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors( contentColor = Color.White, containerColor = LogoBlue ),
                        border = BorderStroke( 1.dp , LogoBlue )
                    ) {
                        Text(text = "Create Definition", fontSize = 20.sp)
                    }

                }
            }

        }

        when {
            openAlertDialog.value -> {
                ControlSignalTypeDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        openAlertDialog.value = false
                        println("Confirmation registered") // Add logic here to handle confirmation.
                    },
                    dialogTitle = "Control Signal Type",
                    dialogText = "Select the type of control signal to add",
                    icon = Icons.Default.Settings,
                    controlList = controlSignals.value,
                    iter
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlSignalTypeDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    controlList: MutableList<ControlSignalDataModel>,
    iter: MutableState<Int>
) {
    AlertDialog(
        containerColor = LogoBlue,
        textContentColor = Color.White,
        titleContentColor = Color.White,
        iconContentColor = Color.White,
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Column( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                                 controlList.add(ControlSignalDataModel("TEXT"))
                    iter.value += 1
                    onDismissRequest()
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = LogoBlue),
                    modifier = Modifier.fillMaxWidth(1f)) {
                    Text(text = "Text Control")
                }

                Button(onClick = {
                    controlList.add(ControlSignalDataModel("NUMBER"))
                    iter.value += 1
                    onDismissRequest()
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = LogoBlue),
                    modifier = Modifier.fillMaxWidth(1f)) {
                    Text(text = "Number Control")
                }

                Button(onClick = {
                    controlList.add(ControlSignalDataModel("TOGGLE"))
                    iter.value += 1
                    onDismissRequest()
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = LogoBlue),
                    modifier = Modifier.fillMaxWidth(1f)) {
                    Text(text = "Toggle Control")
                }

            }
        }
    )
}

