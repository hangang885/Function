package com.example.myapplication.LiveData

class QuizDetailRepository {
    private var quiz = QuizMethod().searchQuiz(0)

    fun getQuiz(_index: Int):Quiz?{
        val _quiz = QuizMethod().searchQuiz(_index)
        _quiz?.let{
            quiz = _quiz
        }
        return quiz
    }
}