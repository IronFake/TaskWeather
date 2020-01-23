package com.ironfake.taskweather.model

import com.ironfake.taskweather.utils.SEASONS

data class Town(
    val name: String,
    val typeOfTown: String,
    val temperatureUnit: Int,
    val januaryTmp: Int,
    val februaryTmp: Int,
    val marchTmp: Int,
    val aprilTmp: Int,
    val mayTmp: Int,
    val juneTmp: Int,
    val julyTmp: Int,
    val augustTmp: Int,
    val septemberTmp: Int,
    val octoberTmp: Int,
    val novemberTmp: Int,
    val decemberTmp: Int) : TownProperties
{
    override fun getAverageTemp(season: String): String {
        var averageTmp = 0f
        when (season){
            SEASONS[0] -> averageTmp = (decemberTmp+januaryTmp+februaryTmp)/3f
            SEASONS[1] -> averageTmp = (marchTmp+aprilTmp+mayTmp)/3f
            SEASONS[2] -> averageTmp = (juneTmp+julyTmp+augustTmp)/3f
            SEASONS[3] -> averageTmp = (septemberTmp+octoberTmp+novemberTmp)/3f
        }
        return averageTmp.toString()
    }

    var id = 0

    companion object {

        private var smallTowns = ArrayList<String>()
        private val averageTowns = ArrayList<String>()
        private val bigTowns = ArrayList<String>()

        fun create(name: String, typeOfTown: String, temperatureUnit: Int, temp: List<Int>): Town{
            when(typeOfTown){
                "small" -> smallTowns.add(name)
                "average" -> averageTowns.add(name)
                "big" -> bigTowns.add(name)
            }
            return Town(name, typeOfTown, temperatureUnit, temp[0], temp[1], temp[2],
                temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9], temp[10], temp[11])
        }

        fun getTypeOfCity(name: String): String{
            return when (name) {
                in smallTowns -> "small"
                in averageTowns -> "average"
                in bigTowns -> "big"
                else -> ""
            }
        }

        fun getTemperatureList(town: Town): List<Int>{
            val tmpList = ArrayList<Int>()
            tmpList.add(town.januaryTmp)
            tmpList.add(town.februaryTmp)
            tmpList.add(town.marchTmp)
            tmpList.add(town.aprilTmp)
            tmpList.add(town.mayTmp)
            tmpList.add(town.juneTmp)
            tmpList.add(town.julyTmp)
            tmpList.add(town.augustTmp)
            tmpList.add(town.septemberTmp)
            tmpList.add(town.octoberTmp)
            tmpList.add(town.novemberTmp)
            tmpList.add(town.decemberTmp)
            return tmpList
        }
    }
}