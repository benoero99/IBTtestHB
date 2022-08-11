package com.example.ibttesthb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ibttesthb.R
import com.example.ibttesthb.databinding.QuestionBinding
import com.example.ibttesthb.service.model.QuestionModel
import java.text.SimpleDateFormat
import java.util.*

// Adapter for the recyclerview
class QuestionAdapter(private val listener: ElementClickListener) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private var data: MutableList<QuestionModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = QuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = data[position]
        holder.bind(question)
    }

    // Update whole recyclerview
    fun update(elements: MutableList<QuestionModel>) {
        data.clear()
        data.addAll(elements)
        notifyDataSetChanged()
    }

    private fun convertEpochToDateString(epoch: Long): String {
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(epoch * 1000))
        return date.toString()
    }

    // Listener, so we can click on every individual element
    interface ElementClickListener {
        fun onElementClicked(position: Int, holder: ViewHolder)
    }

    inner class ViewHolder(private val itemBinding: QuestionBinding) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {
        fun bind(question: QuestionModel) {
            itemView.setOnClickListener(this)
            itemBinding.askerNameTV.text = "Asker: ${question.owner.display_name}"
            itemBinding.titleTV.text = "Title: ${question.title}"
            itemBinding.creationDateTV.text = "Date: ${convertEpochToDateString(question.creation_date.toLong())}"
            itemBinding.viewCountTV.text = "Views: ${question.view_count}"
            itemBinding.answerCountTV.text = "Answers: ${question.answer_count}"
        }

        override fun onClick(v: View?) {
            listener.onElementClicked(adapterPosition, this)
        }

    }


}