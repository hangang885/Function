package com.example.myapplication

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.myapplication.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding

    private lateinit var prefs: SharedPreferences

    private var date = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        calendarSetting()
        prefs = getSharedPreferences("Calendar", MODE_PRIVATE)

        listenerSetting()


    }

    private fun calendarSetting() {
        binding.calendar.apply {
            dateTextAppearance = R.style.TextAppearance_AppCompat_Large
            weekDayTextAppearance = R.style.TextAppearance_AppCompat_Medium
        }


    }

    private fun listenerSetting() {
        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.d("han_Calendar", "$year $month $dayOfMonth")
            date = "$year/$month/$dayOfMonth"
            var memo = prefs.getString(date, "")
            if(memo.isNullOrEmpty()){
                binding.save.text = "저장"
                binding.delete.visibility = View.GONE
            }
            else{
                binding.save.text = "수정"
                binding.delete.visibility = View.VISIBLE
            }
            binding.edit.setText(memo)
        }
        binding.save.setOnClickListener {
            var memo = binding.edit.text.toString()
            var editor = prefs.edit()
            editor.putString(date, memo)
            editor.apply()
        }
    }


}