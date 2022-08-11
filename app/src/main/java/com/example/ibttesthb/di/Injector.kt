package com.example.ibttesthb.di

import com.example.ibttesthb.service.repository.QuestionRepository
import com.example.ibttesthb.viewmodel.SharedViewModelFactory

// Dependency injection
object Injector {
    fun provideQuestionFactory(): SharedViewModelFactory {
        val questionRepository = QuestionRepository.getInstance()
        return SharedViewModelFactory(questionRepository)
    }
}