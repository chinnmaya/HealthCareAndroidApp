package com.example.healthcare.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import com.example.healthcare.adapter.GridLayoutAdapter
import com.example.healthcare.models.Doctor
import com.example.healthcare.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_home_visit.*
import java.util.*
import kotlin.collections.ArrayList

class HomeVisitActivity : BaseActivity() {
    private var docList:ArrayList<Doctor>?=null
    private var tmpList:ArrayList<Doctor>?=null
    private var gridView: GridView ? = null
    private var gridAdapter: GridLayoutAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_visit)
        setupActionBar()
        docList = ArrayList()
        tmpList = ArrayList()
        fetchdoctorlist()

    }
    private fun fetchdoctorlist(){
        showProgressDialog("Please Wait..")
        API().getdocotorlistbyhomeVist(){doctors->
            docList!!.addAll(doctors)
            tmpList!!.addAll(doctors)
            displaydoctors()
            hideProgressDialog()
        }
    }
    private fun displaydoctors(){
        if (docList!= null) {
           // docList!!.addAll(doctorlist)
            //tmpList!!.addAll(doctorlist)
            gridView=findViewById<GridView>(R.id.idGVdoctor)

            gridAdapter = GridLayoutAdapter(this, tmpList!!)
            gridView!!.adapter = gridAdapter

            gridView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                // handle item click event
                //val doctor: Doctor = doctorlist!!.get(position)

                //val intent = Intent(this, ClicnVisitBookingActivity::class.java)
                //intent.putExtra("doctor", doctor)
                //startActivity(intent)




                // Toast.makeText(this, tmpList!!.size.toString(), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error retrieving doctors list", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupActionBar() {

        setSupportActionBar(home_toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
            actionBar.title = "Home Visit"
        }

        home_toolbar.setNavigationOnClickListener { onBackPressed() }
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
}