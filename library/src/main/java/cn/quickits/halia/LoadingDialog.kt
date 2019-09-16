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

    private var dialog: Dialog? = null

    private var isManualClose: Boolean = false

    fun show(data: Any? = null): FlowableProcessor<T> {
        init(data)
        dialog?.show()
        return processor
    }

    private fun init(data: Any? = null) {
        dialog = Halia.createDialog(data)
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
                .subscribe(
                    { data -> dispatchDataOrError(data = data) },
                    { error -> dispatchDataOrError(error = error) }
                )
        }

        dialog?.setOnDismissListener {
            disposable?.dispose()

            if (!isManualClose) {
                processor.onError(RuntimeException("主动关闭对话框"))
                processor.onComplete()
            }
        }
    }

    private fun dismiss() {
        dialog?.dismiss()
    }

    private fun dispatchDataOrError(data: T? = null, error: Throwable? = null) {
        isManualClose = true

        if (data != null) {
            processor.onNext(data)
        } else if (error != null) {
            processor.onError(error)
        }

        processor.onComplete()

        try {
            dismiss()
        } catch (e: IllegalArgumentException) {

        }
    }

}