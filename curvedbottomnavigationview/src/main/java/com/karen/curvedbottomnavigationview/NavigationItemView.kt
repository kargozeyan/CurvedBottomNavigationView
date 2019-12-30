package com.karen.curvedbottomnavigationview

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable


internal class NavigationItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var item: NavigationItem
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        val defBackgroundColor = Color.WHITE.toDrawable()
        val defScaleType = ScaleType.CENTER_INSIDE
        const val defActiveColor = Color.CYAN
    }

    var activeColor: Int =
        defActiveColor
    private var dr: Drawable

    init {
        scaleType =
            defScaleType
        background =
            defBackgroundColor
        val outValue = TypedValue()
        getContext().theme
            .resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        setBackgroundResource(outValue.resourceId)
        isClickable = true
        isFocusable = true
        setImageResource(item.icon)
        dr = drawable
    }

    fun activeMode(isActive: Boolean) {
        if (isActive) {
            val newDrawable = drawable.constantState?.newDrawable()
            newDrawable?.mutate()?.colorFilter =
                BlendModeColorFilter(activeColor, BlendMode.SRC_ATOP)
            setImageDrawable(newDrawable)
        } else {
            setImageDrawable(dr)
        }
    }

    fun setInactiveColor(color: Int) {
        val newDrawable = drawable.constantState?.newDrawable()
        newDrawable?.mutate()?.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        dr = newDrawable!!
        setImageDrawable(dr)
    }
}