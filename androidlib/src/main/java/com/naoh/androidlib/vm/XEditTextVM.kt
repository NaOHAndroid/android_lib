package com.naoh.androidlib.vm

import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ViewFlipper
import androidx.databinding.Bindable
import com.naoh.androidlib.BR
import com.naoh.androidlib.R
import com.naoh.androidlib.m.BaseM
import com.naoh.androidlib.v.XEditText

class XEditTextVM(
    m: BaseM? = null,
    private val xEditText: XEditText
) : BaseVM(m, xEditText) {
    val focusChangeListener =
        View.OnFocusChangeListener { _, hasFocus -> xEditText.isChecked = hasFocus }


    @get:Bindable
    var icClearVisibility = View.GONE
        set(value) {
            field = value
            notifyPropertyChanged(BR.icClearVisibility)
        }

    @get:Bindable
    var text: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.text)
        }
    @get:Bindable
    var hint: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.hint)
        }
    @get:Bindable
    var iconColor: Int = Color.BLACK
        set(value) {
            field = value
            notifyPropertyChanged(BR.iconColor)
        }

    fun flip(view: View) {
        (view as ViewFlipper).showNext()
        val et = xEditText.findViewById<EditText>(R.id.et)
        val end = et.selectionEnd
        when (view.currentView.id) {
            R.id.ivOff -> et.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            R.id.ivOn -> et.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        et.setSelection(end)
    }

    private var lastState = View.GONE
    fun afterTextChange(s: Editable) {
        lastState = if (s.isNotEmpty()) View.VISIBLE else View.GONE
        if (icClearVisibility != lastState) {
            icClearVisibility = lastState
        }
    }
    fun clear(view: View) {
        view.rootView.findViewById<EditText>(R.id.et).setText("")
    }
}