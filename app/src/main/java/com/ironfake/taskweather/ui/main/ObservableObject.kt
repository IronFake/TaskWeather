package com.ironfake.taskweather.ui.main

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

interface ValueChangeListener{
    fun onValueChanged(view: View, newValue: String)
}

class ShowSnackBarWithTmp : ValueChangeListener{
    override fun onValueChanged(view: View, newValue: String) {
        Snackbar.make(view, newValue, Snackbar.LENGTH_INDEFINITE).show()
    }

}

class ObservableObject(view: View, listener: ValueChangeListener){
    var averageTmp: String by Delegates.observable(
                    initialValue = "",
                    onChange = {
                        property, oldValue, newValue ->
                        listener.onValueChanged(view, newValue)
                    }
    )
}