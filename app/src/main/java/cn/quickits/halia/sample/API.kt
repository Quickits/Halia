package cn.quickits.halia.sample

import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * @program: Halia
 * @description:
 * @author: gavinliu
 * @create: 2019-04-22 15:52
 **/
object API {

    fun loadingByNetwork(): Observable<String> {
        return Observable.just("Hello, 123").delay(2, TimeUnit.SECONDS)
    }

}