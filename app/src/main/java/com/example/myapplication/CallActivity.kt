package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import com.example.myapplication.databinding.ActivityCallBinding
import com.example.myapplication.databinding.ActivityMapBinding

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

    fun onClick(view:View){

        when(view){
            mBinding.opencall ->{
                var intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: ${mBinding.writeNumber.text}"))
                startActivity(intent)
            }
            mBinding.call ->{
                var intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: ${mBinding.writeNumber.text}"))
                startActivity(intent)
            }

        }
    }
}