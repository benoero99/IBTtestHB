package com.example.ibttesthb.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ibttesthb.R
import com.example.ibttesthb.viewmodel.SharedViewModel
import com.example.ibttesthb.databinding.FragmentDetailBinding
import com.example.ibttesthb.di.Injector
import java.text.SimpleDateFormat
import java.util.*

// Fragment which shows detailed information about a specific question
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initializeUI() {
        val factory = Injector.provideQuestionFactory()
        viewModel = ViewModelProvider(requireActivity(), factory)[SharedViewModel::class.java]
        binding.vm = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}