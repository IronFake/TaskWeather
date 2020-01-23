package com.ironfake.taskweather.ui.main

interface TemperatureStrategy {
    val temperatureUnit: String
}

class CelsiusTempStrategy(override val temperatureUnit: String = "\u00B0 C") :
    TemperatureStrategy {}
class KelvinTempStrategy(override val temperatureUnit: String = "K") :
    TemperatureStrategy {}
class FahrenheitTempStrategy(override val temperatureUnit: String = "\u00B0 F") :
    TemperatureStrategy {}

class TempUnit(var temperatureStrategy: TemperatureStrategy){
    fun getTempUnit(): String {
        return temperatureStrategy.temperatureUnit
    }
}



