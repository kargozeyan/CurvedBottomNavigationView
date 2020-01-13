package com.karen.curvedbottomnavigationview

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurvedBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var curvedType: CurvedType = CurvedType.UP
) : RelativeLayout(context, attrs, defStyleAttr) {
    /** OnClick and ReClick listener*/
    private var listener: OnItemClickedListener? = null
    /** Saving Clicked Item*/
    private var clickedItem: NavigationItemView? = null
    /** Saving Center Item Clicked State*/
    private var isCenterItemClicked = false

    /** Constant values*/
    companion object {
        const val CRADLE = 5
        const val FAB_RADIUS = 64
        const val CURVE_WEIGHT = 7 * FAB_RADIUS / 4
        const val CURVE_HEIGHT = 24
        const val BAR_HEIGHT = 56
        const val ITEM_MARGIN = 2
    }

    private enum class Where {
        LEFT, CENTER, RIGHT
    }

    private var upCurve: UpCurveView =
        UpCurveView(
            context
        ).apply {
            layoutParams = LayoutParams(CURVE_WEIGHT.dp, CURVE_HEIGHT.dp).apply {
                addRule(CENTER_HORIZONTAL, TRUE)
                setBackgroundColor(Color.WHITE)
            }
        }
    private var centerSquare = View(context).apply {
        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, BAR_HEIGHT.dp).apply {
            gravity = Gravity.BOTTOM
        }
        background = Color.TRANSPARENT.toDrawable()
    }

    private var fabLp = FrameLayout.LayoutParams(FAB_RADIUS.dp, FAB_RADIUS.dp).apply {
        gravity = Gravity.CENTER
        stateListAnimator =
            AnimatorInflater.loadStateListAnimator(context, R.animator.elevation)

    }

    private var fab = FloatingActionButton(context).apply {
        customSize = FAB_RADIUS.dp
        rippleColor = Color.TRANSPARENT
        isClickable = true
        isFocusable = true
        layoutParams = fabLp
    }

    /** Creating 3 parts of bar*/
    private var leftPart: LinearLayout = LinearLayout(context).apply {
        layoutParams =
            LayoutParams(3 * resources.displayMetrics.widthPixels / 8, BAR_HEIGHT.dp).apply {
                addRule(ALIGN_PARENT_BOTTOM, TRUE)
                addRule(ALIGN_PARENT_START, TRUE)
                background = Color.TRANSPARENT.toDrawable()
            }
        weightSum = 2f
    }
    private var rightPart: LinearLayout = LinearLayout(context).apply {
        layoutParams =
            LayoutParams(3 * resources.displayMetrics.widthPixels / 8, BAR_HEIGHT.dp).apply {
                addRule(ALIGN_PARENT_BOTTOM, TRUE)
                addRule(ALIGN_PARENT_END, TRUE)
                background = Color.TRANSPARENT.toDrawable()
            }
        weightSum = 2f
    }
    private var centerPart = FrameLayout(context).apply {
        layoutParams = LayoutParams(resources.displayMetrics.widthPixels / 4, MATCH_PARENT).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            addRule(CENTER_HORIZONTAL, TRUE)
            background = Color.TRANSPARENT.toDrawable()
            addView(centerSquare)
            addView(fab)

        }
    }


    private var items: ArrayList<NavigationItemView> = ArrayList()
    /** INITIALIZATION OF DRAWING TOOLS*/
    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        strokeWidth = 0f
        color = Color.parseColor("#123456")
        style = Paint.Style.FILL
    }

    var curveW = CURVE_WEIGHT.dp.toFloat()
    var curveH = CURVE_HEIGHT.dp.toFloat()
    var barHeight = BAR_HEIGHT.dp.toFloat()

    init {
        addView(leftPart)
        addView(rightPart)
        addView(centerPart)
        /** Setting background transparent */
        background = Color.TRANSPARENT.toDrawable()


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(resources.displayMetrics.widthPixels, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(CURVE_HEIGHT.dp + BAR_HEIGHT.dp, MeasureSpec.EXACTLY)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        when (curvedType) {
            CurvedType.UP -> {
                drawUpCurve(w.toFloat(), h.toFloat())
            }
            CurvedType.DOWN -> {
                fabLp.gravity = Gravity.TOP
                fabLp.gravity = Gravity.CENTER_HORIZONTAL
                fab.layoutParams = fabLp
                drawDownCurve(w.toFloat(), h.toFloat())
            }
            CurvedType.NONE -> {
                /** Soon */
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }

    private fun drawUpCurve(w: Float, h: Float) {
        val leftDown = PointF(0f, h)
        val leftUp = PointF(0f, curveH)
        val rightUp = PointF(w, curveH)
        val rightDown = PointF(w, h)

        val fStart = PointF(w / 2 - curveW / 2, curveH)
        val centreY = h / 2f
        val fEnd = PointF(w / 2, 0f)
        val fControl1 = PointF((fStart.x + fEnd.x) / 2, fStart.y)
        val fControl2 = PointF(fControl1.x, fEnd.y)

        val sStart = fEnd
        val sEnd = PointF(w / 2 + curveW / 2, curveH)
        val sControl1 = PointF((sStart.x + sEnd.x) / 2, sStart.y)
        val sControl2 = PointF(sControl1.x, sEnd.y)

        path.reset()
        path.moveTo(leftDown.x, leftDown.y)
        path.lineTo(leftUp.x, leftUp.y)
        path.lineTo(fStart.x, fStart.y)
        path.cubicTo(
            fControl1.x, fControl1.y,
            fControl2.x, fControl2.y,
            fEnd.x, fEnd.y
        )
        path.cubicTo(
            sControl1.x, sControl1.y,
            sControl2.x, sControl2.y,
            sEnd.x, sEnd.y
        )
        path.lineTo(rightUp.x, rightUp.y)
        path.lineTo(rightDown.x, rightDown.y)
        path.lineTo(leftDown.x, leftDown.y)
        path.close()
    }

    private fun drawDownCurve(w: Float, h: Float) {
        val leftDown = PointF(0f, h)
        val leftUp = PointF(0f, curveH)
        val rightUp = PointF(w, curveH)
        val rightDown = PointF(w, h)

        val fStart = PointF(w / 2 - FAB_RADIUS.dp * 1.25f, leftUp.y)
        val fEnd = PointF(w / 2, FAB_RADIUS.dp.toFloat() + CRADLE.dp)
        val fControl1 = PointF((fStart.x + fEnd.x) / 2, fStart.y)
        val fControl2 = PointF(fControl1.x, fEnd.y)

        val sStart = fEnd
        val sEnd = PointF(w / 2 + FAB_RADIUS.dp * 1.25f, rightUp.y)
        val sControl1 = PointF((sStart.x + sEnd.x) / 2, sStart.y)
        val sControl2 = PointF(sControl1.x, sEnd.y)

        path.reset()
        path.moveTo(leftDown.x, leftDown.y)
        path.lineTo(leftUp.x, leftUp.y)
        path.lineTo(fStart.x, fStart.y)
        path.cubicTo(
            fControl1.x, fControl1.y,
            fControl2.x, fControl2.y,
            fEnd.x, fEnd.y
        )
        path.cubicTo(
            sControl1.x, sControl1.y,
            sControl2.x, sControl2.y,
            sEnd.x, sEnd.y
        )
        path.lineTo(rightUp.x, rightUp.y)
        path.lineTo(rightDown.x, rightDown.y)
        path.lineTo(leftDown.x, leftDown.y)
        path.close()
    }

    fun fill(clear: Boolean, vararg items: NavigationItem) {
        if (clear) this.items.clear()
        items.forEach {
            this.items.add(
                NavigationItemView(
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
            throw IllegalArgumentException("items count must be 2 or 4")
        }
    }

    /** Adding created items to left and right parts*/
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

    fun setCenterItemIcon(@DrawableRes icon: Int) {
        fab.setImageResource(icon)
    }

    fun setItemActiveColor(color: String) {
        items.forEach {
            it.activeColor = Color.parseColor(color)
        }
    }

    fun setItemActiveColor(@ColorRes resId: Int) {
        items.forEach {
            it.activeColor = ContextCompat.getColor(context, resId)
        }
    }

    fun setItemInactiveColor(color: String) {
        items.forEach {
            it.setInActiveColor(Color.parseColor(color))
        }
    }

    fun setItemInactiveColor(resId: Int) {
        items.forEach {
            it.inactiveColor = ContextCompat.getColor(context, resId)
        }
    }

    fun setBackgroundColor(color: String) {
        setBackgroundColor(Color.parseColor(color))
    }

    override fun setBackgroundColor(color: Int) {
        leftPart.setBackgroundColor(color)
        rightPart.setBackgroundColor(color)
        centerSquare.setBackgroundColor(color)
        upCurve.setBackgroundColor(color)
    }

    private fun setActiveItem(index: Int) {
        items.forEach {
            it.activeMode(false)
        }
        items[index].activeMode(true)
    }

    private class NavigationItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        var item: NavigationItem
    ) : ImageView(context, attrs, defStyleAttr) {

        companion object {
            val defBackgroundColor = Color.WHITE.toDrawable()
            val defScaleType = ScaleType.CENTER_INSIDE
            const val defActiveColor = Color.CYAN
            const val defInactiveColor = Color.RED
        }

        var activeColor: Int =
            defActiveColor
        var inactiveColor: Int = defInactiveColor
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
                val newDr = drawable.constantState?.newDrawable()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    newDr?.mutate()?.colorFilter =
                        BlendModeColorFilter(activeColor, BlendMode.SRC_ATOP)
                } else {
                    @Suppress("DEPRECATION")
                    newDr?.mutate()?.setColorFilter(activeColor, PorterDuff.Mode.SRC_ATOP)
                }
                setImageDrawable(newDr)
            } else {
                setImageDrawable(dr)
            }
        }

        fun setInActiveColor(value: Int) {
            inactiveColor = value
            val newDr = drawable.constantState?.newDrawable()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                newDr?.mutate()?.colorFilter = BlendModeColorFilter(value, BlendMode.SRC_ATOP)
            } else {
                @Suppress("DEPRECATION")
                newDr?.mutate()?.setColorFilter(value, PorterDuff.Mode.SRC_ATOP)
            }
            if (newDr != null) {
                dr = newDr
            }
            setImageDrawable(newDr)
        }
    }
}