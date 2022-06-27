package com.example.ibttesthb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class QuestionsModelViewFactory(private val questionRepository: QuestionRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionViewModel(questionRepository) as T
    }
}