package com.example.ibttesthb.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ibttesthb.QuestionViewModel
import com.example.ibttesthb.R
import com.example.ibttesthb.di.Injector
import java.text.SimpleDateFormat
import java.util.*

// Fragment which shows detailed information about a specific question
class DetailFragment : Fragment() {

    private lateinit var detailTitleTV: TextView
    private lateinit var detailTagsTV: TextView
    private lateinit var detailAskerTV: TextView
    private lateinit var detailReputationScoreTV: TextView
    private lateinit var detailAnsweredTV: TextView
    private lateinit var detailViewCountTV: TextView
    private lateinit var detailAnswerCountTV: TextView
    private lateinit var detailScoreTV: TextView
    private lateinit var detailCreationDateTV: TextView
    private lateinit var detailLinkTV: TextView


    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTVs()
        initializeUI()
    }

    private fun initTVs() {
        detailTitleTV = requireView().findViewById(R.id.detailTitleTV)
        detailTagsTV = requireView().findViewById(R.id.detailTagsTV)
        detailAskerTV = requireView().findViewById(R.id.detailAskerTV)
        detailReputationScoreTV = requireView().findViewById(R.id.detailReputationScoreTV)
        detailAnsweredTV = requireView().findViewById(R.id.detailAnsweredTV)
        detailViewCountTV = requireView().findViewById(R.id.detailViewCountTV)
        detailAnswerCountTV = requireView().findViewById(R.id.detailAnswerCountTV)
        detailScoreTV = requireView().findViewById(R.id.detailScoreTV)
        detailCreationDateTV = requireView().findViewById(R.id.detailCreationDateTV)
        detailLinkTV = requireView().findViewById(R.id.detailLinkTV)
    }

    @SuppressLint("SetTextI18n")
    private fun initializeUI() {
        val factory = Injector.provideQuestionFactory()
        viewModel = ViewModelProviders.of(this, factory)
            .get(QuestionViewModel::class.java)
        val selectedQuestion = viewModel.getSelectedQuestion().value
        if(selectedQuestion != null) {
            detailTitleTV.text = selectedQuestion.title
            detailTagsTV.text = "Tags: " + concatenateTags(selectedQuestion.tags)
            detailAskerTV.text = "Name: " + selectedQuestion.owner.display_name
            detailReputationScoreTV.text = "Reputation: " + selectedQuestion.owner.reputation.toString()
            detailAnsweredTV.text = if (selectedQuestion.is_answered) "The question is answered" else "The question is not answered"
            detailViewCountTV.text = "Views: " + selectedQuestion.view_count.toString()
            detailAnswerCountTV.text = "Answers: " + selectedQuestion.answer_count.toString()
            detailScoreTV.text = "Score: " + selectedQuestion.score.toString()
            detailCreationDateTV.text = "Creation date: " + convertEpochToDateString(selectedQuestion.creation_date.toLong())
            detailLinkTV.text = selectedQuestion.link

        }
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