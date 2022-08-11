package com.example.ibttesthb.service.repository

import com.example.ibttesthb.service.model.QuestionModel
import com.example.ibttesthb.service.network.StackExchangeAPI
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class QuestionRepository private constructor(/*private val questionDao: QuestionDao*/) {

    private val url = "https://api.stackexchange.com/"

    suspend fun requestQuestion(): MutableList<QuestionModel> {
        val response = createApi().getQuestions()
        return response.items
    }

    suspend fun requestQuestion(from : Int, to : Int): MutableList<QuestionModel> {
        val response = createApi().getQuestionsInterval(from, to)
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

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: QuestionRepository().also { instance = it }
            }
    }
}