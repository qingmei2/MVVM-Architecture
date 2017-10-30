package com.qingmei2.library.bind.image;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.qingmei2.library.R;
import com.qingmei2.library.image.GlideApp;

/**
 * Created by QingMei on 2017/10/17.
 * desc:
 */

public class BindingImage {

    @BindingAdapter("circleImage")
    public static void setCircleImage(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .circleCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }
}
