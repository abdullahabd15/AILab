package com.ai.project.ailab.custom_call_screen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public abstract class AiCallStateReceiver extends BroadcastReceiver {
    private int lastState = TelephonyManager.CALL_STATE_IDLE;
    private boolean isIncoming;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                telephonyManager.listen(new AiPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void onOutgoingCallStarted(Context context, String phoneNumber);

    protected abstract void onOutgoingCallEnded(Context context, String phoneNumber);

    protected abstract void onIncomingCallReceived(Context context, String phoneNumber);

    protected abstract void onIncomingCallAnswered(Context context, String phoneNumber);

    protected abstract void onIncomingCallEnded(Context context, String phoneNumber);

    private class AiPhoneStateListener extends PhoneStateListener {
        Context context;
        private AiPhoneStateListener(Context context) {
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            if (lastState == state) {
                return;
            }
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    isIncoming = true;
                    onIncomingCallReceived(context, phoneNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                        isIncoming = false;
                        onOutgoingCallStarted(context, phoneNumber);
                    } else {
                        isIncoming = true;
                        onIncomingCallAnswered(context, phoneNumber);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (isIncoming) {
                        onIncomingCallEnded(context, phoneNumber);
                    } else {
                        onOutgoingCallEnded(context, phoneNumber);
                    }
                    break;
            }
            lastState = state;
        }
    }
}
