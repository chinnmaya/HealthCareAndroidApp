package com.example.healthcare.dialoughs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcare.R
import com.example.healthcare.adapter.DoctorListDialoughsAdapter
import com.example.healthcare.models.Doctor
import kotlinx.android.synthetic.main.dialog_list.view.*

abstract class DoctorsListDialog(
    context: Context,
    private var list: ArrayList<Doctor>,
    private val title: String = "",

) : Dialog(context) {

    private var adapter: DoctorListDialoughsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null)

        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setUpRecyclerView(view)
    }

    private fun setUpRecyclerView(view: View) {
        view.tvTitle.text = title
        view.rvList.layoutManager = LinearLayoutManager(context)
        adapter = DoctorListDialoughsAdapter(context, list)
        view.rvList.adapter = adapter

        adapter!!.onItemClickListener = object : DoctorListDialoughsAdapter.OnItemClickListener {

            override fun onClick(position: Int, doctor: Doctor) {
                dismiss()
                onItemSelected(doctor)
            }
        }
    }

    protected abstract fun onItemSelected(doctor: Doctor)
}