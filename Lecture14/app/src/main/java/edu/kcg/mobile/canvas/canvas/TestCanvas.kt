package edu.kcg.mobile.canvas.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class TestCanvas(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    data class Point(val x: Float, val y: Float)
    data class Line(val p1: Point, val p2: Point)

    private val lines: MutableList<Line> = mutableListOf()
    private var currentLine: Line? = null

    private val whitePaint: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val bluePaint: Paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }

    private val blackPaint: Paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    private val textPaint: Paint = Paint(Paint.LINEAR_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = pxFromDp(24f)
    }

    private val linePaintGreen = Paint().apply {
        isAntiAlias = true
        color = Color.GREEN
        style = Paint.Style.STROKE
    }

    private val linePaintRed = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        strokeWidth = 3f
        style = Paint.Style.STROKE
    }

    private val arcLeft = pxFromDp(20f)

    private fun pxFromDp(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    private fun addLine(p1: Point, p2: Point) {
        lines.add(Line(p1, p2))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPaint(bluePaint)

        canvas.drawRect(
            left + (right - left) / 3f,
            0f,
            right - (right - left) / 3f,
            top + (bottom - top) / 3f,
            whitePaint
        )

        canvas.drawCircle(width / 2f, height / 2f, arcLeft, blackPaint)

        canvas.drawText("KCGI Mobile", 0f, height.toFloat(), textPaint)

        canvas.drawLine(0f, 0f, 500f, 200f, linePaintGreen)

        lines.forEach {
            canvas.drawLine(it.p1.x, it.p1.y, it.p2.x, it.p2.y, linePaintGreen)
        }
        currentLine?.let {
            canvas.drawLine(it.p1.x, it.p1.y, it.p2.x, it.p2.y, linePaintRed)
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
                    addLine(it.p1, it.p2)
                }
                currentLine = null
                performClick()
            }
        }
        invalidate()
        return true
    }

}