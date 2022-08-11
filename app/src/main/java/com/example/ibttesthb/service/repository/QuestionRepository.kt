package com.example.ibttesthb.service.repository

import com.example.ibttesthb.service.model.QuestionModel
import com.example.ibttesthb.service.network.StackExchangeAPI
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class QuestionRepository private constructor(/*private val questionDao: QuestionDao*/) {

    private val url = "https://api.stackexchange.com/"

    /*fun selectQuestion(position: Int) = questionDao.selectQuestion(position)
    fun getSelectedQuestion() = questionDao.getSelectedQuestion()
    fun getQuestions() = questionDao.getQuestions()*/

    suspend fun requestQuestion(): MutableList<QuestionModel> {
        val response = createApi().getQuestions()
/*
        questionDao.updateQuestions(response.items)
*/
        return response.items
    }

    suspend fun requestQuestion(from : Int, to : Int): MutableList<QuestionModel> {
        val response = createApi().getQuestionsInterval(from, to)
/*
        questionDao.updateQuestions(response.items)
*/
        return response.items
    }

    private fun createApi() : StackExchangeAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(StackExchangeAPI::class.java)
    }


    companion object {
        @Volatile private var instance: QuestionRepository? = null

        fun getInstance(/*questionDao: QuestionDao*/) =
            instance ?: synchronized(this) {
                instance ?: QuestionRepository(/*questionDao*/).also { instance = it }
            }
    }
}