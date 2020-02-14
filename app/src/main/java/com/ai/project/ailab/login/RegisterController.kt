package com.ai.project.ailab.login

import android.widget.Toast
import com.ai.project.libui.AiActivity

class RegisterController(var activity: AiActivity) : IRegister {

    override fun onRegisterButtonClicked() {
        Toast.makeText(activity, "Hello Friend", Toast.LENGTH_LONG).show()
    }
}

