package cn.quickits.halia

import io.reactivex.Observable
import io.reactivex.processors.FlowableProcessor

/**
 * @author: taicheng
 *
 * Created on 2019-04-23.
 **/
fun <T> Observable<T>.loading(data: Any? = null): FlowableProcessor<T> = LoadingDialog(this).show(data)
