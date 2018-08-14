package com.qingmei2.rhine.http.service;


import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

/**
 * Created by Glooory on 17/5/15.
 */
@Singleton
public class ServiceManager {

    @Getter
    private UserInfoService userInfoService;

    @Inject
    public ServiceManager(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

}
