package com.qingmei2.library.base;

import android.databinding.ObservableBoolean;

import com.qingmei2.library.http.service.ServiceManager;

import javax.inject.Inject;



/**
 * Created by QingMei on 2017/10/14.
 * desc:
 */

public class BaseViewModel {

    @Inject
    protected ServiceManager serviceManager;

    public final ObservableBoolean dialogShowing = new ObservableBoolean(false);

}
