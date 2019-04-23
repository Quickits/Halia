package cn.quickits.halia.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.quickits.halia.Halia
import cn.quickits.halia.loading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            API.loadingByNetwork().loading()
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
