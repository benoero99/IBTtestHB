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
import com.example.ibttesthb.databinding.FragmentDetailBinding
import com.example.ibttesthb.di.Injector
import java.text.SimpleDateFormat
import java.util.*

// Fragment which shows detailed information about a specific question
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initializeUI() {
        val factory = Injector.provideQuestionFactory()
        viewModel = ViewModelProviders.of(this, factory)[QuestionViewModel::class.java]
        val selectedQuestion = viewModel.getSelectedQuestion().value
        if(selectedQuestion != null) {
            binding.detailTitleTV.text = selectedQuestion.title
            binding.detailTagsTV.text = "Tags: " + concatenateTags(selectedQuestion.tags)
            binding.detailAskerTV.text = "Name: " + selectedQuestion.owner.display_name
            binding.detailReputationScoreTV.text = "Reputation: " + selectedQuestion.owner.reputation.toString()
            binding.detailAnsweredTV.text = if (selectedQuestion.is_answered) "The question is answered" else "The question is not answered"
            binding.detailViewCountTV.text = "Views: " + selectedQuestion.view_count.toString()
            binding.detailAnswerCountTV.text = "Answers: " + selectedQuestion.answer_count.toString()
            binding.detailScoreTV.text = "Score: " + selectedQuestion.score.toString()
            binding.detailCreationDateTV.text = "Creation date: " + convertEpochToDateString(selectedQuestion.creation_date.toLong())
            binding.detailLinkTV.text = selectedQuestion.link

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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