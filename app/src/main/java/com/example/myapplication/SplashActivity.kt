package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivitySplashBinding? = null

    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding  .inflate(layoutInflater)
        setContentView(binding.root)

        var handler:Handler = Handler()
        handler.postDelayed(Runnable {
            run {
                var intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        },3000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }


}

