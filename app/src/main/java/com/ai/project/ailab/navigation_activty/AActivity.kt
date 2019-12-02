package com.ai.project.ailab.navigation_activty

import android.content.Intent
import android.os.Bundle
import com.ai.project.ailab.R
import com.ai.project.libui.AiActivity
import kotlinx.android.synthetic.main.activity_a.*

class AActivity : AiActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_a)

            btn_a.setOnClickListener {
                val intent = Intent(applicationContext, BActivity::class.java)
                startActivity(intent)
            }
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }
}
