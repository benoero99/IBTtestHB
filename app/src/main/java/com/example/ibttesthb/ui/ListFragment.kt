package com.example.ibttesthb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ibttesthb.di.Injector
import com.example.ibttesthb.viewmodel.SharedViewModel
import com.example.ibttesthb.R
import com.example.ibttesthb.adapter.QuestionAdapter
import com.example.ibttesthb.databinding.FragmentListBinding
import com.example.ibttesthb.service.model.QuestionModel

// Shows the list of the requested questions
class ListFragment : Fragment(), QuestionAdapter.ElementClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var madapter: QuestionAdapter
    private lateinit var viewModel: SharedViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        madapter = QuestionAdapter(this)
        binding.questionList.adapter = madapter

        val factory = Injector.provideQuestionFactory()
        viewModel = ViewModelProvider(requireActivity(), factory)[SharedViewModel::class.java]
        viewModel.getQuestions().observe(viewLifecycleOwner) { questions ->
            madapter.update(questions as MutableList<QuestionModel>)
        }
    }

    // Opens DetailFragment when an element is clicked
    override fun onElementClicked(position: Int, holder: QuestionAdapter.ViewHolder) {
        val detFrag = DetailFragment()
        viewModel.selectQuestion(position)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, detFrag)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}