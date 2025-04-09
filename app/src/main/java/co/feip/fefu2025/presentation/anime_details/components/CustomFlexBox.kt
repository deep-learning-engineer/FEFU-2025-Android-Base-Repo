package co.feip.fefu2025.presentation.anime_details.components

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

class CustomFlexBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    var horizontalSpacing: Int = 0
    var verticalSpacing: Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val containerWidth = MeasureSpec.getSize(widthMeasureSpec)
        if (containerWidth == 0) {
            setMeasuredDimension(0, 0)
            return
        }

        var currentLineWidth = 0
        var currentLineHeight = 0
        var totalHeight = 0
        var maxLineWidth = 0
        var childrenOnCurrentLine = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) continue

            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            // Проверяем, помещается ли ребенок на текущую строку
            val requiredWidth = if (childrenOnCurrentLine > 0) {
                currentLineWidth + horizontalSpacing + childWidth
            } else {
                currentLineWidth + childWidth
            }

            if (requiredWidth <= containerWidth) {
                // Помещается: добавляем ширину ребенка и отступ (если не первый)
                currentLineWidth = requiredWidth
                currentLineHeight = max(currentLineHeight, childHeight)
                childrenOnCurrentLine++
            } else {
                // Не помещается: завершаем текущую строку, начинаем новую
                maxLineWidth = max(maxLineWidth, currentLineWidth) // Обновляем макс ширину
                totalHeight += currentLineHeight // Добавляем высоту законченной строки
                if (totalHeight > 0) { // Добавляем вертикальный отступ, если это не первая строка
                    totalHeight += verticalSpacing
                }

                currentLineWidth = childWidth
                currentLineHeight = childHeight
                childrenOnCurrentLine = 1
            }
        }

        totalHeight += currentLineHeight
        maxLineWidth = max(maxLineWidth, currentLineWidth)

        val finalWidth = resolveSize(maxLineWidth, widthMeasureSpec)
        val finalHeight = resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val containerWidth = r - l
        var currentX = paddingLeft
        var currentY = paddingTop
        var currentLineHeight = 0
        var childrenOnCurrentLine = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) continue

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            // Проверяем, помещается ли ребенок
            val requiredX = if (childrenOnCurrentLine > 0) {
                currentX + horizontalSpacing + childWidth
            } else {
                currentX + childWidth
            }

            if (requiredX <= containerWidth - paddingRight) { // Учитываем правый padding
                // Помещается: располагаем ребенка
                if (childrenOnCurrentLine > 0) {
                    currentX += horizontalSpacing // Добавляем горизонтальный отступ перед ребенком
                }
                child.layout(currentX, currentY, currentX + childWidth, currentY + childHeight)
                currentX += childWidth
                currentLineHeight = max(currentLineHeight, childHeight)
                childrenOnCurrentLine++
            } else {
                // Не помещается: переходим на новую строку
                currentX = paddingLeft // Сбрасываем X
                currentY += currentLineHeight + verticalSpacing // Сдвигаем Y на высоту строки + верт. отступ
                currentLineHeight = childHeight // Новая высота строки
                childrenOnCurrentLine = 1

                child.layout(currentX, currentY, currentX + childWidth, currentY + childHeight)
                currentX += childWidth
            }
        }
    }
}