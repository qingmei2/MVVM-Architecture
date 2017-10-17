package com.qingmei2.library.bind;

import android.annotation.SuppressLint;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.annimon.stream.IntPair;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.qingmei2.library.base.adapter.DataBindingItemViewBinder;

import java.util.Collections;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by QingMei on 2017/10/17.
 * desc:
 */

public class Bindings {

    public static class BindableVariables extends BaseObservable {
        @Bindable
        public Object data;
    }

    @BindingAdapter({"itemLayout", "onBindItem"})
    public static void setAdapter(RecyclerView view, int resId, DataBindingItemViewBinder.OnBindItem onBindItem) {
        final MultiTypeAdapter adapter = getOrCreateAdapter(view);
        //noinspection unchecked
        adapter.register(Object.class, new DataBindingItemViewBinder(resId, onBindItem));
    }

    private static MultiTypeAdapter getOrCreateAdapter(RecyclerView view) {
        if (view.getAdapter() instanceof MultiTypeAdapter) {
            return (MultiTypeAdapter) view.getAdapter();
        } else {
            final MultiTypeAdapter adapter = new MultiTypeAdapter();
            view.setAdapter(adapter);
            return adapter;
        }
    }

    @BindingAdapter({"linkers", "onBindItem"})
    public static void setAdapter(RecyclerView view, List<Linker> linkers, DataBindingItemViewBinder.OnBindItem onBindItem) {
        final MultiTypeAdapter adapter = getOrCreateAdapter(view);
        //noinspection unchecked
        final ItemViewBinder[] binders = Stream.of(linkers)
                .map(Linker::getLayoutId)
                .map(v -> new DataBindingItemViewBinder(v, onBindItem))
                .toArray(ItemViewBinder[]::new);
        //noinspection unchecked
        adapter.register(Object.class)
                .to(binders)
                .withLinker(o -> Stream.of(linkers)
                        .map(Linker::getMatcher)
                        .indexed()
                        .filter(v -> v.getSecond().apply(o))
                        .findFirst()
                        .map(IntPair::getFirst)
                        .orElse(0));
    }

    public static class Linker {
        private final Function<Object, Boolean> matcher;
        private final int layoutId;

        public static Linker of(Function<Object, Boolean> matcher, int layoutId) {
            return new Linker(matcher, layoutId);
        }

        Linker(Function<Object, Boolean> matcher, int layoutId) {
            this.matcher = matcher;
            this.layoutId = layoutId;
        }

        Function<Object, Boolean> getMatcher() {
            return matcher;
        }

        int getLayoutId() {
            return layoutId;
        }
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List items) {
        final MultiTypeAdapter adapter = getOrCreateAdapter(view);
        adapter.setItems(items == null ? Collections.emptyList() : items);
        adapter.notifyDataSetChanged();
    }

    @BindingAdapter("onRefresh")
    public static void setOnRefreshListener(SwipeRefreshLayout view,
                                            SwipeRefreshLayout.OnRefreshListener listener) {
        view.setOnRefreshListener(listener);
    }

    @BindingAdapter("visible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter("html")
    public static void setHtml(WebView view, String html) {
        view.getSettings().setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        view.getSettings().setDefaultFontSize(12);
        view.getSettings().setJavaScriptEnabled(true);

        final String tmp = "" +
                "<html>" +
                "<head>" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                "    <style>" +
                "       img {max-width: 100%%; width:auto; height:auto;}" +
                "    </style>" +
                "</head>" +
                "<body>%s</body>" +
                "</html>";
        html = String.format(tmp, html);
        view.loadData(html, "text/html; charset=utf-8", "utf-8");
    }

    @BindingAdapter("onLongClick")
    public static void setOnLongClick(View view, Runnable callback) {
        view.setOnLongClickListener(__ -> {
            callback.run();
            return true;
        });
    }

}
