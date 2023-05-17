package com.example.dacs3

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dacs3.Calendar.Activity_Calendar
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_asset.*

class Activity_Asset : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asset)

        //Phân trang

        imgEdit2.setOnClickListener {
            val goEdit = Intent(this,MainActivity::class.java)
            startActivity(goEdit)
        }

        imgLich2.setOnClickListener {
            val goLich = Intent(this, Activity_Calendar::class.java)
            startActivity(goLich)
        }

        val list: ArrayList<PieEntry> = ArrayList()

        list.add(PieEntry(20000f))
        list.add(PieEntry(15000f))
        list.add(PieEntry(20000f))
        list.add(PieEntry(20000f))
        list.add(PieEntry(20000f))
        list.add(PieEntry(30000f))

        val pieDataSet = PieDataSet(list, "list")

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)

        pieDataSet.valueTextSize = 12f

        pieDataSet.valueTextColor = Color.BLACK

        val pieData = PieData(pieDataSet)

        pie_chart.data = pieData

        pie_chart.description.text = ""

        pie_chart.animateY(2000)
    }
}