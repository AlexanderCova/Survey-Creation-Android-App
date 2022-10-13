package io.github.alexandercova.surveycreationapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.alexandercova.surveycreationapp.R

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var surveyNames = arrayListOf<String>()
    var surveyPoints = arrayListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.surveyName.text = surveyNames[position]
        holder.surveyPoints.text = (surveyPoints[position] - 1).toString()
    }

    override fun getItemCount(): Int {
        return surveyNames.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var surveyName: TextView
        var surveyPoints : TextView

        init {
            surveyName = itemView.findViewById(R.id.surveyNameText)
            surveyPoints = itemView.findViewById(R.id.surveyPointsText)
        }
    }

}