package com.ai.project.ailab.login

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.ai.project.ailab.HomeActivity
import com.ai.project.ailab.R
import com.ai.project.libui.AiActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register_user.*

class RegisterUserActivity : AiActivity() {
    private lateinit var registerController: RegisterController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_register_user)
            initObject()
            initButtonClick()
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    @Throws(Exception::class)
    private fun initObject() {
        registerController = RegisterController(this)
        auth = FirebaseAuth.getInstance()
    }

    private fun initButtonClick() {
        btn_register.setOnClickListener {
            auth.createUserWithEmailAndPassword(et_email.text.toString().trim(), et_password.text.toString().trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user  = task.result?.user
                        gotoHomeActivity(user)
                        Toast.makeText(this, "Sign Up Success!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

    private fun gotoHomeActivity(user: FirebaseUser?) {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("firebaseUser", user as Parcelable)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
