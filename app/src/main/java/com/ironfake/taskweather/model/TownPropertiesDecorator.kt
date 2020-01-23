package com.ironfake.taskweather.model

import android.util.Log
import com.ironfake.taskweather.utils.TAG

class TownPropertiesDecorator(var townProperties: TownProperties): TownProperties {

    override fun getAverageTemp(season: String): String {
        Log.e (TAG, "average town decorator")
        return this.townProperties.getAverageTemp(season)
    }
}
