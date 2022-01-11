package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.myapplication.databinding.ActivityTtsactivityBinding

class TTSActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityTtsactivityBinding
    lateinit var myTTS: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTtsactivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        myTTS = TextToSpeech(this,this)

        binding.ttsButton.setOnClickListener {
         myTTS.speak(binding.ttxEditext1.text.toString(),TextToSpeech.QUEUE_ADD,null)
        }
    }

    override fun onInit(status: Int) {
        if(binding.ttxEditext1.text.isNullOrEmpty()){
                myTTS.speak("글자를 작성해주세요",TextToSpeech.QUEUE_FLUSH,null)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myTTS.shutdown()
    }
}