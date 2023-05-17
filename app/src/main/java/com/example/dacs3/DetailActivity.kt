package com.example.dacs3

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dacs3.Calendar.Activity_Calendar
import com.example.dacs3.adapters.RvDetailAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.btnNhap2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnChi
import kotlinx.android.synthetic.main.activity_main.txtDateHienThi
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var ds2: ArrayList<EmployeeModelThu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        dbRef = FirebaseDatabase.getInstance().getReference("thunhap")
        //Xử lý btnNhap
        btnNhap2.setOnClickListener {
            saveThuNhap()
        }

        //Phân trang
        btnChi.setOnClickListener {
            var tienChi = Intent(this, MainActivity::class.java)
            startActivity(tienChi)
        }

        imgLich2.setOnClickListener {
            var goLich = Intent(this, Activity_Calendar::class.java)
            startActivity(goLich)
        }

        imgUpdate2.setOnClickListener {
            var goUpdate = Intent(this, Activity_Update::class.java)
            startActivity(goUpdate)
        }

        imgAss2.setOnClickListener {
            var goAss = Intent(this, Activity_Asset::class.java)
            startActivity(goAss)
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
                    txtDateHienThi.setText("$dayOfMonth/${month + 1}/$year")
                }, year, month, day
            ).show()
        }

        rvIcon2.layoutManager = GridLayoutManager(
            this,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        rvIcon2.setHasFixedSize(true)
        ds2 = arrayListOf<EmployeeModelThu>()
        GetThuNhap()

    }

    private fun saveThuNhap() {
        //Getting value
        val empGhiChu = edtGhiChu2.text.toString()
        val empTienThu = edtTienThu.text.toString()

        //Đẩy DB
        val empId = dbRef.push().key!!
        val employee = EmployeeModelThu(empId, empGhiChu, empTienThu)

        //Kiểm tra điều kiện
        if (empGhiChu.isEmpty()) {
            edtGhiChu.error = "Nhập ghi chú"
            return
        }
        if (empTienThu.isEmpty()) {
            edtTienThu.error = "Nhập số tiền"
            return
        }

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Đã thêm thu nhập", Toast.LENGTH_SHORT).show()
                edtGhiChu2.setText("")
                edtTienThu.setText("")
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
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
                    val mAdapter = RvDetailAdapter(ds2)
                    rvIcon2.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}