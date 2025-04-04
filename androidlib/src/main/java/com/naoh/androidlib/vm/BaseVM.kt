package com.naoh.androidlib.vm

import android.view.View
import androidx.databinding.BaseObservable
import com.naoh.androidlib.m.BaseM

open class BaseVM(
  val  m: BaseM? = null,
    val v: View? = null
) : BaseObservable() {


}