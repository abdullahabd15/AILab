package com.ai.project.ailab.navigation_activty

import android.content.Intent
import android.os.Bundle
import com.ai.project.ailab.R
import com.ai.project.libui.AiActivity
import kotlinx.android.synthetic.main.activity_b.*

class BActivity : AiActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_b)

            btn_b.setOnClickListener {
                val intent = Intent(applicationContext, CActivity::class.java)
                startActivity(intent)
            }
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }
}
