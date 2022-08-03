package com.example.ibttesthb

import android.util.Log
import com.example.ibttesthb.fakedb.QuestionDao
import com.example.ibttesthb.model.ListWrapper
import com.example.ibttesthb.model.QuestionModel
import com.example.ibttesthb.network.StackExchangeAPI
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class QuestionRepository private constructor(private val questionDao: QuestionDao) {

    private val url = "https://api.stackexchange.com/"

    fun selectQuestion(position: Int) = questionDao.selectQuestion(position)

    fun getSelectedQuestion() = questionDao.getSelectedQuestion()

    suspend fun requestQuestion() {
        val response = createApi().getQuestions()
        questionDao.updateQuestions(response.items)
    }

    suspend fun requestQuestionInterval(from : Int, to : Int) {
        val response = createApi().getQuestionsInterval(from, to)
        questionDao.updateQuestions(response.items)
    }

    fun createApi() : StackExchangeAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(StackExchangeAPI::class.java)
    }

    fun getQuestions() = questionDao.getQuestions()

    companion object {
        @Volatile private var instance: QuestionRepository? = null

        fun getInstance(questionDao: QuestionDao) =
            instance ?: synchronized(this) {
                instance ?: QuestionRepository(questionDao).also { instance = it }
            }
    }
}