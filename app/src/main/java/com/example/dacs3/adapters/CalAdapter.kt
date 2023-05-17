package com.example.dacs3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3.EmployeeModelChi
import com.example.dacs3.EmployeeModelThu
import com.example.dacs3.R
import kotlinx.android.synthetic.main.activity_calendar.view.*
import kotlinx.android.synthetic.main.layout_cal_item.view.*

class CalAdapter(
    private val ds: ArrayList<EmployeeModelChi>
) : RecyclerView.Adapter<CalAdapter.ViewHolder>() {

    //code adapter lắng nghe sự kiện
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    //tạo class ViewHolder
    class ViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_cal_item, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tvMieuTa.text = ds[position].empGhiChu
            tvChiTieu.text = ds[position].empTienChi
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}