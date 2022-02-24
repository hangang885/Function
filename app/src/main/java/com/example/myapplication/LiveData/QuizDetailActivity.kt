package com.example.myapplication.LiveData

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.databinding.ActivityQuizDetailBinding

class QuizDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizDetailBinding
    private lateinit var model: QuizDetailViewModel

    private val observeListener = Observer<Quiz> {
        it?.let { quiz ->

                binding.liveDataName.text = quiz.name
                binding.liveDataDesc.text = quiz.desc

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProviders.of(this).get(QuizDetailViewModel::class.java)

        if (::model.isInitialized) {
            model.quiz.observe(this, observeListener)
        }
        model.request(2)
    }
}
