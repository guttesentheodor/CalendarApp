package com.example.calendarapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.calendarapp.databinding.ActivityCalendarBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCalendarBinding
    private lateinit var appointmentArrayList : ArrayList<Appointment>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appointmentArrayList = getItemsList()
        sortListByDate(appointmentArrayList)

        binding.listview.isClickable = true
        binding.listview.adapter = ListAdapter(this, appointmentArrayList)

        binding.listview.setOnItemClickListener {
            parent, view, position, id->
            val appointmentId = appointmentArrayList[position].id
            val appointmentTitle = appointmentArrayList[position].appointmentTitle
            val appointmentNote = appointmentArrayList[position].appointmentNote
            val appointmentDate = appointmentArrayList[position].appointmentDate

            val i = Intent(this,AppointmentActivity::class.java)
            i.putExtra("id",appointmentId)
            i.putExtra("title", appointmentTitle)
            i.putExtra("note", appointmentNote)
            i.putExtra("date", appointmentDate.toString())
            startActivity(i)
        }

        binding.fab.isClickable = true
        binding.fab.setOnClickListener {
            val i = Intent(this,MakeAppointmentActivity::class.java)
            startActivity(i)
        }
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun createNotification(){

    }

    private fun deleteOldAppointment(){

    }

    private fun archiveOldAppointment(){

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getItemsList(): ArrayList<Appointment> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewAppointment()
    }

    private fun sortListByDate(arrayList: ArrayList<Appointment>): ArrayList<Appointment> {
        arrayList.sortBy{ it.appointmentDate }

        return arrayList
    }
}