package com.example.saki.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DatabaseReference

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TextControlSignal(name: String = "Name", state: String = "state", dbRef: DatabaseReference? = null) {
    var controlSignalName by remember {
        mutableStateOf(name)
    }

    var controlSignalValue by remember {
        mutableStateOf(state)
    }

    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp,0.dp),
        elevation =CardDefaults.cardElevation(0.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp, 20.dp) ) {
            Text(text = controlSignalName, fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = controlSignalValue, onValueChange = {controlSignalValue = it
                                                                            dbRef!!.setValue(it)
                                                                          },
                modifier = Modifier.fillMaxWidth(1f),
                placeholder = { Text(text = "Value")})
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NumberControlSignal(
    name: String = "name",
    minValue: Int = 0,
    maxValue: Int = 2,
    current: Int = 1,
    dbRef: DatabaseReference? = null
) {
    var controlSignalName by remember {
        mutableStateOf(name)
    }

    var controlSignalCurrent by remember {
        mutableStateOf(current)
    }

    var controlSignalMin by remember {
        mutableStateOf(minValue)
    }

    var controlSignalMax by remember {
        mutableStateOf(maxValue)
    }

    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp,0.dp),
        elevation =CardDefaults.cardElevation(0.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp, 20.dp) ) {
            Text(text = controlSignalName, fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(20.dp))
            Row ( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(0.dp, 0.dp) ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Current Value : ", fontSize = 18.sp, modifier = Modifier.padding(5.dp,0.dp), color = Color.Black)
                Text(text = controlSignalCurrent.toString(), fontSize = 18.sp, color = Color.Black)
            }
            Slider(value = controlSignalCurrent.toFloat(), onValueChange = { controlSignalCurrent = it.toInt() },
                valueRange = controlSignalMin.toFloat() .. controlSignalMax.toFloat(),
                onValueChangeFinished = {
                    dbRef!!.setValue(controlSignalCurrent)
                })
            Row ( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp, 0.dp) ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = controlSignalMin.toString(), fontSize = 18.sp, color = Color.Gray)
                Text(text = controlSignalMax.toString(), fontSize = 18.sp, color = Color.Gray)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ToggleControlSignal(name: String = "name", state: Boolean = false, dbRef: DatabaseReference? = null) {
    var controlSignalName by remember {
        mutableStateOf(name)
    }

    var controlSignalDefault by remember {
        mutableStateOf(state)
    }

    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp,0.dp),
        elevation =CardDefaults.cardElevation(0.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp, 20.dp) ) {
            Row ( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = controlSignalName, fontSize = 20.sp, color = Color.Black)
                Switch(checked = controlSignalDefault, onCheckedChange = {controlSignalDefault = it
                                                                         dbRef!!.setValue(if (it) 1 else 0)
                                                                         }, colors = SwitchDefaults.colors( uncheckedTrackColor = Color.White ))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun StateControlSignal(name: String = "State Signal", state: Boolean = false, dbRef: DatabaseReference? = null){
    var controlSignalName by remember {
        mutableStateOf(name)
    }

    var controlSignalDefault by remember {
        mutableStateOf(state)
    }

    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp,0.dp),
        elevation =CardDefaults.cardElevation(0.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp, 20.dp) ) {
            Row ( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = controlSignalName, fontSize = 20.sp, color = Color.Black)
                Switch(checked = controlSignalDefault, onCheckedChange = {controlSignalDefault = it
                    dbRef!!.setValue(if (it) 1 else 0)
                }, colors = SwitchDefaults.colors( uncheckedTrackColor = Color.White ))
            }
        }
    }
}