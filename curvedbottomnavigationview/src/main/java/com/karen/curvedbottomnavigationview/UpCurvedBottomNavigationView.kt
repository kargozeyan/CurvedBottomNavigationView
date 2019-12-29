package com.karen.curvedbottomnavigationview

import android.animation.AnimatorInflater
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton


class UpCurvedBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var listener: OnItemClickedListener? = null
    private var clickedItem: NavifartionItemView? = null
    private var isCenterItemClicked = false

    companion object {
        const val UP_CURVE_HEIGHT = 30
        const val BAR_HEIGHT = 60
        const val FAB_RADIUS = 64
        const val ITEM_MARGIN = 2
    }

    enum class Where {
        LEFT, CENTER, RIGHT
    }

    private var upCurve: UpCurveView =
        UpCurveView(
            context
        ).apply {
            layoutParams = LayoutParams(120.dp, UP_CURVE_HEIGHT.dp).apply {
                addRule(CENTER_HORIZONTAL, TRUE)
                setBackgroundColor(Color.WHITE)
            }
        }

    private var leftPart: LinearLayout = LinearLayout(context).apply {
        layoutParams =
            LayoutParams(3 * resources.displayMetrics.widthPixels / 8, BAR_HEIGHT.dp).apply {
                addRule(ALIGN_PARENT_BOTTOM, TRUE)
                addRule(ALIGN_PARENT_START, TRUE)
                background = Color.WHITE.toDrawable()
            }
        weightSum = 2f
    }
    private var rightPart: LinearLayout = LinearLayout(context).apply {
        layoutParams =
            LayoutParams(3 * resources.displayMetrics.widthPixels / 8, BAR_HEIGHT.dp).apply {
                addRule(ALIGN_PARENT_BOTTOM, TRUE)
                addRule(ALIGN_PARENT_END, TRUE)
                background = Color.WHITE.toDrawable()
            }
        weightSum = 2f
    }
    private var fab = FloatingActionButton(context).apply {
        layoutParams = FrameLayout.LayoutParams(FAB_RADIUS.dp, FAB_RADIUS.dp).apply {
            customSize = FAB_RADIUS.dp
            gravity = Gravity.CENTER
            stateListAnimator =
                AnimatorInflater.loadStateListAnimator(context, R.animator.elevation)
            rippleColor = Color.TRANSPARENT
            isClickable = true
            isFocusable = true
            setMargins(0, 0, 0, 5.dp)
            setOnClickListener {
                if (!isCenterItemClicked) {
                    isCenterItemClicked = true
                    listener?.onCenterItemClicked()

                } else {
                    isCenterItemClicked = false
                    listener?.onCenterItemReClicked()
                }
            }
        }
    }

    private var centerPart = FrameLayout(context).apply {
        layoutParams = LayoutParams(resources.displayMetrics.widthPixels / 4, MATCH_PARENT).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            addRule(CENTER_HORIZONTAL, TRUE)
            addView(View(context).apply {
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, BAR_HEIGHT.dp).apply {
                    gravity = Gravity.BOTTOM
                }
                background = Color.WHITE.toDrawable()
            })

            addView(fab)
        }
    }


    private var items: ArrayList<NavifartionItemView> =
        ArrayList()

    init {
        addView(upCurve)
        addView(leftPart)
        addView(centerPart)
        addView(rightPart)
        setBackgroundColor(Color.TRANSPARENT)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(resources.displayMetrics.widthPixels, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(UP_CURVE_HEIGHT.dp + BAR_HEIGHT.dp, MeasureSpec.EXACTLY)
        )
    }

    fun fill(clear: Boolean, vararg item: com.karen.curvedbottomnavigationview.NavigationItem) {
        if (clear) items.clear()
        item.forEach {
            items.add(
                NavifartionItemView(
                    context,
                    item = it
                )
            )
        }
        upd()
    }

    private fun upd() {
        if (items.size == 2 || items.size == 4) {
            createViews()
        } else {
            Toast.makeText(context, "Items count should be 2 or 4", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun createViews() {
        items.forEachIndexed { i, item ->
            val view = item.apply {
                layoutParams =
                    LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT, 4f / items.size).apply {
                        if (items.size == 4) {
                            if (i == 1) {
                                setMargins(0, 0, ITEM_MARGIN.dp, 0)
                            }
                            if (i == 2) {
                                setMargins(ITEM_MARGIN.dp, 0, 0, 0)
                            }
                        }
                        if (items.size == 2) {
                            setMargins(ITEM_MARGIN.dp, 0, 0, ITEM_MARGIN.dp)
                        }
                    }

                setOnClickListener {
                    isCenterItemClicked = false
                    if (clickedItem == item) listener?.onItemReClicked(item.item)
                    else {
                        listener?.onItemClicked(item = item.item)
                        setActiveItem(i)
                        clickedItem = item
                    }
                }
            }
            val where = if (i + 1 <= items.size / 2) {
                Where.LEFT
            } else {
                Where.RIGHT
            }
            addViewToBar(view, where)
        }
    }

    private fun addViewToBar(view: View, where: Where) {
        when (where) {
            Where.LEFT -> {
                leftPart.addView(view)
            }
            Where.CENTER -> {
                TODO()
            }
            Where.RIGHT -> {
                rightPart.addView(view)
            }

        }
    }

    fun setOnItemClickListener(listener: OnItemClickedListener) {
        this.listener = listener
    }

    fun setCenterItem(icon: Int) {
        fab.setImageResource(icon)
    }

    fun setItemActiveColor(string: String) {
        items.forEach {
            it.activeColor = Color.parseColor(string)
        }
    }

    fun setItemActiveColor(resId: Int) {
        items.forEach {
            it.activeColor = ContextCompat.getColor(context, resId)
        }
    }

    override fun setBackgroundColor(color: Int) {
        leftPart.setBackgroundColor(color)
        upCurve.setBackgroundColor(color)
        rightPart.setBackgroundColor(color)
    }

    private fun setActiveItem(index: Int) {
        items.forEach {
            it.activeMode(false)
        }
        items[index].activeMode(true)
    }
}