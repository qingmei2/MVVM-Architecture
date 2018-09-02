package com.qingmei2.rhine.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.qingmei2.rhine.image.GlideApp

@BindingAdapter("bind_imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    GlideApp.with(imageView.context)
            .load(url)
            .into(imageView)
}
