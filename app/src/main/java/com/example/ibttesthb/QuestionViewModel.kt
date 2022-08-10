package com.example.ibttesthb

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

    fun selectQuestion(position : Int) = questionRepository.selectQuestion(position)

    fun getSelectedQuestion() = questionRepository.getSelectedQuestion()

    fun requestQuestions() = viewModelScope.launch(Dispatchers.IO) {
        try {
            questionRepository.requestQuestion()
        } catch (e: Exception) {
            Log.d("apiError", e.message.toString())
        }
    }


    fun requestQuestions(from : Int, to : Int)  = viewModelScope.launch(Dispatchers.IO) {
        try {
            questionRepository.requestQuestionInterval(from, to)
        } catch (e: Exception) {
            Log.d("apiError", e.message.toString())
        }
    }

    fun getQuestions() = questionRepository.getQuestions()
}