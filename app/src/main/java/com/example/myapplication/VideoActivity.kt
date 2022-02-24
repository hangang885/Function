package com.example.myapplication

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.example.myapplication.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoUri:Uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")

        binding.video.setMediaController(MediaController(this))
        binding.video.setVideoURI(videoUri)

        binding.video.setOnPreparedListener { MediaPlayer.OnPreparedListener { binding.video.start() } }
    }

    override fun onPause() {
        super.onPause()
        if(binding.video.isPlaying) binding.video.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.video.stopPlayback()
    }
}