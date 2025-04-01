package com.naoh.androidlib.v

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.LinearLayout

open class CheckableLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), Checkable {

    private var mChecked: Boolean = false
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }

    init {
        isFocusable=true
        isClickable = true
    }


    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }



    override fun toggle() {
        isChecked = !mChecked
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun setChecked(checked: Boolean) {
        if (mChecked != checked) {
            mChecked = checked
            refreshDrawableState()
            setCheckedRecursive(this, checked)
            onCheckedChangeListener?.onCheckedChanged(this, checked)
        }
    }

    private fun setCheckedRecursive(parent: ViewGroup, checked: Boolean) {
        for (i in 0 until parent.childCount) {
            val v = parent.getChildAt(i)
            if (v is Checkable) {
                v.isChecked = checked
            }
            if (v is ViewGroup) {
                setCheckedRecursive(v, checked)
            }
        }
    }


    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()

        val drawable: Drawable? = background
        drawable?.let {
            it.state = drawableState
            invalidate()
        }
    }


    class SavedState : BaseSavedState {
        var checked: Boolean = false

        constructor(superState: Parcelable?) : super(superState)

        private constructor(source: Parcel) : super(source) {
            checked = source.readValue(javaClass.classLoader) as Boolean
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeValue(checked)
        }

        override fun toString(): String {
            return "CheckableLinearLayout.SavedState{" +
                    Integer.toHexString(System.identityHashCode(this)) +
                    " checked=$checked}"
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.checked = isChecked
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            isChecked = state.checked
            requestLayout()
        }
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(who: CheckableLinearLayout, checked: Boolean)
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        this.onCheckedChangeListener = listener
    }
}