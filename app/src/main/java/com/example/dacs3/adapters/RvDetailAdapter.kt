package com.example.dacs3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3.EmployeeModelThu
import com.example.dacs3.R
import kotlinx.android.synthetic.main.layout_item.view.*
import java.util.ArrayList

class RvDetailAdapter(private val ds: ArrayList<EmployeeModelThu>) :
    RecyclerView.Adapter<CalDetailAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalDetailAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return CalDetailAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CalDetailAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            tvMieuTa.text = ds[position].empGhiChu
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}