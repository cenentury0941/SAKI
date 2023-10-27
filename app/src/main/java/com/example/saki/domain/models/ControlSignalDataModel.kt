package com.example.saki.domain.models

data class ControlSignalDataModel(
    var type:String = "UNDEFINED",
    var name:String = "",
    var defaultText:String = "",
    var defaultNumber:Int = 0,
    var maxValue: Int = 256,
    var minValue: Int = -256
)