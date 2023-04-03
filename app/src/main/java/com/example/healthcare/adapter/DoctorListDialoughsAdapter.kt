package com.example.healthcare.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R
import com.example.healthcare.models.Doctor

import java.util.*

class DoctorListDialoughsAdapter(
    private val context: Context,
    private var list: ArrayList<Doctor>,

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_label_doctor,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]

        if (holder is MyViewHolder) {
            holder.name.text=item.name
            holder.specialist.text=item.specialist



            holder.itemView.setOnClickListener {

                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(position, item)
                }
            }
            holder.email.setOnClickListener {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:${item.email}"))
                context.startActivity(intent)

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.dname)
        val email=view.findViewById<ImageView>(R.id.demail)
        val profile=view.findViewById<ImageView>(R.id.profile)
        val specialist=view.findViewById<TextView>(R.id.dspecialist)
    }

    interface OnItemClickListener {

        fun onClick(position: Int, dcotor: Doctor)
    }
}