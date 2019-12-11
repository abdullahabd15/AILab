package com.ai.project.ailab.custom_call_screen

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.session.MediaSessionManager
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.ai.project.ailab.R
import com.ai.project.libui.AiActivity

class CustomCallScreenActivity : AiActivity() {
    private var tvCallerId: TextView? = null
    private var btnPickUp: ImageView? = null
    private var btnReject: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var telephonyManager: TelephonyManager? = null
    private var callerId: String = ""
    private var phoneNumber: String = ""
    private var isIncomingCall: Boolean = false
    private var preferences: SharedPreferences? = null

    companion object {
        const val INCOMING_CALL_CODE = "INCOMING_CALL_CODE"
        const val PHONE_NUMBER_CODE = "PHONE_NUMBER_CODE"
        const val ANSWER_CALL_PERMISSION_CODE = 999
        var activity: AiActivity? = null

        fun startCallActivity(context: Context?, phoneNumber: String?, isIncomingCall: Boolean) {
            val intent = Intent(context, CustomCallScreenActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(INCOMING_CALL_CODE, isIncomingCall)
            intent.putExtra(PHONE_NUMBER_CODE, phoneNumber)
            context?.startActivity(intent)
        }

        fun dismissCallActivity() {
            activity?.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_custom_call_screen)
            initObject()
            initComponent()
            getDataFromIntent()
            getCallerId()
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    override fun onStart() {
        try {
            super.onStart()
            activity = this
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    override fun onBackPressed() { }

    @Throws(Exception::class)
    private fun initObject() {
        preferences = applicationContext.getSharedPreferences("call", Context.MODE_PRIVATE)
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
    }

    private fun initComponent() {
        tvCallerId = findViewById(R.id.tv_caller_id)
        btnPickUp = findViewById(R.id.btn_pick_up)
        btnReject = findViewById(R.id.btn_reject)
        progressBar = findViewById(R.id.progress_bar)

        initComponentListener()
    }

    private fun initComponentListener() {
        btnPickUp?.setOnClickListener {
            try {
                answerIncomingCall()
            } catch (e: Exception) {
                showErrorDialog(e)
            }
        }

        btnReject?.setOnClickListener {
            try {
                telephonyManager?.javaClass?.getMethod("endCall")?.invoke(telephonyManager)
                removeCallerIdFromSharePref()
            } catch (e: Exception) {
                showErrorDialog(e)
            }
        }
    }

    private fun getDataFromIntent() {
        val intent = intent
        if (intent.hasExtra(INCOMING_CALL_CODE)) {
            isIncomingCall = intent.getBooleanExtra(INCOMING_CALL_CODE, false)
        }

        if (intent.hasExtra(PHONE_NUMBER_CODE)) {
            phoneNumber = intent.getStringExtra(PHONE_NUMBER_CODE)
        }
    }

    private fun getCallerId() {
        if (!isIncomingCall) {
            callerId = preferences?.getString("callerId", "")!!
            tvCallerId?.text = callerId
        } else {
            btnPickUp?.visibility = View.VISIBLE
            tvCallerId?.text = phoneNumber
        }
    }

    private fun answerIncomingCall() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_GRANTED) {
                    val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                    telecomManager.acceptRingingCall()
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ANSWER_PHONE_CALLS), ANSWER_CALL_PERMISSION_CODE)
                }
            } else {
                val mediaControllers = getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
                for (mediaController in mediaControllers.getActiveSessions(ComponentName(applicationContext, AiNotificationListenerService::class.java))) {
                    if ("com.android.server.telecom" == mediaController.packageName) {
                        mediaController.dispatchMediaButtonEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK))
                        btnPickUp?.visibility = View.GONE
                        return
                    }
                }
            }
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    private fun removeCallerIdFromSharePref() {
        preferences?.edit()?.remove("callerId")?.apply()
    }
}
