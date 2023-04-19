package com.example.healthcare.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.*
import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import com.example.healthcare.adapter.GridLayoutAdapter
import com.example.healthcare.models.Doctor
import com.example.healthcare.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_clinc_visit.*
import java.util.*
import kotlin.collections.ArrayList

class ClincVisitActivity : BaseActivity() {
    private var gridView: GridView ? = null
    // findViewById<GridView>(R.id.idGVdoctor)
    private var docList:ArrayList<Doctor>?=null
    private var tmpList:ArrayList<Doctor>?=null
    private var gridAdapter: GridLayoutAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clinc_visit)
       setupActionBar()




        docList = ArrayList()
        tmpList = ArrayList()
        fetchdoctorlist()

    }
    private fun fetchdoctorlist(){
        showProgressDialog("Please Wait..")

        API().getdoctorslistbyclicnvisit(this) { doctorlist ->
            if (doctorlist != null) {
                docList!!.addAll(doctorlist)
                tmpList!!.addAll(doctorlist)
                displaydoctors()

            } else {
                Toast.makeText(this, "Error retrieving doctors list", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun displaydoctors(){
        gridView=findViewById<GridView>(R.id.idGVdoctor)
        hideProgressDialog()

        gridAdapter = GridLayoutAdapter(this, tmpList!!)
        gridView!!.adapter = gridAdapter

        gridView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // handle item click event
            val doctor: Doctor = docList!!.get(position)

            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("doctor", doctor)

            startActivity(intent)




            // Toast.makeText(this, tmpList!!.size.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        val item=menu?.findItem(R.id.ic_search)
        val searchView = item?.actionView as androidx.appcompat.widget.SearchView
       // val searchView=item?.actionView as SearchView

        searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false

            }


            override fun onQueryTextChange(text: String?): Boolean {
                tmpList!!.clear()
                val serachtext=text!!.toLowerCase(Locale.getDefault())
                if(serachtext.isNotEmpty()){
                    docList!!.forEach {
                        if(it.name.toLowerCase(Locale.getDefault()).contains(serachtext)){
                            tmpList!!.add(it)
                        }

                    }
                    gridAdapter!!.notifyDataSetChanged()


                }else{
                    tmpList!!.clear()
                    tmpList!!.addAll(docList!!)
                    gridAdapter!!.notifyDataSetChanged()
                }

                return false

            }

        })
        return super.onCreateOptionsMenu(menu)

    }
    private fun setupActionBar() {

        setSupportActionBar(clicn_toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
            actionBar.title = "Clinc Visit"
        }

        clicn_toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}