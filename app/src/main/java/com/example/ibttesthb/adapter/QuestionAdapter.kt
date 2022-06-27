package com.example.ibttesthb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ibttesthb.R
import com.example.ibttesthb.model.QuestionModel
import java.text.SimpleDateFormat
import java.util.*

// Adapter for the recyclerview
class QuestionAdapter(private val listener: ElementClickListener) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private var data: MutableList<QuestionModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.question, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = data.size

    // Manage textviews depending on the item's position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.item = item
        holder.askerNameTV.text = "Asker: ${item.owner.display_name}"
        holder.titleTV.text = "Title: ${item.title}"
        holder.creationDateTV.text = "Date: ${convertEpochToDateString(item.creation_date.toLong())}"
        holder.viewCountTV.text = "Views: ${item.view_count}"
        holder.answerCountTV.text = "Answers: ${item.answer_count}"
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var item: QuestionModel? = null
        var cardView: CardView
        var askerNameTV : TextView
        var titleTV : TextView
        var creationDateTV : TextView
        var viewCountTV : TextView
        var answerCountTV : TextView

        init {
            itemView.setOnClickListener(this)
            cardView = itemView.findViewById(R.id.questionView)
            askerNameTV = itemView.findViewById(R.id.askerNameTV)
            titleTV = itemView.findViewById(R.id.titleTV)
            creationDateTV = itemView.findViewById(R.id.creationDateTV)
            viewCountTV = itemView.findViewById(R.id.viewCountTV)
            answerCountTV = itemView.findViewById(R.id.answerCountTV)
        }

        override fun onClick(v: View?) {
            listener.onElementClicked(adapterPosition, this)
        }

    }


}