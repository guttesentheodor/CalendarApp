package com.example.calendarapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ListAdapter(private val context : Activity, private val arrayList : ArrayList<Appointment>) : ArrayAdapter<Appointment>(context, R.layout.list_item, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item,null)

        val appointmentTitle : TextView = view.findViewById(R.id.appointmentTitle)
        val appointmentDate : TextView = view.findViewById(R.id.appointmentDate)

        appointmentTitle.text = arrayList[position].appointmentTitle
        appointmentDate.text = arrayList[position].appointmentDate

        return view
    }

}
