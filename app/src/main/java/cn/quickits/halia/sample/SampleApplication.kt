package cn.quickits.halia.sample

import android.app.Application
import cn.quickits.halia.Halia


/**
 * @program: Halia
 * @description:
 * @author: gavinliu
 * @create: 2019-04-22 15:38
 **/
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Halia.init(this)
    }

}