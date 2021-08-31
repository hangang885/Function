package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null

    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



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


    }

    private fun onClick(view: View) {
        when (view) {
            binding.firebaseButton -> {
                var intent = Intent(this, FireBaseActivity::class.java)
                startActivity(intent)
            }
            binding.customGallery->{
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


        }
    }

}