package com.example.saki.domain.models

import kotlin.reflect.KProperty

data class DeviceDataModel(
    var id:Int = 0,
    var name:String = "Name",
    var type:String = "Definition",
    var icon:String = "https://firebasestorage.googleapis.com/v0/b/home-automation-jetpack.appspot.com/o/Generics%2Fswitch_open.png?alt=media&token=d17761aa-9643-4b8b-b791-926fa79f4826",
    var icon_on:String = "https://firebasestorage.googleapis.com/v0/b/home-automation-jetpack.appspot.com/o/Generics%2Fswitch_closed.png?alt=media&token=50bc46a5-ab55-457a-9cde-edd754ccab9e"
)