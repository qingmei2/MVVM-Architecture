package com.qingmei2.rhine.http.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by QingMei on 2017/8/15.
 * desc:
 */
@Data
public class UserInfo {

    public String name;

    public String login;

    @SerializedName("avatar_url")
    public String avatarUrl;

}
