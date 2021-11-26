package com.example.myapplication.LiveData.Exam1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLive1MainBinding

class Live1MainActivity : AppCompatActivity() {
    private var count = 0
    private var liveText: MutableLiveData<String> = MutableLiveData()
    private var liveText2: MutableLiveData<String> = MutableLiveData<String>().set("Hello World! ${++count}")

    private lateinit var binding: ActivityLive1MainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLive1MainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        liveText.observe(this, Observer {

            binding.liveText.text = it
        })
        liveText2.observe(this, Observer {
            binding.liveText2.text = it
        })

        binding.liveBtn.setOnClickListener {
            liveText.value = "Live Data Hello ${++count}"
            liveText2.value = "Hello World  ${++count}"
        }
    }

    // 함수 정의 방법
    private fun MutableLiveData<String>.set(value: String) : MutableLiveData<String> {
        this.value = value
        return this
    }
}