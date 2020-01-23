package com.ironfake.taskweather.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.ironfake.taskweather.R
import com.ironfake.taskweather.data.DatabaseHandler
import com.ironfake.taskweather.model.*
import com.ironfake.taskweather.ui.settings.SettingsActivity
import com.ironfake.taskweather.utils.SEASONS

class MainActivity : AppCompatActivity() {

    lateinit var townList: List<Town>
    private val nameList =  ArrayList<String>()

    var currentSeason = SEASONS[0]
    lateinit var currentTown: Town

    lateinit var typeOfTown: TextView
    lateinit var averageTmpTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabaseHandler.getInstance(baseContext)
        townList = db.getAllTown(null)

        setTownSpinner()
        setSeasonSpinner()

        typeOfTown = findViewById(R.id.typeOfTownTextView)
        averageTmpTextView = findViewById(R.id.averageTmpTextView)
    }

    private fun getAllTownNamesFromList(): List<String>{
        for (town in townList){
            nameList.add(town.name)
        }
        return nameList
    }

    private fun setSpinnerAdapter(list: List<String>, layout: Int?): ArrayAdapter<String> {
        return ArrayAdapter(this, layout ?: android.R.layout.simple_spinner_item, list)
            .also<ArrayAdapter<String>> { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }

    private fun setTownSpinner(){
        val selectTownSpinner = findViewById<Spinner>(R.id.townSpinner)
        selectTownSpinner.adapter = setSpinnerAdapter(getAllTownNamesFromList(), null)
        selectTownSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                currentTown = townList[selectTownSpinner.selectedItemId.toInt()]
                if (townList.isNotEmpty()) updateTextViews(currentTown, currentSeason)
            }
        }
    }

    private fun setSeasonSpinner(){
        val selectSeasonSpinner = findViewById<Spinner>(R.id.seasonSpinner)
        selectSeasonSpinner.adapter = setSpinnerAdapter(SEASONS, null)
        selectSeasonSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                currentSeason = selectSeasonSpinner.selectedItem.toString()
                if (townList.isNotEmpty()) updateTextViews(currentTown, currentSeason)
            }
        }
    }

    private fun updateTextViews(currentTown: Town, currentSeason: String){
        typeOfTown.text = Town.getTypeOfCity(currentTown.name)
        val observableObject = ObservableObject(findViewById(android.R.id.content), ShowSnackBarWithTmp())
        var tempUnit = ""
        when (currentTown.temperatureUnit){
            1 -> tempUnit = TempUnit(
                CelsiusTempStrategy()
            ).getTempUnit()
            2 -> tempUnit = TempUnit(
                KelvinTempStrategy()
            ).getTempUnit()
            3 -> tempUnit = TempUnit(
                FahrenheitTempStrategy()
            ).getTempUnit()
        }
        observableObject.averageTmp = TownPropertiesDecorator(currentTown).getAverageTemp(currentSeason) + tempUnit
        averageTmpTextView.text = observableObject.averageTmp
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings -> startActivity(Intent(this, SettingsActivity:: class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
