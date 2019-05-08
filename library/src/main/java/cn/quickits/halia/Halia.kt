package cn.quickits.halia

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import androidx.collection.ArrayMap
import com.afollestad.materialdialogs.MaterialDialog
import java.lang.ref.WeakReference
import java.util.*


/**
 * @program: Halia
 * @description:
 * @author: gavinliu
 * @create: 2019-04-22 15:25
 **/
object Halia {

    private val activityList = LinkedList<Activity>()

    private lateinit var mDialogCreator: (activity: Activity) -> Dialog

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                setTopActivity(activity)
            }

            override fun onActivityStarted(activity: Activity?) {
                setTopActivity(activity)
            }

            override fun onActivityResumed(activity: Activity?) {
                setTopActivity(activity)
            }

            override fun onActivityDestroyed(activity: Activity?) {
                activityList.remove(activity)
            }

            override fun onActivityPaused(activity: Activity?) {
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
        val current = getTopActivity() ?: return null

        if (::mDialogCreator.isInitialized) {
            return mDialogCreator(current)
        }

        return MaterialDialog(current).title(text = "Loading")
    }

    private fun setTopActivity(activity: Activity?) {
        activity ?: return

        if (activityList.contains(activity)) {
            if (activityList.last != activity) {
                activityList.remove(activity)
                activityList.addLast(activity)
            }
        } else {
            activityList.addLast(activity)
        }
    }

    private fun getTopActivity(): Activity? {
        return if (activityList.isNotEmpty()) {
            activityList.last
        } else {
            null
        }
    }

}