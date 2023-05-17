package com.example.dacs3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3.EmployeeModelChi
import com.example.dacs3.EmployeeModelThu
import com.example.dacs3.R
import kotlinx.android.synthetic.main.layout_item.view.tvMieuTa
import java.util.ArrayList

class RvAdapter(private val ds: ArrayList<EmployeeModelChi>) :
    RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    //tạo class ViewHolder
    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return RvAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RvAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            tvMieuTa.text = ds[position].empGhiChu
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}