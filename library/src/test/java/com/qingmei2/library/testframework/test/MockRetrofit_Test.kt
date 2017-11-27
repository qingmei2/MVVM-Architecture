package com.qingmei2.library.testframework.test

import com.qingmei2.library.testframework.tools.MockAssest
import com.qingmei2.library.testframework.tools.MockRetrofit
import org.junit.Test

/**
 * Created by QingMei on 2017/11/7.
 * desc:
 */
class MockRetrofit_Test {

    @Test
    fun mockRetrofitTest() {
        val retrofit = MockRetrofit()
        val service = retrofit.create(TestService::class.java)
        retrofit.path = MockAssest.USER_DATA

        service.getUser("test")
                .test()
                .assertValue { it ->
                    it.login.equals("qingmei2")
                    it.name.equals("qingmei")
                }
    }
}