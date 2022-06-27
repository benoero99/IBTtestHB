package com.example.ibttesthb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.ibttesthb.di.Injector
import com.example.ibttesthb.QuestionViewModel
import com.example.ibttesthb.R
import com.example.ibttesthb.adapter.QuestionAdapter
import com.example.ibttesthb.model.QuestionModel

// Shows the list of the requested questions
class ListFragment : Fragment(), QuestionAdapter.ElementClickListener {

    private lateinit var recycler: RecyclerView
    private lateinit var madapter: QuestionAdapter
    private lateinit var viewModel: QuestionViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        recycler = requireView().findViewById(R.id.questionList)
        madapter = QuestionAdapter(this)
        recycler.adapter = madapter

        val factory = Injector.provideQuestionFactory()
        viewModel = ViewModelProviders.of(this, factory)
            .get(QuestionViewModel::class.java)
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

}