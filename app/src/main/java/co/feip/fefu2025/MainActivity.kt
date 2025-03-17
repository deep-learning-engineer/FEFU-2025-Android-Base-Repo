package co.feip.fefu2025

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    val amimeGenres = listOf(
        "Приключения",
        "Романтика",
        "Реалистичный",
        "Экспериментальный",
        "Социальная драма"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addCategory = findViewById<Button>(R.id.addCategory)
        addCategory.setOnClickListener{
            addNewCategory()
        }
    }

    fun addNewCategory() {
        val customFlexBox = findViewById<ViewGroup>(R.id.customFlexBox)
        val newCategory = CategoryView(this)

        val color = android.graphics.Color.argb(
            255,
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        newCategory.setBackGroundColor(color)
        newCategory.setCategoryName(amimeGenres.random())

        customFlexBox.addView(newCategory)
    }
}