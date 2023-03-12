package com.example.healthcare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R

class DiseaseAdapter(private val diseaselist:ArrayList<String>): RecyclerView.Adapter<DiseaseAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for each item and return a new ViewHolder object
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_diease, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return diseaselist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentdiease = diseaselist[position]
        holder.diseasname.text =currentdiease

    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diseasname: TextView = itemView.findViewById(R.id.tv_diseas)

    }

}