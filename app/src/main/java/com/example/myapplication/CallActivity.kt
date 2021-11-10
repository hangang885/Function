package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityCallBinding
import com.example.myapplication.databinding.ActivityMapBinding
import java.util.jar.Manifest

class CallActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mBinding.call.setOnClickListener {
            onClick(mBinding.call)
        }

        mBinding.opencall.setOnClickListener {
            onClick(mBinding.opencall)
        }

        mBinding.writeNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }

    fun onClick(view: View) {

        when (view) {
            mBinding.opencall -> {
                var intent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel: ${mBinding.writeNumber.text}"))
                startActivity(intent)
            }
            mBinding.call -> {

                if(CheckPermession()){
                    var intent =
                        Intent(Intent.ACTION_CALL, Uri.parse("tel: ${mBinding.writeNumber.text}"))
                    startActivity(intent)
                }
            }

        }
    }

    fun CheckPermession(): Boolean {
        if ((ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            )) != PackageManager.PERMISSION_GRANTED
        ) {

            return if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.CALL_PHONE
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.CALL_PHONE
                    ), 100
                )
                true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.CALL_PHONE
                    ), 100
                )
                false
            }
        }

        return true
    }

}