package com.naoh.androidlib.v

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.withStyledAttributes
import androidx.databinding.DataBindingUtil
import com.naoh.androidlib.R
import com.naoh.androidlib.databinding.XEditTextBinding
import com.naoh.androidlib.vm.XEditTextVM

class XEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableLinearLayout(context, attrs, defStyleAttr) {

    private val vm = XEditTextVM(xEditText = this)
    private val binding: XEditTextBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.x_edit_text, this, true)

    init {
        binding.vm = vm

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.XEditText, defStyleAttr, 0) {
                vm.text = (getString(R.styleable.XEditText_android_text) ?: "").also { text ->
                    if (text.isNotEmpty()) {
                      vm.icClearVisibility= VISIBLE
                    }
                }
                vm.hint = getString(R.styleable.XEditText_android_hint) ?: ""
                vm.iconColor= getColor(R.styleable.XEditText_iconColor, Color.BLACK)
                binding.et.inputType =
                    getInt(R.styleable.XEditText_android_inputType, 0).also { inputType ->
                        if (inputType == 129) {
                            binding.flipper.visibility = VISIBLE
                        }
                    }
            }
        }
    }
}