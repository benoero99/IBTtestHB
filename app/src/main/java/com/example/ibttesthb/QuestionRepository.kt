package com.example.ibttesthb

import android.util.Log
import com.example.ibttesthb.fakedb.QuestionDao
import com.example.ibttesthb.model.ListWrapper
import com.example.ibttesthb.model.QuestionModel
import com.example.ibttesthb.network.StackExchangeAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionRepository private constructor(private val questionDao: QuestionDao) {

    private val url = "https://api.stackexchange.com/"

    fun selectQuestion(position: Int) = questionDao.selectQuestion(position)

    fun getSelectedQuestion() = questionDao.getSelectedQuestion()

    fun requestQuestion() {
        val questionCall = createApi().getQuestions()
        call(questionCall)
    }

    fun requestQuestionInterval(from : Int, to : Int) {
        val questionCall = createApi().getQuestionsInterval(from, to)
        call(questionCall)
    }

    fun createApi() : StackExchangeAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(StackExchangeAPI::class.java)
    }

    fun call(questionCall : Call<ListWrapper<QuestionModel>>) {
        questionCall.enqueue(object: Callback<ListWrapper<QuestionModel>> {

            override fun onResponse(call: Call<ListWrapper<QuestionModel>>, response: Response<ListWrapper<QuestionModel>>) {
                questionDao.updateQuestions(response.body()!!.items)
            }

            override fun onFailure(call: Call<ListWrapper<QuestionModel>>, t: Throwable) {
                Log.d("apiError", t.message.toString())
            }
        })
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