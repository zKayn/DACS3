package com.example.dacs3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3.EmployeeModelThu
import com.example.dacs3.R
import kotlinx.android.synthetic.main.layout_cal_item.view.*

class CalDetailAdapter (private val ds: ArrayList<EmployeeModelThu>): RecyclerView.Adapter<CalDetailAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_cal_detail_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tvMieuTa.text = ds[position].empGhiChu
            tvChiTieu.text = ds[position].empTienThu
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}