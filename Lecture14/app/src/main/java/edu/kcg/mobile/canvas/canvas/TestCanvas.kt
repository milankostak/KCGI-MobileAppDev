package edu.kcg.mobile.canvas.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TestCanvas(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    data class Point(val x: Float, val y: Float)
    data class Line(val p1: Point, val p2: Point)

    private val lines = mutableListOf<Line>()
    private var currentLine: Line? = null

    private val whiteFillPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val blueFillPaint = Paint().apply {
        color = Color.valueOf(0.3f, 0.3f, 1f).toArgb()
        style = Paint.Style.FILL
    }

    private val cyanFillPaint = Paint().apply {
        color = Color.CYAN // valueOf(0f, 1f, 1f).toArgb()
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.LINEAR_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = pxFromDp(24f)
    }

    private val greenLinePaint = Paint().apply {
        isAntiAlias = true
        color = Color.GREEN
        style = Paint.Style.STROKE
    }

    private val redLinePaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        strokeWidth = 3f
        style = Paint.Style.STROKE
    }

    private val arcLeft = pxFromDp(20f)

    private fun pxFromDp(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPaint(blueFillPaint)

        canvas.drawRect(width / 3f, 0f, width * 0.667f, height / 2f, whiteFillPaint)

        canvas.drawCircle(width / 2f, height / 3f, arcLeft, cyanFillPaint)

        canvas.drawText("KCGI Mobile", 0f, height.toFloat(), textPaint)

        canvas.drawLine(0f, 0f, 500f, 200f, greenLinePaint)

        lines.forEach {
            canvas.drawLine(it.p1.x, it.p1.y, it.p2.x, it.p2.y, greenLinePaint)
        }

        currentLine?.let {
            canvas.drawLine(it.p1.x, it.p1.y, it.p2.x, it.p2.y, redLinePaint)
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val x = e.x
        val y = e.y

        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                currentLine = Line(Point(x, y), Point(x, y))
            }
            MotionEvent.ACTION_MOVE -> {
                currentLine = Line(currentLine?.p1 ?: Point(x, y), Point(x, y))
            }
            MotionEvent.ACTION_UP -> {
                currentLine?.let {
                    lines.add(Line(it.p1, it.p2))
                }
                currentLine = null
                performClick()
            }
        }
        invalidate() // request redraw
        return true
    }

}