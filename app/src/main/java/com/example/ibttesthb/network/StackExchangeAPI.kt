package com.example.ibttesthb.network

import com.example.ibttesthb.model.ListWrapper
import com.example.ibttesthb.model.QuestionModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StackExchangeAPI {
    @GET("/2.3/questions?pagesize=100&order=desc&sort=creation&site=stackoverflow")
    fun getQuestions() : Call<ListWrapper<QuestionModel>>

    @GET("/2.3/questions?pagesize=100&order=desc&sort=creation&site=stackoverflow")
    fun getQuestionsInterval(@Query ("fromdate") fromDate : Int, @Query ("todate") toDate : Int
                             ) : Call<ListWrapper<QuestionModel>>
}