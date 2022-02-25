package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Facebook.FacebookLoginActivity
import com.example.myapplication.Google.GoogleLoginActivity
import com.example.myapplication.LiveData.LiveDataExamPageActivity
import com.example.myapplication.Mvvm.Exam1.MvvmExampleActivity
import com.example.myapplication.Naver.NaverLoginActivity
import com.example.myapplication.dagger.Dagger2Activity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    private lateinit var mReceiver: BroadcastReceiver

    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mReceiver = BroadCastRece()



        binding.firebaseButton.setOnClickListener {

            onClick(binding.firebaseButton)
        }
        binding.customGallery.setOnClickListener {
            onClick(binding.customGallery)
        }
        binding.camerax.setOnClickListener {
            onClick(binding.camerax)
        }
        binding.camera1.setOnClickListener {
            onClick(binding.camera1)
        }
        binding.coroutine.setOnClickListener {
            onClick(binding.coroutine)
        }
        binding.room.setOnClickListener {
            onClick(binding.room)
        }
        binding.daumMap.setOnClickListener {
            onClick(binding.daumMap)
        }
        binding.call.setOnClickListener {
            onClick(binding.call)
        }
        binding.textTts.setOnClickListener {
            onClick(binding.textTts)
        }
        binding.video.setOnClickListener {
            onClick(binding.video)
        }
        binding.notification.setOnClickListener {
            onClick(binding.notification)
        }
        binding.mvvmExam.setOnClickListener {
            onClick(binding.mvvmExam)
        }
        binding.kotlin.setOnClickListener {
            onClick(binding.kotlin)
        }

        binding.kakaoLogin.setOnClickListener {
            onClick(binding.kakaoLogin)
        }
        binding.node.setOnClickListener {
            onClick(binding.node)
        }
        binding.webview.setOnClickListener {
            onClick(binding.webview)
        }
        binding.naverMap.setOnClickListener {
            onClick(binding.naverMap)
        }
        binding.LiveData.setOnClickListener {
            onClick(binding.LiveData)
        }
        binding.facebookLogin.setOnClickListener {
            onClick(binding.facebookLogin)
        }
        binding.naverLogin.setOnClickListener {
            onClick(binding.naverLogin)
        }
        binding.biometric.setOnClickListener {
            onClick(binding.biometric)
        }
        binding.calendar.setOnClickListener {
            onClick(binding.calendar)
        }
        binding.customCalendar.setOnClickListener {
            onClick(binding.customCalendar)
        }
        binding.dagger.setOnClickListener {
            onClick(binding.dagger)
        }


    }


    private fun onClick(view: View) {
        when (view) {
            binding.firebaseButton -> {
                val intent = Intent(this, FireBaseActivity::class.java)
                startActivity(intent)
            }
            binding.customGallery -> {
                val intent = Intent(this, CustomGalleryActivity::class.java)
                startActivity(intent)
            }
            binding.camerax -> {
                val intent = Intent(this, CameraXActivity::class.java)
                startActivity(intent)
            }
            binding.camera1 -> {
                val intent = Intent(this, Camera1Activity::class.java)
                startActivity(intent)
            }
            binding.coroutine -> {
                val intent = Intent(this, coroutineActivity::class.java)
                startActivity(intent)
            }
            binding.room -> {
                val intent = Intent(this, RoomActivity::class.java)
                startActivity(intent)
            }
            binding.daumMap -> {
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
            }
            binding.call -> {
                val intent = Intent(this, CallActivity::class.java)
                startActivity(intent)
            }
            binding.textTts -> {
                val intent = Intent(this, TTSActivity::class.java)
                startActivity(intent)
            }
            binding.video -> {
                val intent = Intent(this, VideoActivity::class.java)
                startActivity(intent)
            }
            binding.notification -> {
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
            }
            binding.mvvmExam -> {
                val intent = Intent(this, MvvmExampleActivity::class.java)
                startActivity(intent)
            }
            binding.kotlin -> {
                val intent = Intent(this, KotlinActivity::class.java)
                startActivity(intent)
            }
            binding.kakaoLogin -> {
                val intent = Intent(this, KaKaoLoginActivity::class.java)
                startActivity(intent)
            }
            binding.node -> {
                val intent = Intent(this, NodeActivity::class.java)
                startActivity(intent)
            }
            binding.webview -> {
                val intent = Intent(this, WebviewActivity::class.java)
                startActivity(intent)
            }
            binding.naverMap -> {
                val intent = Intent(this, NaverMapActivity::class.java)
                startActivity(intent)
            }
            binding.LiveData -> {
                val intent = Intent(this, LiveDataExamPageActivity::class.java)
                startActivity(intent)
            }
            binding.facebookLogin -> {
                val intent = Intent(this, FacebookLoginActivity::class.java)
                startActivity(intent)
            }
            binding.naverLogin -> {
                val intent = Intent(this, NaverLoginActivity::class.java)
                startActivity(intent)
            }
            binding.googleLogin -> {
                val intent = Intent(this, GoogleLoginActivity::class.java)
                startActivity(intent)
            }
            binding.biometric -> {
                val intent = Intent(this, BiometricActivity::class.java)
                startActivity(intent)
            }
            binding.calendar -> {
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
            }
            binding.customCalendar -> {
                val intent = Intent(this, CustomCalendarActivity::class.java)
                startActivity(intent)
            }
            binding.dagger -> {
                val intent = Intent(this, Dagger2Activity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        // 필터를 정의하여 broadCastRece클래스에 전송
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(mReceiver, filter)

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }


}