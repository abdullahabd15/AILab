package com.ai.project.ailab.activity_lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ai.project.ailab.R
import com.ai.project.libui.AiActivity

class SecondActivity : AiActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_second)
            initActionBar("Second Activity")
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }
}
