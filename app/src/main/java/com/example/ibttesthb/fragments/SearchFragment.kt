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
import com.example.ibttesthb.di.Injector
import java.text.SimpleDateFormat
import java.util.*


class SearchFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var searchNewButton: Button
    private lateinit var fromDateButton: Button
    private lateinit var toDateButton: Button
    private lateinit var searchFilterButton: Button

    private var fromEpoch: Long = Long.MIN_VALUE
    private var toEpoch: Long = Long.MAX_VALUE

    // To know which button text needs to be changed after date pick
    // Pretty bad solution :(
    private var isFrom = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchNewButton = view.findViewById(R.id.searchNewButton)
        fromDateButton = view.findViewById(R.id.fromDateButton)
        toDateButton = view.findViewById(R.id.toDateButton)
        searchFilterButton = view.findViewById(R.id.searchFilterButton)
        initializeUI()
    }

    private fun initializeUI() {
        val factory = Injector.provideQuestionFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(QuestionViewModel::class.java)



        searchNewButton.setOnClickListener {
            viewModel.requestQuestions()
            openListFragment()
        }
        fromDateButton.setOnClickListener {
            openDatePickerDialog()
            isFrom = true
        }
        toDateButton.setOnClickListener {
            openDatePickerDialog()
            isFrom = false
        }
        searchFilterButton.setOnClickListener {
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
            fromDateButton.text = "From: $formattedDate"
        } else {
            toEpoch = date.time / 1000
            toDateButton.text = "To: $formattedDate"
        }
    }
}