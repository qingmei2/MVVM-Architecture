package com.qingmei2.rhine.binding

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.qingmei2.rhine.image.GlideApp

@BindingAdapter("bind_imageView_url")
fun loadImage(imageView: ImageView, url: String?) {
    GlideApp.with(imageView.context)
            .load(url)
            .into(imageView)
}

@BindingAdapter("bind_imageView_url_circle")
fun loadImageCircle(imageView: ImageView, url: String?) {
    GlideApp.with(imageView.context)
            .load(url)
            .apply(RequestOptions().circleCrop())
            .into(imageView)
}