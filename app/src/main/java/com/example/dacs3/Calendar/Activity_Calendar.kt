package com.example.dacs3.Calendar

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dacs3.*
import com.example.dacs3.R
import com.example.dacs3.adapters.CalAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.activity_calendar.imgAss3
import kotlinx.android.synthetic.main.activity_calendar.imgEdit
import kotlinx.android.synthetic.main.activity_calendar.txtChiTieu
import kotlinx.android.synthetic.main.activity_calendar.txtDateHienThi
import java.util.*
import kotlin.collections.ArrayList

class Activity_Calendar : AppCompatActivity() {

    private var Database: DatabaseReference? = null
    private lateinit var ds: ArrayList<EmployeeModelChi>
    private lateinit var ds2: ArrayList<EmployeeModelThu>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        rvGetIntent.layoutManager = LinearLayoutManager(this)
        rvGetIntent.setHasFixedSize(true)

        ds = arrayListOf<EmployeeModelChi>()
        GetChiTieu()

        ds2 = arrayListOf<EmployeeModelThu>()
        GetThuNhap()

        //Phân trang
        imgEdit.setOnClickListener {
            var goEdit = Intent(this, MainActivity::class.java)
            startActivity(goEdit)
        }

        imgAss3.setOnClickListener {
            var goAss = Intent(this, Activity_Asset::class.java)
            startActivity(goAss)
        }

        btnCalThu.setOnClickListener {
            var CalThu = Intent(this, Activity_Calendar_Detail::class.java)
            startActivity(CalThu)
        }

        //Get ngày tháng hiện tại
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        txtDateHienThi.setOnClickListener {
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    txtDateHienThi.setText("0${month + 1}/$year")
                }, year, month, day
            ).show()
        }
    }

    private fun GetChiTieu() {
        dbRef = FirebaseDatabase.getInstance().getReference("chitieu")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empDataChi = empSnap.getValue((EmployeeModelChi::class.java))
                        ds.add(empDataChi!!)
                    }
                    val mAdapter = CalAdapter(ds)
                    rvGetIntent.adapter = mAdapter
                    mAdapter.setOnItemClickListener(object : CalAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Activity_Calendar, Activity_Update::class.java)
                            //Put dữ liệu
                            intent.putExtra("empId", ds[position].empId)
                            intent.putExtra("empGhiChu", ds[position].empGhiChu)
                            intent.putExtra("empTienChi", ds[position].empTienChi)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //Tính tổng chi tiêu
        Database = FirebaseDatabase.getInstance().reference.child("chitieu")
        Database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var sum = 0
                for (ds in dataSnapshot.children) {
                    val map = ds.value as Map<String, Any>?
                    val price = map!!["empTienChi"]
                    val pValue = price.toString().toInt()
                    sum += pValue
                    txtChiTieu!!.text = sum.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun openUpdateDialog(empGhiChu: String, empTienChi: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val alertDialog = mDialog.create()
        alertDialog.show()

    }

    //Tính tổng thu nhập
    private fun GetThuNhap() {
        dbRef = FirebaseDatabase.getInstance().getReference("thunhap")
        Database = FirebaseDatabase.getInstance().reference.child("thunhap")
        Database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var sum = 0
                for (ds in dataSnapshot.children) {
                    val map = ds.value as Map<String, Any>?
                    val price = map!!["empTienThu"]
                    val pValue = price.toString().toInt()
                    sum += pValue
                    txtThuNhap!!.text = sum.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
