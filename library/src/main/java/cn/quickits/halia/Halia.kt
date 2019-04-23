package cn.quickits.halia

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import androidx.collection.ArrayMap
import com.afollestad.materialdialogs.MaterialDialog
import java.lang.ref.WeakReference


/**
 * @program: Halia
 * @description:
 * @author: gavinliu
 * @create: 2019-04-22 15:25
 **/
object Halia {

    var currentActivity: WeakReference<Activity?>? = null

    val arrayMap: ArrayMap<Int, WeakReference<Activity?>> = ArrayMap()

    private lateinit var mDialogCreator: (activity: Activity) -> Dialog

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity ?: return

                currentActivity?.clear()
                currentActivity = WeakReference(activity)

                arrayMap[activity.hashCode()] = currentActivity

                println(activity)
            }

            override fun onActivityDestroyed(activity: Activity?) {
                currentActivity?.clear()

                activity ?: return

                val wek = arrayMap.remove(activity.hashCode())
                wek?.clear()
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

        })
    }

    fun customDialog(dialogCreator: ((activity: Activity) -> Dialog)) {
        mDialogCreator = dialogCreator
    }

    fun createDialog(): Dialog? {
        val current = currentActivity?.get() ?: return null

        if (::mDialogCreator.isInitialized) {
            return mDialogCreator(current)
        }

        return MaterialDialog(current).title(text = "Loading")
    }

}