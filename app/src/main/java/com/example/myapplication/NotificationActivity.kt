package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.creat.setOnClickListener {
            createNotification()
        }
        binding.remove.setOnClickListener {
            removeNotification()
        }
    }

    fun createNotification(){
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(this,"default")

        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("한강이에요")
        builder.setContentText("한강 알람이라구요")
        builder.setAutoCancel(true)

        var intent: Intent = Intent(this,NotificationActivity::class.java)
        var pendingIntent: PendingIntent = PendingIntent.getActivities(this,0,
            arrayOf(intent),PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(pendingIntent)
        builder.setVibrate(longArrayOf(0,2000, 1000, 3000))


            var notificationManager:NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel( NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT))
        }

        notificationManager.notify(1,builder.build())
    }
    fun removeNotification(){
        NotificationManagerCompat.from(this).cancel(1)
    }
}