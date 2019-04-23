package cn.quickits.halia

import io.reactivex.Observable
import io.reactivex.processors.FlowableProcessor

/**
 * @program: HbbApp
 * @description:
 * @author:taicheng
 * @create: 19-4-23
 **/
fun <T> Observable<T>.loading(): FlowableProcessor<T> {
    return LoadingDialog(this).show()
}