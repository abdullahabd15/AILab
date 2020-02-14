package com.ai.project.ailab.activity_lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.ai.project.ailab.R
import com.ai.project.libui.AiActivity

class FirstActivity : AiActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_first)
            initActionBar("First Activity")
            showAlertToast("onCreate")
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    override fun onStart() {
        super.onStart()
        showAlertToast("onStart")
    }

    override fun onResume() {
        super.onResume()
        showAlertToast("onResume")
    }

    override fun onPause() {
        showAlertToast("onPause")
        super.onPause()
    }

    override fun onStop() {
        showAlertToast("onStop")
        super.onStop()
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        showAlertToast("Activity Reenter")
    }

    override fun onRestart() {
        showAlertToast("onRestart")
        super.onRestart()
    }

    override fun onDestroy() {
        showAlertToast("onDestroy")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        showAlertToast("onSaveInstanceState")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        showAlertToast("onNewIntent")
    }
}
