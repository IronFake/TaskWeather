package com.ironfake.taskweather.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.TabHost
import androidx.core.content.contentValuesOf
import com.ironfake.taskweather.model.Town
import com.ironfake.taskweather.utils.*

class DatabaseHandler(context: Context, db_name: String, db_version: Int)
            : SQLiteOpenHelper(context, db_name, null, db_version) {

    companion object{

        private var instance: DatabaseHandler? = null

        fun getInstance(context: Context): DatabaseHandler {
            if(instance == null) {
                instance = DatabaseHandler(context, DATABASE_NAME, DATABASE_VERSION)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TOWNS_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_NAME TEXT," +
                "$KEY_TYPE_OF_TOWN TEXT," +
                "$KEY_TEMP_UNIT INTEGER," +
                "$KEY_JANUARY_TMP INTEGER," +
                "$KEY_FEBRUARY_TMP INTEGER," +
                "$KEY_MARCH_TMP INTEGER," +
                "$KEY_APRIL_TMP INTEGER," +
                "$KEY_MAY_TMP INTEGER," +
                "$KEY_JUNE_TMP INTEGER," +
                "$KEY_JULY_TMP INTEGER," +
                "$KEY_AUGUST_TMP INTEGER," +
                "$KEY_SEPTEMBER_TMP INTEGER," +
                "$KEY_OCTOBER_TMP INTEGER," +
                "$KEY_NOVEMBER_TMP INTEGER," +
                "$KEY_DECEMBER_TMP INTEGER )"
        db?.execSQL(CREATE_TOWNS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //CRUD
    fun addTown(town: Town){
        val db = this.writableDatabase
        Log.e(TAG, town.toString())
        val contentValues = inflateContentValues(town)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getAllTown(typeOfTown: String?): List<Town> {
        val db = this.readableDatabase
        val townList = ArrayList<Town>()
        val SELECT_ALL_TOWNS: String
        val cursor: Cursor
        if (typeOfTown == null ){
            SELECT_ALL_TOWNS = "SELECT * FROM $TABLE_NAME"
            cursor = db.rawQuery(SELECT_ALL_TOWNS, null)
        }else {
            SELECT_ALL_TOWNS = "SELECT * FROM $TABLE_NAME WHERE $KEY_TYPE_OF_TOWN =?"
            cursor = db.rawQuery(SELECT_ALL_TOWNS, arrayOf(typeOfTown))
        }


        if (cursor.moveToFirst()){
            do {
                val temp = ArrayList<Int>()
                for (i in 4 until cursor.columnCount){
                    temp.add(cursor.getInt(i))
                }
                val town = Town.create(cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), temp)
                townList.add(town)
            }while (cursor.moveToNext())
        }
        db.close()
        return townList
    }

    fun deleteTown(town: Town){
        val db = this.writableDatabase
        val contentValues = inflateContentValues(town)
        Log.e(com.ironfake.taskweather.utils.TAG, town.toString())
        db.delete(TABLE_NAME,"$KEY_NAME =?",
            arrayOf(town.name))
    }

    private fun inflateContentValues(town: Town): ContentValues{
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, town.name)
        contentValues.put(KEY_TYPE_OF_TOWN, town.typeOfTown)
        contentValues.put(KEY_TEMP_UNIT, town.temperatureUnit)
        contentValues.put(KEY_JANUARY_TMP, town.januaryTmp)
        contentValues.put(KEY_FEBRUARY_TMP, town.februaryTmp)
        contentValues.put(KEY_MARCH_TMP, town.marchTmp)
        contentValues.put(KEY_APRIL_TMP, town.aprilTmp)
        contentValues.put(KEY_MAY_TMP, town.mayTmp)
        contentValues.put(KEY_JUNE_TMP, town.juneTmp)
        contentValues.put(KEY_JULY_TMP, town.julyTmp)
        contentValues.put(KEY_AUGUST_TMP, town.augustTmp)
        contentValues.put(KEY_SEPTEMBER_TMP, town.septemberTmp)
        contentValues.put(KEY_OCTOBER_TMP, town.octoberTmp)
        contentValues.put(KEY_NOVEMBER_TMP, town.novemberTmp)
        contentValues.put(KEY_DECEMBER_TMP, town.decemberTmp)
        return contentValues
    }
}