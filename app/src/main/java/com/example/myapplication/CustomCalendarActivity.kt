package com.example.myapplication

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.example.myapplication.databinding.ActivityCustomCalendarBinding
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*
import kotlin.collections.HashSet

class CustomCalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomCalendarBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        calendarSetting()
        listenerSetting()
    }

    private fun calendarSetting() {

        binding.customCalendar.apply {
            addDecorators(TodayDecorator(),SaturdayDecorator(),SundayDecorator())
        }


    }

    private fun listenerSetting() {
        binding.customCalendar.setOnDateChangedListener { widget, date, selected ->
            binding.customCalendar.addDecorator(
                EventDecorator(Collections.singleton(date))
            )
        }
    }
}

class TodayDecorator : DayViewDecorator {
    private var date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
        view?.addSpan(RelativeSizeSpan(1f))
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#1D872A")))
    }
}

class EventDecorator(dates: Collection<CalendarDay>) : DayViewDecorator {

    var dates: HashSet<CalendarDay> = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(5F, Color.parseColor("#1D872A")))
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