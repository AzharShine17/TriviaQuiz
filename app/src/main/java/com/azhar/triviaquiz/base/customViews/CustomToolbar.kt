
package com.azhar.triviaquiz.base.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.appbar.MaterialToolbar


class CustomToolbar : MaterialToolbar {

    constructor(context: Context) : super(context) {
        setCustomAttrs(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setCustomAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setCustomAttrs(context, attrs)
    }

    private fun setCustomAttrs(context: Context, attrs: AttributeSet?) {

    }

    fun getTitleTextView(): TextView? {
        return this.getChildOrNull(1) as? TextView
    }

    fun getSubTitleTextView(): TextView? {
        return this.getChildOrNull(2) as? TextView
    }

    fun getNavImageButton(): ImageButton? {
        return this.getChildOrNull(0) as? ImageButton
    }
}

fun ViewGroup.getChildOrNull(index: Int): View? {
    return if (index < this.childCount) {
        this.getChildAt(index)
    } else {
        null
    }
}