package com.example.healthcare

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.APICalls.API
import com.example.healthcare.adapter.DiseaseAdapter
import com.example.healthcare.adapter.GridLayoutAdapter
import com.example.healthcare.fragments.Doctorprofile
import com.example.healthcare.models.Doctor
import com.example.healthcare.utils.BaseActivity
import com.example.healthcare.utils.Constants
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var gridView:GridView?=null
private var arrayList:ArrayList<Doctor>?=null
private var gridAdapter:GridLayoutAdapter?=null

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    private lateinit var mProgressDialog: Dialog
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireContext())

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.tv_progress_text.text = text

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }
    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        showProgressDialog("wait")
        API().getdoctorslist(requireContext()) { doctorlist ->
            if (doctorlist != null) {
                Toast.makeText(context,doctorlist.size.toString(),Toast.LENGTH_SHORT).show()

                val gridView = view.findViewById<GridView>(R.id.idGVdoctor)
                val adapter = GridLayoutAdapter(requireContext(), doctorlist)
                gridView.adapter = adapter

                gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    // handle item click event
                    changefragment(Doctorprofile())
                }

            } else {
                Toast.makeText(context, "Error retrieving doctors list", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }
   fun changefragment(newfragment:Fragment){

       val transaction = fragmentManager?.beginTransaction()
       transaction!!.replace(R.id.frame_layout, newfragment)
       transaction!!.addToBackStack(null)
       transaction.commit()
   }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // BaseActivity().showProgressDialog("wait")

        wissh()
        //showProgressDialog("wait")

       // hideProgressDialog()


        ll_clicn.setOnClickListener {




            Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show()
        }
        val employelist=Constants.getdiseaseList()

        // Assign employeelist to ItemAdapter
        val itemAdapter= DiseaseAdapter(employelist)

        // Set the LayoutManager that
        // this RecyclerView will use.
        val recyclerView: RecyclerView =view.findViewById(R.id.recylerView)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = itemAdapter


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun wissh(){
        val currentTime = LocalTime.now()
        val hour = currentTime.hour

        if (hour in 6..11) {
            wish.setText("Good morning\uD83C\uDF1E !!! ")
            println("Good morning!")
        } else if (hour in 12..17) {
            wish.setText("Good afternoon\uD83C\uDF1E!!!")
            println("Good afternoon!")
        }else if(hour in 18..20){
            wish.setText("Good evening\uD83C\uDF03!!!")

        }
        else {
            println("Good night\uD83C\uDF03!!!")
        }
    }

}