package cn.quickits.halia

import android.app.Dialog
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor
import io.reactivex.schedulers.Schedulers


/**
 * @program: Halia
 * @description:
 * @author: gavinliu
 * @create: 2019-04-22 15:55
 **/
class LoadingDialog<T>(private val observable: Observable<T>) {

    private var disposable: Disposable? = null

    private val processor: FlowableProcessor<T> = BehaviorProcessor.create<T>().toSerialized()

    private var dialog: Dialog? = Halia.createDialog()

    private var isManualClose: Boolean = false

    init {
        dialog?.setOnShowListener {
            disposable = observable
                .doOnError {

                }
                .doOnComplete {

                }
                .doOnDispose {

                }
                .doFinally {

                }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    isManualClose = true
                    processor.onNext(it)
                    processor.onComplete()
                    try {
                        dialog?.dismiss()
                    } catch (e: IllegalArgumentException) {

                    }
                }, {
                    isManualClose = true
                    processor.onError(it)
                    processor.onComplete()
                    try {
                        dialog?.dismiss()
                    } catch (e: IllegalArgumentException) {

                    }
                })
        }

        dialog?.setOnDismissListener {
            disposable?.dispose()

            if (!isManualClose) {
                processor.onError(RuntimeException("主动关闭对话框"))
                processor.onComplete()
            }
        }
    }

    fun show(): FlowableProcessor<T> {
        dialog?.show()
        return processor
    }

    fun dismiss() {
        dialog?.dismiss()
    }

}