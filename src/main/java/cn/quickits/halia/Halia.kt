package cn.quickits.halia

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import java.util.*


/**
 * @author: Gavin Liu
 *
 * Created on 2019-04-22.
 **/
object Halia {

    private val activityList = LinkedList<Activity>()

    private var dialogCreator: (data: Any?, activity: Activity) -> Dialog? = ::defaultDialogCreator

    fun init(application: Application): Halia {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {

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
        return this
    }

    fun customDialog(dialogCreator: (data: Any?, activity: Activity) -> Dialog?): Halia {
        this.dialogCreator = dialogCreator
        return this
    }

    internal fun createDialog(data: Any?): Dialog? {
        val activity = getTopActivity() ?: return null
        return dialogCreator(data, activity)
    }

    private fun defaultDialogCreator(data: Any? = null, activity: Activity): Dialog? =
        MaterialDialog(activity).title(text = "Loading")

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