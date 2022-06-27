package com.example.ibttesthb.fakedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ibttesthb.model.QuestionModel

// Queries for the fake database
class QuestionDao {
    private var questionList = mutableListOf<QuestionModel>()
    private val questions = MutableLiveData<List<QuestionModel>>()
    private val selectedQuestion = MutableLiveData<QuestionModel>()

    init {
        questions.value = questionList
    }

    fun selectQuestion(position: Int) {
        selectedQuestion.value = questionList[position]
    }

    fun getSelectedQuestion(): LiveData<QuestionModel> {
        return selectedQuestion
    }

    // Update whole list when we request questions from stack exchange api
    fun updateQuestions(searchedQuestions : MutableList<QuestionModel>) {
        questionList = searchedQuestions
        questions.value = questionList
    }

    // Get already stored questions from the "database"
    fun getQuestions(): LiveData<List<QuestionModel>> {
        return questions
    }
}