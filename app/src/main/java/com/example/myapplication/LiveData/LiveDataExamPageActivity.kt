package com.example.myapplication.LiveData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.LiveData.Exam1.Live1MainActivity
import com.example.myapplication.databinding.ActivityLiveDataExamPageBinding

class LiveDataExamPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLiveDataExamPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveDataExamPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.exam1.setOnClickListener {
            val intent = Intent(this,QuizDetailActivity::class.java)
            startActivity(intent)
        }
        binding.exam2.setOnClickListener {
            val intent = Intent(this,Live1MainActivity::class.java)
            startActivity(intent)
        }

    }


}