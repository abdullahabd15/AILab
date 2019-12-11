package com.ai.project.ailab.custom_call_screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.project.ailab.R
import com.ai.project.libcore.AiCallUtil
import com.ai.project.libcore.CallLogData
import com.ai.project.libui.AiActivity
import com.ai.project.libui.AiRecyclerView
import com.ai.project.libui.AiRecyclerViewAdapter

class DialerActivity : AiActivity() {
    private var etDialPhone: EditText? = null
    private var btnCall: Button? = null
    private var btnCallLog: Button? = null
    private var rvCallLog: AiRecyclerView? = null
    private var adapter: AiRecyclerViewAdapter<CallLogData>? = null
    private var callLogList: MutableList<CallLogData>? = ArrayList()
    private var callUtil: AiCallUtil? = null

    companion object {
        const val CALL_PHONE_PERMISSION_CODE = 888
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_dialer)
            initObject()
            initComponent()
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    override fun onStart() {
        try {
            super.onStart()
            checkingPermission()
            etDialPhone?.setText("")
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    @Throws(Exception::class)
    private fun initObject() {
        callUtil = AiCallUtil(this)
    }

    private fun initComponent() {
        etDialPhone = findViewById(R.id.et_dial)
        btnCall = findViewById(R.id.btn_call)
        btnCallLog = findViewById(R.id.btn_call_log)
        rvCallLog = findViewById(R.id.rv_call_log)

        initActionBar("Dial Phone")
        initRecyclerView()
        initComponentListener()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvCallLog?.setDefaultDecoration()
        rvCallLog?.layoutManager = layoutManager
        adapter = AiRecyclerViewAdapter(this, R.layout.card_call_log_item, callLogList)
        adapter?.setHasStableIds(true)
        rvCallLog?.adapter = adapter
    }

    private fun initComponentListener() {
        btnCall?.setOnClickListener {
            if (etDialPhone?.text.toString().isNotEmpty()) {
                saveCallerIdToSharedPref(etDialPhone?.text.toString())
                makeCall()
            } else {
                etDialPhone?.error = "Input not valid"
            }
        }

        btnCallLog?.setOnClickListener {
            val dataList: MutableList<CallLogData>? = callUtil?.getCallHistory()
            dataList?.sortByDescending {
                it.callDateTime
            }
            callLogList?.clear()
            if (dataList != null) {
                callLogList?.addAll(dataList)
            }
            adapter?.notifyDataSetChanged()
        }
    }

    private fun checkingPermission() {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_PERMISSION_CODE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivityForResult(intent, 200)
            }
        }

        if (!Settings.Secure.getString(this.contentResolver, "enabled_notification_listeners").contains(applicationContext.packageName)) {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
    }

    private fun makeCall() {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + etDialPhone?.text.toString())
            startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_PERMISSION_CODE)
        }
    }

    private fun saveCallerIdToSharedPref(callerId: String) {
        val preference: SharedPreferences = applicationContext.getSharedPreferences("call", Context.MODE_PRIVATE)
        preference.edit().putString("callerId", callerId).apply()
    }
}
