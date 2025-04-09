package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup


class CustomFlexBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = 0
        var height = 0
        var lineHeight = 0
        val containerWidth = MeasureSpec.getSize(widthMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (width + childWidth > containerWidth) {
                width = 0
                height += lineHeight
                lineHeight = childHeight
            } else {
                lineHeight = maxOf(lineHeight, childHeight)
            }

            width += childWidth
        }
        
        val totalHeight = height + lineHeight
        setMeasuredDimension(containerWidth, totalHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var x = 0
        var y = 0
        var lineHeight = 0
        val containerWidth = right - left

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (x + childWidth > containerWidth) {
                x = left
                y += lineHeight
                lineHeight = 0
            }
            
            child.layout(x, y, x + childWidth, y + childHeight)
            x += childWidth
            lineHeight = maxOf(lineHeight, childHeight)
        }
    }
}