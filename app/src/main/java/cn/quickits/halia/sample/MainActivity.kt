package cn.quickits.halia.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.quickits.halia.LoadingDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            LoadingDialog(API.loadingByNetwork()).show()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println(it)
                    btn.text = it
                }, {
                    it.printStackTrace()
                })
        }

    }

}
