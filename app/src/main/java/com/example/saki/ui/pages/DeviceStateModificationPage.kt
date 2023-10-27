package com.example.saki.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.saki.ui.components.NumberControlSignal
import com.example.saki.ui.components.StateControlSignal
import com.example.saki.ui.components.TextControlSignal
import com.example.saki.ui.components.ToggleControlSignal
import com.example.saki.ui.theme.LogoBlue
import com.example.saki.ui.viewmodels.FirebaseViewModel

@Preview
@Composable
fun DeviceStateModificationPage(
    navHostController: NavHostController? = null,
    firebaseViewModel: FirebaseViewModel = viewModel()
) {

    var selectedDevice = firebaseViewModel.selectedDevice

    var controlSignals = firebaseViewModel.getControlSignals(selectedDevice.type)

    var deviceState = firebaseViewModel.getDeviceState(selectedDevice.name).child("controls")

    Surface(
        color = LogoBlue
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
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
                    AsyncImage(model = firebaseViewModel.selectedDevice.icon , contentDescription = "Icon" ,
                        modifier = Modifier.fillMaxSize(1f))
                }
                Column( verticalArrangement = Arrangement.Center) {
                    Text(text = firebaseViewModel.selectedDevice.name, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight(666))
                    Text(text = firebaseViewModel.selectedDevice.type, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight(300))
                }
            }

            ElevatedCard( modifier = Modifier
                .fillMaxWidth(1f)
                .defaultMinSize(minHeight = 800.dp)
                .padding(10.dp, 30.dp, 10.dp, 0.dp),
                shape = RoundedCornerShape( 20.dp , 20.dp , 0.dp , 0.dp ),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column( modifier = Modifier.fillMaxWidth(1f).padding(10.dp,20.dp) ) {

                    controlSignals.forEachIndexed { index, controlSignalDataModel ->
                        when( controlSignalDataModel.type )
                        {
                            "STATE" -> {
                                StateControlSignal( controlSignalDataModel.name , deviceState.child(""+index).value.toString().toInt()==1 , dbRef = firebaseViewModel.getSelectedDeviceRef().child(""+index))
                            }
                            "TEXT" -> {
                                TextControlSignal( controlSignalDataModel.name , deviceState.child(""+index).value.toString() , dbRef = firebaseViewModel.getSelectedDeviceRef().child(""+index) )
                            }
                            "NUMBER" -> {
                                NumberControlSignal(
                                    controlSignalDataModel.name,
                                    controlSignalDataModel.minValue,
                                    controlSignalDataModel.maxValue,
                                    deviceState.child(""+index).value.toString().toInt() , dbRef = firebaseViewModel.getSelectedDeviceRef().child(""+index)
                                )
                            }
                            "TOGGLE" -> {
                                ToggleControlSignal( controlSignalDataModel.name , deviceState.child(""+index).value.toString().toInt()==1  , dbRef = firebaseViewModel.getSelectedDeviceRef().child(""+index))
                            }
                        }
                    }

                }

            }

        }
    }
}