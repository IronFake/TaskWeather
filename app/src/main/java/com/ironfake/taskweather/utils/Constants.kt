package com.ironfake.taskweather.utils

const val TAG = "TAG"

val MONTHS = listOf("January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December")

val SEASONS = listOf("Winter", "Spring", "Summer", "Autumn")
val TYPE_OF_TOWN = listOf("small", "average", "big")
val TEMPERATURE_UNIT = mapOf(1 to "\u00B0 C", 2 to "K",  3 to "\u00B0 F")

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "townsDB"
const val TABLE_NAME = "towns"

const val KEY_ID = "id"
const val KEY_NAME = "town"
const val KEY_TYPE_OF_TOWN = "typeOfTown"
const val KEY_TEMP_UNIT = "tempUnit"
const val KEY_JANUARY_TMP = "januaryTmp"
const val KEY_FEBRUARY_TMP = "februaryTmp"
const val KEY_MARCH_TMP = "marchTmp"
const val KEY_APRIL_TMP = "aprilTmp"
const val KEY_MAY_TMP = "mayTmp"
const val KEY_JUNE_TMP = "juneTmp"
const val KEY_JULY_TMP = "julyTmp"
const val KEY_AUGUST_TMP = "augustTmp"
const val KEY_SEPTEMBER_TMP = "septemberTmp"
const val KEY_OCTOBER_TMP = "octoberTmp"
const val KEY_NOVEMBER_TMP = "novemberTmp"
const val KEY_DECEMBER_TMP = "decemberTmp"

