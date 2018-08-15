package com.qingmei2.rhine.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.qingmei2.rhine.image.GlideApp

object ImageViewBinding {

    @BindingAdapter(value = *arrayOf("android:imageUrl", "android:placeHolder", "android:error", "android:beCircle"), requireAll = false)
    fun loadImage(imageView: ImageView, url: String, placeHolder: Drawable, error: Drawable, beCircle: Boolean) {
        var glideRequest = GlideApp.with(imageView.context)
                .load(url)
                .error(error)
                .placeholder(placeHolder)
        glideRequest = if (beCircle) glideRequest.circleCrop() else glideRequest
        glideRequest.into(imageView)
    }
}
