package com.qingmei2.rhine.bind;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by QingMei on 2017/11/6.
 * desc:
 */

public class ViewBinding {

    @BindingAdapter("visible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("onLongClick")
    public static void setOnLongClick(View view, Runnable callback) {
        view.setOnLongClickListener(__ -> {
            callback.run();
            return true;
        });
    }
}
