package co.feip.fefu2025

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat


class CategoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {
    private val textName: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.anime_filter_item, this, true)
        textName = findViewById(R.id.filterName)
    }

    fun setCategoryName(name: String) {
        textName.text = name
    }

    fun setBackGroundColor(color: Int) {
        val background = ContextCompat.getDrawable(context, R.drawable.filter_background) as GradientDrawable
        background.setColor(color)
        textName.background = background
    }
}