package com.example.saki.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.saki.domain.models.ControlSignalDataModel
import com.example.saki.domain.models.DeviceDataModel
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.lang.Exception

class FirebaseViewModel:ViewModel() {

    val database = Firebase.database
    var credentials:SignInCredential? = null
    lateinit var rootRef:DatabaseReference
    lateinit var definitionsSnapshot:DataSnapshot
    lateinit var devicesSnapshot: DataSnapshot
    var loadedDevices by mutableStateOf(false)
    var loadedDefinitions by mutableStateOf(false)
    var selectedDevice by mutableStateOf(DeviceDataModel())

    fun updateRefs()
    {
        if( credentials != null )
        {
            rootRef = database.getReference(credentials!!.id.replace('@','_').replace('.','_'))

            rootRef.child("definitions").addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    definitionsSnapshot = snapshot
                    loadedDefinitions = true
                    Log.d("ABC", "Value is: " + snapshot.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("ABC", "Failed to read value.", error.toException())
                }

            })

            rootRef.child("devices").addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    devicesSnapshot = snapshot
                    loadedDevices = true
                    Log.d("ABC", "Value is: " + snapshot.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("ABC", "Failed to read value.", error.toException())
                }

            })

        }
    }

    fun createDeviceDefinition(
        deviceType: String,
        controlSignals: MutableState<MutableList<ControlSignalDataModel>>
    ): Boolean
    {
        try {
            var definitionsRef = rootRef.child("definitions")
            var deviceRef = definitionsRef.child(deviceType)

            controlSignals.value.forEachIndexed { index, controlSignalDataModel ->
                Log.d("ABC", "createDeviceDefinition: " + controlSignalDataModel.name)
                when(controlSignalDataModel.type)
                {
                    "STATE" -> {
                        deviceRef.child(index.toString()).child("NAME").setValue(controlSignalDataModel.name)
                        deviceRef.child(index.toString()).child("TYPE").setValue("STATE")
                        deviceRef.child(index.toString()).child("DEFAULT_NUMBER").setValue(controlSignalDataModel.defaultNumber)
                    }
                    "TEXT" ->  {
                        deviceRef.child(index.toString()).child("NAME").setValue(controlSignalDataModel.name)
                        deviceRef.child(index.toString()).child("TYPE").setValue("TEXT")
                        deviceRef.child(index.toString()).child("DEFAULT_STRING").setValue(controlSignalDataModel.defaultText)
                    }
                    "NUMBER" -> {
                        deviceRef.child(index.toString()).child("NAME").setValue(controlSignalDataModel.name)
                        deviceRef.child(index.toString()).child("TYPE").setValue("NUMBER")
                        deviceRef.child(index.toString()).child("DEFAULT_NUMBER").setValue(controlSignalDataModel.defaultNumber)
                        deviceRef.child(index.toString()).child("MIN_VALUE").setValue(controlSignalDataModel.minValue)
                        deviceRef.child(index.toString()).child("MAX_VALUE").setValue(controlSignalDataModel.maxValue)
                    }
                    "TOGGLE" -> {
                        deviceRef.child(index.toString()).child("NAME").setValue(controlSignalDataModel.name)
                        deviceRef.child(index.toString()).child("TYPE").setValue("TOGGLE")
                        deviceRef.child(index.toString()).child("DEFAULT_NUMBER").setValue(controlSignalDataModel.defaultNumber)
                    }
                }

            }
            return true
        }
        catch (ex:Exception)
        {
            return false
        }
        return false
    }

    fun createDevice(deviceName: String, deviceDataModel: DeviceDataModel):Boolean
    {
        try{
            var devicesRef = rootRef.child("devices")
            var deviceRef = devicesRef.child(deviceName)
            var definitionRef = definitionsSnapshot.child(deviceDataModel.name)
            deviceRef.child("type").setValue(deviceDataModel.name)
            var deviceControlRef = deviceRef.child("controls")
            definitionRef.children.forEach {
                if( it.child("TYPE").value.toString() == "TEXT" )
                {
                    deviceControlRef.child(it.key!!).setValue( it.child("DEFAULT_TEXT").value )
                }
                else
                {
                    deviceControlRef.child(it.key!!).setValue( it.child("DEFAULT_NUMBER").value )
                }

            }
            return true
        }
        catch (ex:Exception)
        {
            return false
        }
        return false
    }

    fun getDefinitions():MutableList<DeviceDataModel>
    {
        var definitions = mutableListOf<DeviceDataModel>()
        definitionsSnapshot.children.forEachIndexed { index , child ->
            definitions.add( DeviceDataModel( index , child.key.toString() , "type" ) )
        }
        return definitions
    }

    fun getDevices(): MutableList<DeviceDataModel> {
        var devices = mutableListOf<DeviceDataModel>()
        try {
            devicesSnapshot.children.forEachIndexed { index, dataSnapshot ->
                devices.add(DeviceDataModel( index , dataSnapshot.key.toString(), dataSnapshot.child("type").value.toString() ))
            }
        }
        catch (ex:Exception)
        {

        }
        return devices
    }

    fun getRunningDevices(): MutableList<DeviceDataModel> {
        var devices = mutableListOf<DeviceDataModel>()
        try {
            devicesSnapshot.children.filter { it.child("controls").child("0").value != 0 }.forEachIndexed { index, dataSnapshot ->
                devices.add(DeviceDataModel( index , dataSnapshot.key.toString(), dataSnapshot.child("type").value.toString() ))
            }
        }
        catch (ex:Exception)
        {

        }
        return devices
    }

    fun getControlSignals(type:String): MutableList<ControlSignalDataModel> {
        var controlSignals = mutableListOf<ControlSignalDataModel>()
        definitionsSnapshot.child(type).children.forEachIndexed { index, dataSnapshot ->

            when(dataSnapshot.child("TYPE").value.toString())
            {
                "STATE" -> {
                    controlSignals.add(ControlSignalDataModel( dataSnapshot.child("TYPE").value.toString() , dataSnapshot.child("NAME").value.toString(), defaultNumber = dataSnapshot.child("DEFAULT_NUMBER").value.toString().toInt() ))
                }
                "TEXT" -> {
                    controlSignals.add(
                        ControlSignalDataModel(
                        "TEXT",
                            dataSnapshot.child("NAME").value.toString(),
                            defaultText = dataSnapshot.child("DEFAULT_TEXT").value.toString(),
                            )
                    )
                }
                "NUMBER" -> {
                    controlSignals.add(
                        ControlSignalDataModel(
                        "NUMBER",
                            dataSnapshot.child("NAME").value.toString(),
                            defaultNumber = dataSnapshot.child("DEFAULT_NUMBER").value.toString().toInt(),
                            minValue = dataSnapshot.child("MIN_VALUE").value.toString().toInt(),
                            maxValue = dataSnapshot.child("MAX_VALUE").value.toString().toInt()
                            )
                    )
                }
                "TOGGLE" -> {
                    controlSignals.add(
                        ControlSignalDataModel(
                        "TOGGLE",
                            dataSnapshot.child("NAME").value.toString(),
                            defaultNumber = dataSnapshot.child("DEFAULT_NUMBER").value.toString().toInt()
                            )
                    )
                }
            }
        }
        return controlSignals
    }

    fun getDeviceState(device:String): DataSnapshot {
        return devicesSnapshot.child(device)
    }

    fun getSelectedDeviceRef(): DatabaseReference {
        return rootRef.child("devices").child(selectedDevice.name).child("controls")//devicesSnapshot.child(selectedDevice.name)
    }

}