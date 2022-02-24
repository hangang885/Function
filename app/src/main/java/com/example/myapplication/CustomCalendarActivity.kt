package com.example.myapplication

import android.R
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityCustomCalendarBinding
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*


class CustomCalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomCalendarBinding
    private lateinit var prefs: SharedPreferences
    private var clickDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomCalendarBinding.inflate(layoutInflater)
        val view = binding.root
        prefs = getSharedPreferences("customCalendar", MODE_PRIVATE)
        setContentView(view)
        calendarSetting()
        listenerSetting()

    }

    private fun calendarSetting() {

        prefs = getSharedPreferences("customCalendar", MODE_PRIVATE)
        binding.customCalendar.apply {
            addDecorators(
                TodayDecorator(),
                SaturdayDecorator(),
                SundayDecorator(),
                MemoDecorator(prefs)
            )
            selectionColor =
                ContextCompat.getColor(this@CustomCalendarActivity, R.color.holo_orange_light)

        }


    }


    private fun listenerSetting() {
        binding.customCalendar.setOnDateChangedListener { _, date, _ ->
            val memo = prefs.getString(date.toString(), "")
            Log.d("han_date", date.toString())
            binding.memo.setText(memo)
            clickDate = date.toString()
            binding.customCalendar.addDecorator(ClickDayDecorator(date))

            when {
                memo.isNullOrEmpty() -> {
                    binding.save.text = "저장"
                    binding.delete.visibility = View.GONE

                }
                else -> {
                    binding.save.text = "수정"
                    binding.delete.visibility = View.VISIBLE
                    binding.delete.setOnClickListener {
                        val editor = prefs.edit()
                        editor.remove(date.toString())
                        editor.apply()
                        binding.customCalendar.invalidateDecorators()
                    }
                }
            }
        }
        binding.save.setOnClickListener {
            val memo = binding.memo.text.toString()
            val editor = prefs.edit()
            editor.putString(clickDate, memo)
            editor.apply()
            binding.customCalendar.addDecorator(MemoDecorator(prefs))
        }

    }

}

class ClickDayDecorator(var date: CalendarDay) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        Log.d("han_month",(date.month+1).toString())
        Log.d("han_max",date.calendar.getActualMaximum(Calendar.DAY_OF_MONTH).toString())
        return date.day - 4 < day!!.day && date.day + 4 > day.day
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(10F, Color.parseColor("#FF9800")))
    }

}


class TodayDecorator : DayViewDecorator {
    private var date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
        view?.addSpan(RelativeSizeSpan(2f))
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#fe7274")))
    }
}

class MemoDecorator(preferences: SharedPreferences) : DayViewDecorator {
    private val calendar = Calendar.getInstance()
    private var prefs = preferences
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val memo = prefs.getString(day.toString(), "")
        return memo != ""
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(10F, Color.parseColor("#1D872A")))

    }
}

class SundayDecorator : DayViewDecorator {
    private val calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object : ForegroundColorSpan(Color.RED) {})
    }
}

class SaturdayDecorator : DayViewDecorator {
    private val calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SATURDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object : ForegroundColorSpan(Color.BLUE) {})
    }
}