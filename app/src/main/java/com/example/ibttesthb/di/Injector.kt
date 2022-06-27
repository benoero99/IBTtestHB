package com.example.ibttesthb.di

import com.example.ibttesthb.fakedb.Database
import com.example.ibttesthb.QuestionRepository
import com.example.ibttesthb.QuestionsModelViewFactory

// Dependency injection
object Injector {
    fun provideQuestionFactory(): QuestionsModelViewFactory {
        val questionRepository = QuestionRepository.getInstance(Database.getInstance().questionDao)
        return QuestionsModelViewFactory(questionRepository)
    }
}