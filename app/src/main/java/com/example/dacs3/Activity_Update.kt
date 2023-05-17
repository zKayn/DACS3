package com.example.dacs3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dacs3.Calendar.Activity_Calendar
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update.*

class Activity_Update : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        hienThiThongTin()
        //img Delete
        imgDelete.setOnClickListener {
            deleteReCord(
                intent.getStringExtra("empId").toString()
            )
        }

        //btnUpdate
        btnUpdate.setOnClickListener {
            openUpdateDilog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empGhiChu").toString()
            )
        }
    }

    private fun openUpdateDilog(empId: String, empGhiChu: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)
        mDialog.setView(mDialogView)

        //update thông tin  vào dialog
        val edtEmpGhiChu = mDialogView.findViewById<EditText>(R.id.edtEmpGhiChu)
        val edtEmpTienChi = mDialogView.findViewById<EditText>(R.id.edtEmpTienChi)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        edtEmpGhiChu.setText(intent.getStringExtra("empGhiChu").toString())
        edtEmpTienChi.setText(intent.getStringExtra("empTienChi").toString())

        val alertDialog = mDialog.create()
        alertDialog.show()

        //click btnUpdateData
        btnUpdateData.setOnClickListener {
            updateData(
                empId,
                edtEmpGhiChu.text.toString(),
                edtEmpTienChi.text.toString()
            )
            Toast.makeText(applicationContext, "Update thành công", Toast.LENGTH_SHORT).show()

            //Update lại data lên Dialog
            tvGhiChu.setText(edtEmpGhiChu.text.toString())
            tvTienChi.setText(edtEmpTienChi.text.toString())

            alertDialog.dismiss() // Đóng hộp thoại sau Update
        }
    }

    private fun updateData(id: String, GhiChu: String, TienChi: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("chitieu").child(id)
        val Update = EmployeeModelChi(id, GhiChu, TienChi)
        dbRef.setValue(Update)
    }

    private fun deleteReCord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("chitieu").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Xoá thành công", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Activity_Calendar::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Lỗi khi xoá ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hienThiThongTin() {
        tvGhiChu.text = intent.getStringExtra("empGhiChu")
        tvTienChi.text = intent.getStringExtra("empTienChi")
    }
}