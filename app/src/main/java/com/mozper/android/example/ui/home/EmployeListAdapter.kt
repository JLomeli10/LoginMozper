package com.mozper.android.example.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mozper.android.example.R
import com.mozper.android.example.data.model.Employee
import com.squareup.picasso.Picasso

class EmployeListAdapter(private val data: List<Employee>, private val onClick: (Employee) -> Unit) :
    RecyclerView.Adapter<EmployeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.employe_list, parent, false),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employe = data[position]
        holder.bind(employe)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View,private val onClick: (Employee) -> Unit) : RecyclerView.ViewHolder(view) {
        private val nameEmployee: TextView = view.findViewById(R.id.tv_name)
        private val icEmployee: ImageView = view.findViewById(R.id.ic_employe)
        private val rate: TextView = view.findViewById(R.id.tv_rate)
        private var currentEmployee: Employee? = null

        init {
            view.setOnClickListener {
                currentEmployee?.let {
                    onClick(it)
                }
            }
        }

        fun bind(employee: Employee) {
            currentEmployee = employee
            nameEmployee.text = employee.firstName + "\n" + employee.lastName
            Picasso.get().load(employee.image).into(icEmployee)
            rate.text = employee.rating.toString()
        }
    }
}