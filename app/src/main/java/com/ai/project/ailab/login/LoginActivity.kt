package com.ai.project.ailab.login

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.ai.project.ailab.HomeActivity
import com.ai.project.ailab.R
import com.ai.project.libui.AiActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AiActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    companion object {
        private val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            initObject()
            initComponentClick()
            checkIfAlreadyLogin()
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                showAlertToast(e.message)
            }
        }
    }

    override fun onBackPressed() { }

    private fun initObject() {
        auth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun initComponentClick() {
        btn_login.setOnClickListener {
            auth.signInWithEmailAndPassword(et_email.text.toString().trim(), et_password.text.toString().trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user  = task.result?.user
                        gotoHomeActivity(user)
                        showAlertToast("Login Success")
                    } else {
                        showAlertToast(task.exception?.message)
                    }
                }
        }

        btn_login_by_google.setOnClickListener {
            signIn()
        }

        tv_register.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterUserActivity::class.java))
        }
    }

    private fun gotoHomeActivity(user: FirebaseUser?) {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("firebaseUser", user as Parcelable)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun checkIfAlreadyLogin() {
        val user = auth.currentUser
        if (user != null) {
            gotoHomeActivity(user)
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    gotoHomeActivity(user)
                } else {
                    showAlertToast(task.exception?.message)
                }
            }
    }
}
