package com.qingmei2.library.testframework.test

import com.qingmei2.library.testframework.tools.MockAssest
import io.reactivex.Observable
import org.junit.Test

/**
 * Created by QingMei on 2017/11/7.
 * desc:
 */
class MockAsset_Test {

    @Test
    fun assetTest() {
        val content = MockAssest.readFile(MockAssest.USER_DATA)
        Observable.just(content)
                .test()
                .assertValue("{\n" + "    \"login\": \"qingmei2\",\n" + "    \"name\": \"qingmei\"\n" + "}")
    }

}