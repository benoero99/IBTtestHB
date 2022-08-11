package com.example.ibttesthb.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ibttesthb.service.repository.QuestionRepository
import com.example.ibttesthb.service.model.QuestionModel
import com.example.ibttesthb.service.model.UiDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SharedViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

    private var questionsLiveData = MutableLiveData<MutableList<QuestionModel>>()
    private var selectedQuestionForUILiveData = MutableLiveData<UiDetailModel>()

    fun getQuestions(): LiveData<MutableList<QuestionModel>> = questionsLiveData

    fun selectQuestion(position : Int) {
        val question = questionsLiveData.value?.get(position)
        selectedQuestionForUILiveData.postValue(question?.toUiDetailModel())
    }

    fun getSelectedQuestion(): LiveData<UiDetailModel> = selectedQuestionForUILiveData

    fun requestQuestions() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val list = questionRepository.requestQuestion()
            questionsLiveData.postValue(list)
        } catch (e: Exception) {
            Log.d("apiError", e.message.toString())
        }
    }

    fun requestQuestions(from : Int, to : Int)  = viewModelScope.launch(Dispatchers.IO) {
        try {
            val list = questionRepository.requestQuestion(from, to)
            questionsLiveData.postValue(list)
        } catch (e: Exception) {
            Log.d("apiError", e.message.toString())
        }
    }

    private fun QuestionModel.toUiDetailModel(): UiDetailModel {
        return UiDetailModel(
            title = title,
            tags = concatenateTags(tags),
            askerName = owner.display_name,
            reputation = owner.reputation.toString(),
            answered = if(is_answered) "The question is answered" else "The question is not answered",
            viewCount = view_count.toString(),
            answerCount = answer_count.toString(),
            score = score.toString(),
            creationDate = convertEpochToDateString(creation_date.toLong()),
            link = link
        )
    }

    private fun concatenateTags(tags: List<String>): String {
        val stringBuilder = StringBuilder()
        tags.forEach {
            stringBuilder.append("$it, ")
        }
        stringBuilder.setLength(stringBuilder.length - 2)
        return stringBuilder.toString()
    }

    private fun convertEpochToDateString(epoch: Long): String {
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(epoch * 1000))
        return date.toString()
    }

}