package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityConstraintLayoutBinding

class ConstraintLayoutActivity : AppCompatActivity() {
    private lateinit var binding : ActivityConstraintLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityConstraintLayoutBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}