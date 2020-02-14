package com.ai.project.ailab.custom_call_screen;

import android.content.Context;
import android.provider.CallLog;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ai.project.ailab.R;
import com.ai.project.libcore.CallLogData;
import com.ai.project.libui.RecyclerViewItem;

public class CallLogItemView extends RecyclerViewItem<CallLogData> {
    private TextView tvPhoneNumber, tvCallType, tvCallDate, tvDuration;

    public CallLogItemView(Context context) {
        super(context);
    }

    public CallLogItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CallLogItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(CallLogData callLogData) {
        try {
            super.setData(callLogData);
            initComponent();
            setDataToUI(callLogData);
        } catch (Exception e) {
            getActivity().showErrorDialog(e);
        }
    }

    private void initComponent() {
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvCallType = findViewById(R.id.tv_call_type);
        tvCallDate = findViewById(R.id.tv_call_date);
        tvDuration = findViewById(R.id.tv_duration);
    }

    private void setDataToUI(CallLogData callLogData) {
        tvPhoneNumber.setText(callLogData.phoneNumber);
        tvCallDate.setText(callLogData.callDateTime.toString());
        tvDuration.setText(callLogData.callDurationInSeconds);

        switch (callLogData.callTypeAsInt) {
            case CallLog.Calls.INCOMING_TYPE:
                tvCallType.setText("PANGGILAN MASUK");
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                tvCallType.setText("PANGGILAN KELUAR");
                break;
            case CallLog.Calls.MISSED_TYPE:
                tvCallType.setText("PANGGILAN TAK TERJAWAB");
                break;
        }
    }
}
