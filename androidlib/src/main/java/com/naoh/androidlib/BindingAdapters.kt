package com.naoh.androidlib

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("app:onFocusChangeListener")
    fun setOnFocusChangeListener(editText: EditText, listener: View.OnFocusChangeListener?) {
        editText.onFocusChangeListener = listener
    }
}