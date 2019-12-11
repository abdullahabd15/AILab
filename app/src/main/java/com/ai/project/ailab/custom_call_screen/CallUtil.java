package com.ai.project.ailab.custom_call_screen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallUtil {
    private AppCompatActivity activity;
    public static final int READ_CALL_LOG_PERMISSION_CODE = 1234;

    public CallUtil(AppCompatActivity appCompatActivity) {
        this.activity = appCompatActivity;
    }

    public List<CallLogData> getCallHistory() {
        List<CallLogData> callLogDataList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = activity.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                    null, null, null);
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            while (cursor.moveToNext()) {
                CallLogData callLogData = new CallLogData();
                callLogData.phoneNumber = cursor.getString(number);
                String callDate = cursor.getString(date);
                callLogData.callDateTime = new Date(Long.valueOf(callDate));
                callLogData.callDurationInSeconds = cursor.getString(duration);
                String callType = cursor.getString(type);
                callLogData.callTypeAsInt = Integer.parseInt(callType);

                callLogDataList.add(callLogData);
            }
            cursor.close();
        } else {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_CALL_LOG}, READ_CALL_LOG_PERMISSION_CODE);
        }
        return callLogDataList;
    }
}
