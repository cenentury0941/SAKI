package com.example.saki.ui.components

import android.service.controls.Control
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saki.domain.models.ControlSignalDataModel
import com.example.saki.ui.theme.LogoBlue

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TextControlSignalDefinition(
    index: Int = 0,
    removeItem: (Int) -> Unit = { },
    controlSignalDataModel: ControlSignalDataModel = ControlSignalDataModel()
){
    var controlSignalName by remember {
        mutableStateOf("")
    }

    var controlSignalDefault by remember {
        mutableStateOf("")
    }

    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp),
        elevation =CardDefaults.cardElevation(5.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp, 20.dp) ) {
            Row( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Text Control Signal", color = Color.Gray)
                IconButton(onClick = { removeItem(index) }, modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)) {
                    Icon( Icons.Filled.Close , contentDescription = "remove", tint = LogoBlue)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = controlSignalName, onValueChange = {controlSignalName = it
                                                                         controlSignalDataModel.name = it},
                modifier = Modifier.fillMaxWidth(1f),
                placeholder = { Text(text = "Control Signal Name")},
                colors = TextFieldDefaults.outlinedTextFieldColors( textColor = LogoBlue , focusedBorderColor = LogoBlue , unfocusedBorderColor = LogoBlue, placeholderColor = Color.Gray )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = controlSignalDefault, onValueChange = {controlSignalDefault = it
                                                                            controlSignalDataModel.defaultText = it},
                modifier = Modifier.fillMaxWidth(1f),
                placeholder = { Text(text = "Default Value")},
                colors = TextFieldDefaults.outlinedTextFieldColors( textColor = LogoBlue , focusedBorderColor = LogoBlue , unfocusedBorderColor = LogoBlue, placeholderColor = Color.Gray )
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NumberControlSignalDefinition(
    index: Int = 0,
    removeItem: (Int) -> Unit = { },
    controlSignalDataModel: ControlSignalDataModel = ControlSignalDataModel()
){
    var controlSignalName by remember {
        mutableStateOf("")
    }

    var controlSignalDefault by remember {
        mutableStateOf(0)
    }

    var controlSignalMin by remember {
        mutableStateOf(-128)
    }

    var controlSignalMax by remember {
        mutableStateOf(128)
    }

    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp),
        elevation =CardDefaults.cardElevation(5.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp, 20.dp) ) {

            Row( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Number Control Signal", color = Color.Gray)
                IconButton(onClick = { removeItem(index) }, modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)) {
                    Icon( Icons.Filled.Close , contentDescription = "remove", tint = LogoBlue)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = controlSignalName, onValueChange = {controlSignalName = it
                                                                         controlSignalDataModel.name = it},
                modifier = Modifier.fillMaxWidth(1f),
                placeholder = { Text(text = "Control Signal Name")},
                colors = TextFieldDefaults.outlinedTextFieldColors( textColor = LogoBlue , focusedBorderColor = LogoBlue , unfocusedBorderColor = LogoBlue, placeholderColor = Color.Gray )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Range", fontSize = 18.sp, modifier = Modifier.padding(10.dp,0.dp), color = Color.Black)
            RangeSlider(value = controlSignalMin.toFloat()..controlSignalMax.toFloat(), onValueChange = {
                controlSignalMin = it.start.toInt()
                controlSignalMax = it.endInclusive.toInt()
                controlSignalDefault = (it.start.toInt()+it.endInclusive.toInt())/2

                controlSignalDataModel.minValue = it.start.toInt()
                controlSignalDataModel.maxValue = it.endInclusive.toInt()
                controlSignalDataModel.defaultNumber = (it.start.toInt()+it.endInclusive.toInt())/2
            },
                valueRange = -256f .. 256f)
            Row ( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(30.dp, 0.dp) ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Text(text = controlSignalMin.toString(), fontSize = 18.sp, color = Color.Gray)
                Text(text = controlSignalMax.toString(), fontSize = 18.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row ( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(0.dp, 0.dp) ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Default Value : ", fontSize = 18.sp, modifier = Modifier.padding(10.dp,0.dp), color = Color.Black)
                Text(text = controlSignalDefault.toString(), fontSize = 18.sp, color = Color.Black)
            }
            Slider(value = controlSignalDefault.toFloat(), onValueChange = { controlSignalDefault = it.toInt()
                                                                           controlSignalDataModel.defaultNumber = it.toInt()},
                valueRange = controlSignalMin.toFloat() .. controlSignalMax.toFloat())

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ToggleControlSignalDefinition(
    index: Int = 0,
    removeItem: (Int) -> Unit = { },
    controlSignalDataModel: ControlSignalDataModel = ControlSignalDataModel()
){
    var controlSignalName by remember {
        mutableStateOf("")
    }

    var controlSignalDefault by remember {
        mutableStateOf(false)
    }

    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp),
        elevation =CardDefaults.cardElevation(5.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp, 20.dp) ) {
            Row( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Toggle Control Signal", color = Color.Gray)
                IconButton(onClick = { removeItem(index) }, modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)) {
                    Icon( Icons.Filled.Close , contentDescription = "remove", tint = LogoBlue)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = controlSignalName, onValueChange = {controlSignalName = it
                                                                         controlSignalDataModel.name = it},
                modifier = Modifier.fillMaxWidth(1f),
                placeholder = { Text(text = "Control Signal Name")},
                colors = TextFieldDefaults.outlinedTextFieldColors( textColor = LogoBlue , focusedBorderColor = LogoBlue , unfocusedBorderColor = LogoBlue, placeholderColor = Color.Gray )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row ( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Text(text = "Default Value", fontSize = 18.sp, color = Color.Black)
                Switch(checked = controlSignalDefault, onCheckedChange = {controlSignalDefault = it
                                                                         controlSignalDataModel.defaultNumber = if (it) 1 else 0}, colors = SwitchDefaults.colors( uncheckedTrackColor = Color.White ))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun StateControlSignalDefinition(controlSignalDataModel: ControlSignalDataModel= ControlSignalDataModel()) {
    var controlSignalName by remember {
        mutableStateOf("")
    }

    var controlSignalDefault by remember {
        mutableStateOf(false)
    }

    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp),
        elevation =CardDefaults.cardElevation(5.dp), colors = CardDefaults.cardColors(containerColor = Color.White) ) {
        Column( modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp, 20.dp) ) {
            Text(text = "State Control Signal", color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = controlSignalName, onValueChange = {
                controlSignalName = it
                controlSignalDataModel.name = it
                Log.d( "ABC" , "NAME : " + controlSignalDataModel.name)
                                                                         },
                modifier = Modifier.fillMaxWidth(1f),
                placeholder = { Text(text = "State Control Name")},
                colors = TextFieldDefaults.outlinedTextFieldColors( textColor = LogoBlue , focusedBorderColor = LogoBlue , unfocusedBorderColor = LogoBlue, placeholderColor = Color.Gray )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row ( modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Default Value", fontSize = 18.sp, color = Color.Black)
                Switch(checked = controlSignalDefault, onCheckedChange = {controlSignalDefault = it
                    controlSignalDataModel.defaultNumber = if (it) 1 else 0
                    Log.d( "ABC" , "TOGGLE  : " + controlSignalDataModel.defaultNumber)
                }, colors = SwitchDefaults.colors( uncheckedTrackColor = Color.White ))
            }
        }
    }
}