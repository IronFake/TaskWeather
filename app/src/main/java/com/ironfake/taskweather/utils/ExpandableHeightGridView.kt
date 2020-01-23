package com.ironfake.taskweather.utils

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.GridView


class ExpandableHeightGridView(context: Context, attrs: AttributeSet) : GridView(context, attrs) {

    private var expanded = false

    private fun isExpanded(): Boolean {
        return expanded
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // HACK! TAKE THAT ANDROID!
        if (isExpanded()) { // Calculate entire height by providing a very large height hint.
        // View.MEASURED_SIZE_MASK represents the largest height possible.
            val expandSpec = MeasureSpec.makeMeasureSpec(
                MEASURED_SIZE_MASK,
                MeasureSpec.AT_MOST
            )
            super.onMeasure(widthMeasureSpec, expandSpec)
            val params: ViewGroup.LayoutParams = layoutParams
            params.height = measuredHeight
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
    }
}