package com.qingmei2.library.base;

import android.databinding.ObservableField;

import com.qingmei2.library.http.service.ServiceManager;

import javax.inject.Inject;

import static com.qingmei2.library.base.BaseViewModel.State.LOAD_WAIT;


/**
 * Created by QingMei on 2017/10/14.
 * desc:
 */

public class BaseViewModel {

    @Inject
    protected ServiceManager serviceManager;

    public final ObservableField<State> loadingState = new ObservableField<>(LOAD_WAIT);

    public enum State {
        /**
         * the state waiting for fetch data from server.
         */
        LOAD_WAIT,
        /**
         * the state is fetching data from server.
         */
        LOAD_ING,
        /**
         * fetch data successful
         */
        LOAD_SUCCESS,
        /**
         * fetch data faild
         */
        LOAD_FAILED
    }

}
