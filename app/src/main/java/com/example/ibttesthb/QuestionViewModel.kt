package com.example.ibttesthb

import androidx.lifecycle.ViewModel

class QuestionViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

    fun selectQuestion(position : Int) = questionRepository.selectQuestion(position)

    fun getSelectedQuestion() = questionRepository.getSelectedQuestion()

    fun requestQuestions() = questionRepository.requestQuestion()
    fun requestQuestions(from : Int, to : Int) = questionRepository.requestQuestionInterval(from, to)

    fun getQuestions() = questionRepository.getQuestions()
}