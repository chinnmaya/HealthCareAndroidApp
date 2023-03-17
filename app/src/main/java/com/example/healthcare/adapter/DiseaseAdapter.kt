package com.example.healthcare.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R

class DiseaseAdapter(private val diseaselist:ArrayList<String>,private val fragmentManager: FragmentManager): RecyclerView.Adapter<DiseaseAdapter.MyViewHolder>() {
    private var onClickListener: View.OnClickListener? = null

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
        // Finally add an onclickListener to the item.
        holder.itemView.setOnClickListener {
            // create a bundle to pass data to another fragment
            val bundle = Bundle()
            bundle.putString("myString", currentdiease)

            // create an instance of the second fragment
            //val fragment = DieaseDetectionFragment()
            //fragment.arguments = bundle

            // navigate to the second fragment

            // navigate to the second fragment
           // fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
        }

    }
    // onClickListener Interface
    interface OnClickListener : View.OnClickListener {
        fun onClick(position: Int, model: String)
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diseasname: TextView = itemView.findViewById(R.id.tv_diseas)

    }

}