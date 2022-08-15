package com.kv.workoutapp

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kv.workoutapp.databinding.ActivityExerciseBinding
import com.kv.workoutapp.databinding.ItemExerciseBinding

class ExerciseAdapter(val items : ArrayList<Exerscise>) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>(){
    class ViewHolder(binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model : Exerscise = items[position]
        holder.title.text=model.id.toString()
        when{
            model.isSelected->{
                holder.title.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_exercise_bg_selected)
                holder.title.layoutParams.height= 120
                holder.title.layoutParams.width=120
                holder.title.setTextSize(25F)
                holder.title.setTextColor(Color.parseColor("#FFFFFFFF"))
            }
            model.isCompleted->{
                holder.title.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_exercise_bg_completed)
                holder.title.layoutParams.height=100
                holder.title.layoutParams.width=100
                holder.title.setTextSize(15F)
                holder.title.setTextColor(Color.parseColor("#FF000000"))

            }
            else ->{
                holder.title.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_exercise_bg)
                holder.title.width=35
                holder.title.height=35
                holder.title.setTextColor(Color.parseColor("#FF000000"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}