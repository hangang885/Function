package com.example.myapplication.LiveData

import androidx.lifecycle.ViewModel

data class Quiz(val number:Int, val name: String, val desc: String, val answerType: Int): ViewModel() {

    object AnswerType{
        const val INTEGER = 1
    }
    object Number{
        const val Q1 = 1
        const val Q2 = 2
    }
    var answer = ""
    var result = ""
    var maxValue = 0
}

class QuizMethod {
    val quizArray: HashMap<Int,Quiz>
    get(){
        val hash = hashMapOf(
            Quiz.Number.Q1 to quiz1,
            Quiz.Number.Q2 to quiz2 )
        return hash

    }

    private val quiz1: Quiz
    get(){
        val number = Quiz.Number.Q1
        val name = "name1"
        val desc = "desc1"
        val quiz_ = Quiz(number, name, desc, Quiz.AnswerType.INTEGER)

        return quiz_
    }

    private val quiz2: Quiz
        get(){
            val number = Quiz.Number.Q2
            val name = "name2"
            val desc = "desc2"
            val quiz_ = Quiz(number, name, desc, Quiz.AnswerType.INTEGER)

            return quiz_
        }

    fun searchQuiz(index: Int):Quiz?{
        return quizArray[index]
    }
}