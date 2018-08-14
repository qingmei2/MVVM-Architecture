package com.qingmei2.rhine.ui.home;

import android.databinding.ObservableField;

import com.qingmei2.rhine.base.BaseViewModel;
import com.qingmei2.rhine.http.entity.UserInfo;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by QingMei on 2017/10/14.
 * desc:
 */

public class HomeViewModel extends BaseViewModel {

    public final ObservableField<UserInfo> userInfo = new ObservableField<>();

    @Inject
    public HomeViewModel() {

    }

    public void getUserInfo() {
        serviceManager.getUserInfoService()
                .getUserInfo("qingmei2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo::set, Throwable::printStackTrace);
    }
}
