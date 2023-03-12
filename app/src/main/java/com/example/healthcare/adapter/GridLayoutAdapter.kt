package com.example.healthcare.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.healthcare.R
import com.example.healthcare.models.Doctor


class GridLayoutAdapter(context: Context, data: ArrayList<Doctor>) : BaseAdapter() {
    private val mContext: Context = context
    private val mData: ArrayList<Doctor> = data

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): Any {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(mContext).inflate(R.layout.grid_items, parent, false)
        val name = view.findViewById<TextView>(R.id.TVdname)
        val specialist = view.findViewById<TextView>(R.id.TVdspecial)
       //val profile=view.findViewById<ImageView>(R.id.profile)


        name.text = mData[position].name
        specialist.text=mData[position].specialist
        //profile.setImageResource(mData[position].profileimg)
        return view
    }
}