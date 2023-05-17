package com.example.dacs3

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dacs3.Calendar.Activity_Calendar
import com.example.dacs3.SignUpLogin.Activity_Login
import com.example.dacs3.adapters.RvAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.imgLich
import kotlinx.android.synthetic.main.activity_main.txtDateHienThi
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var ds: ArrayList<EmployeeModelChi>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbRef = FirebaseDatabase.getInstance().getReference("chitieu")
        //Xử lý btnNhap
        btnNhap.setOnClickListener {
            saveChiTieu()
        }

        //Phân trang
        btnThu.setOnClickListener {
            var tienThu = Intent(this, DetailActivity::class.java)
            startActivity(tienThu)
        }

        imgLich.setOnClickListener {
            var goLich = Intent(this, Activity_Calendar::class.java)
            startActivity(goLich)
        }

        imgAss.setOnClickListener {
            var goAss = Intent(this, Activity_Asset::class.java)
            startActivity(goAss)
        }

        imgLogin.setOnClickListener {
            var goLogin = Intent(this, Activity_Login::class.java)
            startActivity(goLogin)
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

        rvIcon.layoutManager = GridLayoutManager(
            this,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        rvIcon.setHasFixedSize(true)
        ds = arrayListOf<EmployeeModelChi>()
        GetChiTieu()
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
                    val mAdapter = RvAdapter(ds)
                    rvIcon.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun saveChiTieu() {
        //Getting value
        val empGhiChu = edtGhiChu.text.toString()
        val empTienChi = edtTienChi.text.toString()

        //Đẩy DB
        val empId = dbRef.push().key!!
        val employee = EmployeeModelChi(empId, empGhiChu, empTienChi)

        //Kiểm tra điều kiện
        if (empGhiChu.isEmpty()) {
            edtGhiChu.error = "Nhập ghi chú"
            return
        }
        if (empTienChi.isEmpty()) {
            edtTienChi.error = "Nhập số tiền"
            return
        }

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Đã nhập chi tiêu", Toast.LENGTH_SHORT).show()
                edtGhiChu.setText("")
                edtTienChi.setText("")
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}