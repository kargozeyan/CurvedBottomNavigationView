package com.karen.curvedbottomnavigationview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


internal class UpCurveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /** DEFAULT VALUES */
    companion object {
        const val DEF_BACKGROUND = Color.TRANSPARENT
        const val DEF_PAINT_COLOR = Color.WHITE
    }

    /** INITIALIZATION OF DRAWING TOOLS*/
    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        strokeWidth = 0f
        color =
            DEF_PAINT_COLOR
        style = Paint.Style.FILL_AND_STROKE
    }

    init {
        setBackgroundColor(DEF_BACKGROUND)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val centerBottomPoint = PointF(w / 2f, h.toFloat())
        /** CURVE FIRST PART POINTS */
        val fStartPoint = PointF(0f, h.toFloat())
        val fControlPoint1 = PointF(w / 4f, h.toFloat())
        val fControlPoint2 = PointF(w / 4f, 0f)
        val fEndPoint = PointF(w / 2f, 0f)
        /** CURVE SECOND PART POINTS */
        val sStartPoint = fEndPoint
        val sControlPoint1 = PointF(3 * w / 4f, 0f)
        val sControlPoint2 = PointF(3 * w / 4f, h.toFloat())
        val sEndPoint = PointF(w.toFloat(), h.toFloat())

        path.reset()
        /**Drawing FIRST PART*/
        path.moveTo(fStartPoint.x, fStartPoint.y)
        path.cubicTo(
            fControlPoint1.x, fControlPoint1.y,
            fControlPoint2.x, fControlPoint2.y,
            fEndPoint.x, fEndPoint.y
        )
        path.lineTo(centerBottomPoint.x, centerBottomPoint.y)
        path.lineTo(fStartPoint.x, fStartPoint.y)
        /**Drawing SECOND PART*/
        path.moveTo(sStartPoint.x, sStartPoint.y)
        path.cubicTo(
            sControlPoint1.x, sControlPoint1.y,
            sControlPoint2.x, sControlPoint2.y,
            sEndPoint.x, sEndPoint.y
        )
        path.lineTo(centerBottomPoint.x, centerBottomPoint.y)
        path.close()
    }

    /** SET BACKGROUND TO CURVE*/
    override fun setBackgroundColor(color: Int) {
        paint.color = color
        invalidate()
    }
}