package com.example.myapplication.LiveData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizDetailViewModel: ViewModel() {

    private val repo =QuizDetailRepository()
    private val _quiz = MutableLiveData<Quiz>()

    val quiz: LiveData<Quiz> = _quiz

    fun request(index:Int){
        repo.getQuiz(index).let{ it ->
            _quiz.value = it
        }
    }
}