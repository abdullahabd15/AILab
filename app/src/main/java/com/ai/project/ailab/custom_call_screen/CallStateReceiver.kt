package com.ai.project.ailab.custom_call_screen

import android.content.Context
import android.os.Handler
import android.util.Log

class CallStateReceiver: AiCallStateReceiver() {
    override fun onOutgoingCallStarted(context: Context?, phoneNumber: String?) {
        Handler().postDelayed({
            CustomCallScreenActivity.startCallActivity(context, phoneNumber, false)
        }, 1500)
    }

    override fun onOutgoingCallEnded(context: Context?, phoneNumber: String?) {
        CustomCallScreenActivity.dismissCallActivity()
    }

    override fun onIncomingCallReceived(context: Context?, phoneNumber: String?) {
        Handler().postDelayed({
            CustomCallScreenActivity.startCallActivity(context, phoneNumber, true)
            Log.i("onIncomingCallReceived ", "onIncomingCallReceived fired")
        }, 1500)
    }

    override fun onIncomingCallAnswered(context: Context?, phoneNumber: String?) {
        Handler().postDelayed({
            CustomCallScreenActivity.startCallActivity(context, phoneNumber, true)
            Log.i("onIncomingCallAnswered", "onIncomingCallAnswered fired")
        }, 1500)
    }

    override fun onIncomingCallEnded(context: Context?, phoneNumber: String?) {
        CustomCallScreenActivity.dismissCallActivity()
    }
}