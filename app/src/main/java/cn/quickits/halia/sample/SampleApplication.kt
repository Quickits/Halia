package cn.quickits.halia.sample

import android.app.AlertDialog
import android.app.Application
import cn.quickits.halia.Halia


/**
 * @program: Halia
 * @description:
 * @author: gavinliu
 * @create: 2019-04-22 15:38
 **/
class SampleApplication : Application() {

    private var custom = false

    override fun onCreate() {
        super.onCreate()
        Halia.init(this)
        if (!custom) {
            return
        }
        //if you need custom dialog ,just do it
        Halia.customDialog {
            return@customDialog AlertDialog.Builder(it)
                .setTitle("custom dialog")
                .create()
        }
    }

}