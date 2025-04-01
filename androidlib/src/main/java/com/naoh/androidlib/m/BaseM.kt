package com.naoh.androidlib.m

import android.view.View
import androidx.databinding.BaseObservable
import com.naoh.androidlib.vm.BaseVM

open class BaseM(
    val v: View?,
    val vm: BaseVM?
) : BaseObservable() {
}