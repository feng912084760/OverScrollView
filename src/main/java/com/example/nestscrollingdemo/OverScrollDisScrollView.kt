package com.example.nestscrollingdemo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ScrollView
import java.lang.reflect.Field

class OverScrollDisScrollView(cont: Context, attrs: AttributeSet?): ScrollView(cont, attrs) {
    val tag = "OverScrollDisScrollView"
    private val overScrollDistance = 500

    constructor(cont: Context): this(cont, null)

    init {
        val sClass = ScrollView::class.java
        var field: Field? = null
        try {
            field = sClass.getDeclaredField("mOverscrollDistance")
            field.isAccessible = true
            field.set(this, overScrollDistance)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        overScrollMode = OVER_SCROLL_ALWAYS
    }



//    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
//        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
//    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        super.onTouchEvent(ev)
        if (ev != null) {
            when(ev.action) {
                MotionEvent.ACTION_UP -> {
                    val yDown = getYDownScrollRange()
                    if (mScrollY < 0) {
                        scrollTo(0, 0)
//                        onOverScrolled(0, 0, false, false)
                    } else if (mScrollY > yDown) {
                        scrollTo(0, yDown)
//                        onOverScrolled(0, yDown, false, false)
                    }
                }

            }
        }

        return true
    }

    private fun getYDownScrollRange(): Int {
        var scrollRange = 0
        if (childCount > 0) {
            val child = getChildAt(0)
            scrollRange = Math.max(
                0,
                child.height - (height - mPaddingBottom - mPaddingTop)
            )
        }
        return scrollRange
    }


}