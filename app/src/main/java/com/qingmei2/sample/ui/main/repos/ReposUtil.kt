package com.qingmei2.sample.ui.main.repos

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import android.view.View
import com.qingmei2.sample.R

object ReposUtil {

    @JvmStatic
    fun transLanguageColor(view: View, language: String?): Drawable {
        if (language == null) return ColorDrawable(Color.TRANSPARENT)

        val colorProvider: (Int) -> Drawable = { resId ->
            ColorDrawable(ContextCompat.getColor(view.context, resId))
        }

        return colorProvider(
                when (language) {
                    "Kotlin" -> R.color.color_language_kotlin
                    "Java" -> R.color.color_language_java
                    "JavaScript" -> R.color.color_language_js
                    "Python" -> R.color.color_language_python
                    "HTML" -> R.color.color_language_html
                    "CSS" -> R.color.color_language_css
                    "Shell" -> R.color.color_language_shell
                    "C++" -> R.color.color_language_cplus
                    "C" -> R.color.color_language_c
                    "ruby" -> R.color.color_language_ruby
                    else -> R.color.color_language_other
                })
    }

    @JvmStatic
    fun transRepoDesc(desc: String?): String =
            desc ?: "(No description, website, or topics provided.)"
}