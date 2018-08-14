package com.qingmei2.rhine.bind;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.qingmei2.rhine.image.GlideApp;
import com.qingmei2.rhine.image.GlideRequest;

/**
 * Created by QingMei on 2017/10/17.
 * desc:
 */

public class ImageViewBinding {

    @BindingAdapter(value = {"android:imageUrl", "android:placeHolder", "android:error", "android:beCircle"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable placeHolder, Drawable error, boolean beCircle) {
        GlideRequest<Drawable> glideRequest = GlideApp.with(imageView.getContext())
                .load(url)
                .error(error)
                .placeholder(placeHolder);
        glideRequest = beCircle ? glideRequest.circleCrop() : glideRequest;
        glideRequest.into(imageView);
    }
}
