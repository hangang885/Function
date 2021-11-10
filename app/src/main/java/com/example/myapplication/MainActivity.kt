package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import com.example.myapplication.Mvvm.MvvmExampleActivity
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


    }


    private fun onClick(view: View) {
        when (view) {
            binding.firebaseButton -> {
                var intent = Intent(this, FireBaseActivity::class.java)
                startActivity(intent)
            }
            binding.customGallery -> {
                var intent = Intent(this, CustomGalleryActivity::class.java)
                startActivity(intent)
            }
            binding.camerax -> {
                var intent = Intent(this, CameraXActivity::class.java)
                startActivity(intent)
            }
            binding.camera1 -> {
                var intent = Intent(this, Camera1Activity::class.java)
                startActivity(intent)
            }
            binding.coroutine -> {
                var intent = Intent(this, coroutineActivity::class.java)
                startActivity(intent)
            }
            binding.room -> {
                var intent = Intent(this, RoomActivity::class.java)
                startActivity(intent)
            }
            binding.daumMap -> {
                var intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
            }
            binding.call -> {
                var intent = Intent(this, CallActivity::class.java)
                startActivity(intent)
            }
            binding.textTts -> {
                var intent = Intent(this, TTSActivity::class.java)
                startActivity(intent)
            }
            binding.video -> {
                var intent = Intent(this, VideoActivity::class.java)
                startActivity(intent)
            }
            binding.notification -> {
                var intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
            }
            binding.mvvmExam -> {
                var intent = Intent(this, MvvmExampleActivity::class.java)
                startActivity(intent)
            }


        }
    }

    override fun onResume() {
        super.onResume()
        // 필터를 정의하여 broadCastRece클래스에 전송
        var filter: IntentFilter = IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(mReceiver, filter);

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }


}