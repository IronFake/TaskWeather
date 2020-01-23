package com.ironfake.taskweather.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ironfake.taskweather.R
import com.ironfake.taskweather.data.DatabaseHandler
import com.ironfake.taskweather.model.Town
import com.ironfake.taskweather.ui.main.MainActivity
import com.ironfake.taskweather.utils.ExpandableHeightGridView
import com.ironfake.taskweather.utils.MONTHS
import com.ironfake.taskweather.utils.TYPE_OF_TOWN
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var townList: List<Town>
    private val nameList =  ArrayList<String>()
    lateinit var currentTown: Town
    lateinit var tmpList: List<Int>


    lateinit var gridView: ExpandableHeightGridView
    lateinit var adapter: GridViewAdapter
    //Buttons
    lateinit var newButton: Button
    lateinit var upgradeButton: Button
    //Choosing concrete town
    lateinit var upperTypeSpinner: Spinner

    //lateinit var townSpinner: Spinner

    var isUpdate = false


    lateinit var nameEditText: EditText
    lateinit var underTypeSpinner: Spinner
    lateinit var temperatureUnit: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initGridView(null)
        setTypeSpinner()


        underTypeSpinner = findViewById(R.id.typeSpinner)
        underTypeSpinner.adapter = setSpinnerAdapter(TYPE_OF_TOWN, R.layout.spinner_item)

        nameEditText = findViewById(R.id.nameEditText)
        temperatureUnit = findViewById(R.id.selectMeasureTmpRG)


        // init buttons
        newButton = findViewById(R.id.newButton)
        newButton.setOnClickListener(this)
        upgradeButton = findViewById(R.id.updateButton)
        upgradeButton.setOnClickListener(this)
        newButton.callOnClick()
    }

    private fun setTownSpinner(){
        val townSpinner = findViewById<Spinner>(R.id.townSpinner)
        townSpinner.adapter = setSpinnerAdapter(getAllTownNamesFromList(), null)
        townSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                Log.e(com.ironfake.taskweather.utils.TAG, "check")
                currentTown = townList[townSpinner.selectedItemId.toInt()]
                temperatureUnit.check(getSelectedRadioButton(currentTown.temperatureUnit))
                nameEditText.setText(currentTown.name)
                underTypeSpinner.setSelection(setTypeSelection(currentTown.typeOfTown))
                tmpList = Town.getTemperatureList(currentTown)
                initGridView(tmpList)
            }
        }
    }

    private fun setTypeSelection(typeOfTown: String): Int{
        return when (typeOfTown){
            "small" -> 0
            "average" -> 1
            "big" -> 2
            else -> 0
        }
    }

    private fun setTypeSpinner(){
        upperTypeSpinner = findViewById<Spinner>(R.id.typeOfTownSpinner)
        upperTypeSpinner.adapter = setSpinnerAdapter(TYPE_OF_TOWN, null)
        upperTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                townList = DatabaseHandler.getInstance(baseContext).getAllTown(upperTypeSpinner.selectedItem.toString())
                if (townList.isEmpty()) upgradeButton.isEnabled = false
                setTownSpinner()
            }
        }
    }

    private fun getAllTownNamesFromList(): List<String>{
        nameList.clear()
        for (town in townList){
            nameList.add(town.name)
        }
        return nameList
    }

    private fun initGridView(tmpList: List<Int>?){
        gridView = findViewById(R.id.gridView)
        adapter = GridViewAdapter(baseContext, MONTHS, tmpList)
        gridView.adapter = adapter
        gridView.setExpanded(true)
    }

    private fun setSpinnerAdapter(list: List<String>, layout: Int?): ArrayAdapter<String>{
        return ArrayAdapter(this, layout ?: android.R.layout.simple_spinner_item, list)
            .also<ArrayAdapter<String>> { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save -> {
                val name = nameEditText.text.toString().trim()
                if (name != ""){
                    val db = DatabaseHandler.getInstance(baseContext)

                    val typeOfTown = underTypeSpinner.selectedItem.toString()
                    val town = Town.create(name, typeOfTown,
                        getSelectedRadioButton(temperatureUnit.checkedRadioButtonId),adapter.getValueFromEdiText())
                    if (isUpdate) {
                        db.deleteTown(currentTown)
                        db.addTown(town)
                    } else {
                        db.addTown(town)
                    }
                    startActivity(Intent(this, MainActivity:: class.java))
                } else Toast.makeText(baseContext, "Enter town name, please", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }

    private fun getSelectedRadioButton(id: Int): Int{
        when(id){
            R.id.celsiusRadioButton -> return 1
            R.id.kelvinRadioButton -> return 2
            R.id.fRadioButton -> return 3
            1 -> return R.id.celsiusRadioButton
            2 -> return R.id.kelvinRadioButton
            3 -> return R.id.fRadioButton
        }
        return 0
    }

    override fun onClick(v: View?) {
        val myLayout = findViewById<LinearLayout>(R.id.choosingConcreteTown)
        when(v?.id){
            R.id.newButton -> {
                newButton.setTextColor(ContextCompat.getColor(baseContext, R.color.colorAccent))
                upgradeButton.setTextColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))
                for (i in 0 until myLayout.childCount) {
                    val view = myLayout.getChildAt(i)
                    view.visibility = View.GONE // Or whatever you want to do with the view.
                }
                nameEditText.setText("")
                initGridView(null)
                isUpdate = false
            }
            R.id.updateButton -> {
                newButton.setTextColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))
                upgradeButton.setTextColor(ContextCompat.getColor(baseContext, R.color.colorAccent))
                for (i in 0 until myLayout.childCount) {
                    val view = myLayout.getChildAt(i)
                    view.visibility = View.VISIBLE // Or whatever you want to do with the view.

                }
                isUpdate = true
            }
        }
    }
}























