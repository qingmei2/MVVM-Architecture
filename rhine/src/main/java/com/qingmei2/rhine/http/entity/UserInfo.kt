package com.qingmei2.rhine.http.entity

import com.google.gson.annotations.SerializedName

import lombok.Data

/**
 * Created by QingMei on 2017/8/15.
 * desc:
 */
@Data
class UserInfo {

    var name: String? = null

    var login: String? = null

    @SerializedName("avatar_url")
    var avatarUrl: String? = null

}
