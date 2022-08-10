package com.example.ibttesthb.fragments


import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ibttesthb.QuestionViewModel
import com.example.ibttesthb.R
import com.example.ibttesthb.databinding.FragmentListBinding
import com.example.ibttesthb.databinding.FragmentSearchBinding
import com.example.ibttesthb.di.Injector
import java.text.SimpleDateFormat
import java.util.*


class SearchFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var fromEpoch: Long = Long.MIN_VALUE
    private var toEpoch: Long = Long.MAX_VALUE

    // To know which button text needs to be changed after date pick
    // Pretty bad solution :(
    private var isFrom = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        val factory = Injector.provideQuestionFactory()
        val viewModel = ViewModelProviders.of(this, factory)[QuestionViewModel::class.java]

        binding.searchNewButton.setOnClickListener {
            viewModel.requestQuestions()
            openListFragment()
        }
        binding.fromDateButton.setOnClickListener {
            openDatePickerDialog()
            isFrom = true
        }
        binding.toDateButton.setOnClickListener {
            openDatePickerDialog()
            isFrom = false
        }
        binding.searchFilterButton.setOnClickListener {
            Log.d("epoches", "$fromEpoch,$toEpoch")
            viewModel.requestQuestions(fromEpoch.toInt(), toEpoch.toInt())
            openListFragment()
        }
    }

    fun openDatePickerDialog() {
        val cal = Calendar.getInstance()
        val day  = cal.get(Calendar.DAY_OF_MONTH)
        val month  = cal.get(Calendar.MONTH)
        val year  = cal.get(Calendar.YEAR)
        val dpd = DatePickerDialog(requireContext(),this,year,month,day)
        dpd.setCanceledOnTouchOutside(false)
        dpd.setButton(
            DialogInterface.BUTTON_NEGATIVE, "Cancel",
            DialogInterface.OnClickListener { _, _ ->
                dpd.dismiss()
            })
        dpd.show()
    }

    fun openListFragment() {
        val listFrag = ListFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, listFrag)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }

    // After selecting date the button text change
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = p1
        cal[Calendar.MONTH] = p2
        cal[Calendar.DAY_OF_MONTH] = p3
        val date = cal.time
        val formattedDate = SimpleDateFormat("dd/MM/yyyy").format(date)
        if(isFrom) {
            fromEpoch = date.time / 1000
            binding.fromDateButton.text = "From: $formattedDate"
        } else {
            toEpoch = date.time / 1000
            binding.toDateButton.text = "To: $formattedDate"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}