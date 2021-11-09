package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadCastRece : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //전원연결 및 전원해제 시 Toast 메시지를 띄운다
        if (Intent.ACTION_POWER_CONNECTED == intent.action) {
            Toast.makeText(context, "전원 연결", Toast.LENGTH_SHORT).show()
        } else {
            if (Intent.ACTION_POWER_DISCONNECTED == intent.action) {
                Toast.makeText(context, "전원 연결 해제", Toast.LENGTH_SHORT).show()

            }
        }

    }
}