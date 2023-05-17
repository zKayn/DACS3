package com.example.dacs3.Calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dacs3.*
import com.example.dacs3.R
import com.example.dacs3.adapters.CalDetailAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_calendar_detail.*

class Activity_Calendar_Detail : AppCompatActivity() {

    private var Database: DatabaseReference? = null
    private lateinit var ds: ArrayList<EmployeeModelChi>
    private lateinit var ds2: ArrayList<EmployeeModelThu>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_detail)

        //Phân Trang

        imgEdit2.setOnClickListener {
            var goEdit = Intent(this, MainActivity::class.java)
            startActivity(goEdit)
        }

        imgAss4.setOnClickListener {
            var goAss = Intent(this, Activity_Asset::class.java)
            startActivity(goAss)
        }

        btnCalChi2.setOnClickListener {
            var CalChi = Intent(this, Activity_Calendar::class.java)
            startActivity(CalChi)
        }

        rvGetIntent2.layoutManager = LinearLayoutManager(this)
        rvGetIntent2.setHasFixedSize(true)

        ds = arrayListOf<EmployeeModelChi>()
        GetChiTieu()

        ds2 = arrayListOf<EmployeeModelThu>()
        GetThuNhap()
    }

    private fun GetThuNhap() {
        dbRef = FirebaseDatabase.getInstance().getReference("thunhap")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ds2.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empDataThu = empSnap.getValue((EmployeeModelThu::class.java))
                        ds2.add(empDataThu!!)
                    }
                    val mAdapter2 = CalDetailAdapter(ds2)
                    rvGetIntent2.adapter = mAdapter2
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        Database = FirebaseDatabase.getInstance().reference.child("thunhap")
        Database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var sum = 0
                for (ds in dataSnapshot.children) {
                    val map = ds.value as Map<String, Any>?
                    val price = map!!["empTienThu"]
                    val pValue = price.toString().toInt()
                    sum += pValue
                    txtThuNhap2!!.text = sum.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun GetChiTieu() {
        dbRef = FirebaseDatabase.getInstance().getReference("chitieu")
        Database = FirebaseDatabase.getInstance().reference.child("chitieu")
        Database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var sum = 0
                for (ds in dataSnapshot.children) {
                    val map = ds.value as Map<String, Any>?
                    val price = map!!["empTienChi"]
                    val pValue = price.toString().toInt()
                    sum += pValue
                    txtChiTieu2!!.text = sum.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}