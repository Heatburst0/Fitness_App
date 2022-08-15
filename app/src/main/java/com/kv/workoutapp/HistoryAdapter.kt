package com.kv.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kv.workoutapp.databinding.HistoryViewBinding

class HistoryAdapter(private val items : ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.viewHolder>() {

    class viewHolder(binding : HistoryViewBinding):RecyclerView.ViewHolder(binding.root){
        val tvView = binding.view
        val work=binding.workedOn
        val date = binding.date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(HistoryViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val date = items.get(position)
        holder.date.text=date
        if(position%2==0){
            holder.tvView.setBackgroundColor(Color.parseColor("#CEFFCC"))
        }else{
            holder.tvView.setBackgroundColor(Color.parseColor("#FFF7CB"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}