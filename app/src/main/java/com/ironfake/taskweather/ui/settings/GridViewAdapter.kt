package com.ironfake.taskweather.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import com.ironfake.taskweather.R
import com.ironfake.taskweather.utils.TAG

class GridViewAdapter(private val context: Context, private val months: List<String>,
                      temperature: List<Int>?) : BaseAdapter(){

    private lateinit var view : View

    private var currentTmpList: MutableList<Int> = temperature?.toMutableList() ?:
                       MutableList(12) {0}
    private var arrayEditText = ArrayList<EditText>()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        if (convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.gridview_item, null)
            val month: TextView = view.findViewById(R.id.monthTV)
            val value: EditText = view.findViewById(R.id.tmpValue)
            if (arrayEditText.size < currentTmpList.size){
                arrayEditText.add(value)
            }
            month.text = months[position]
            value.append(currentTmpList[position].toString())
        }
        return view
    }

    override fun getItem(position: Int): String {
        return months[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return months.size
    }

    fun getValueFromEdiText(): List<Int>{
        var temperatureList = ArrayList<Int>()
        for (editText in arrayEditText){
            temperatureList.add(Integer.parseInt(editText.text.toString()))
        }
        return temperatureList
    }
}