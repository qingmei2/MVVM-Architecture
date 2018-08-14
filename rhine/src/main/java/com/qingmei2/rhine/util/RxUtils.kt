package com.qingmei2.rhine.util

/**
 * Created by Glooory on 17/5/15.
 */

object RxUtils//    public static <T> LifecycleTransformer<T> bindToLifecycle(IView view) {
//        if (view instanceof RxAppCompatActivity) {
//            return ((RxAppCompatActivity) view).bindToLifecycle();
//        } else if (view instanceof RxFragment) {
//            return ((RxFragment) view).bindToLifecycle();
//        } else {
//            throw new IllegalArgumentException("view isn't activity or fragment");
//        }
//    }
//
//    public static <T> MaybeTransformer<T, T> bindViewMaybe(IView view) {
//        return bindToLifecycle(view);
//    }
//
//    public static <T> ObservableTransformer<T, T> bindViewObservable(IView view) {
//        return bindToLifecycle(view);
//    }
//
//    public static <T> SingleTransformer<T, T> bindViewSingle(IView view) {
//        return bindToLifecycle(view);
//    }
//
//    public static <T> FlowableTransformer<T, T> bindViewFlowable(IView view) {
//        return bindToLifecycle(view);
//    }
//    public static <T> MaybeTransformer<T, T> switchThreadMaybe() {
//        return maybe ->
//                maybe
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    public static <T> ObservableTransformer<T, T> switchThreadObservable() {
//        return observable ->
//                observable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    public static <T> SingleTransformer<T, T> switchThreadSingle() {
//        return single ->
//                single
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    public static <T> FlowableTransformer<T, T> switchThreadFlowable() {
//        return flowable ->
//                flowable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//    }
