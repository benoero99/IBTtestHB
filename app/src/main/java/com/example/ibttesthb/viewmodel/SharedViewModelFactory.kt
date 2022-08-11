package com.example.ibttesthb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ibttesthb.service.repository.QuestionRepository

@Suppress("UNCHECKED_CAST")
class SharedViewModelFactory(private val questionRepository: QuestionRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SharedViewModel(questionRepository) as T
    }
}