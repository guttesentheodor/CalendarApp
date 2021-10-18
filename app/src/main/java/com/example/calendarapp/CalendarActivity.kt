package com.example.calendarapp

import android.content.Intent
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.calendarapp.databinding.ActivityCalendarBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCalendarBinding
    private lateinit var appointmentArrayList : ArrayList<Appointment>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val appointmentTitles = arrayOf(
            "Vagt Vandtårnsvej",
            "Vagt Hedehusene",
            "Bess fødselsdag",
            "Vagt Hedehusene"
        )
        val appointmentNotes = arrayOf(
            "husk at se videoer",
            "charlottegårdsskolen",
            "begynder kl. 14",
            "kør mor og far til lufthavnen først"
        )
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val appointmentDates = arrayOf (
            LocalDate.parse("13-10-2021", formatter),
            LocalDate.parse("15-10-2021",formatter),
            LocalDate.parse("16-10-2021",formatter),
            LocalDate.parse("17-10-2021",formatter)
        )
        */
        appointmentArrayList = getItemsList()
        sortListByDate(appointmentArrayList)
        /*for(i in appointmentArrayList.indices){
            val appointment = Appointment(appointmentArrayList[i].id,appointmentArrayList[i].appointmentTitle,appointmentArrayList[i].appointmentNote,appointmentArrayList[i].appointmentDate)
            appointmentArrayList.add(appointment)
        }*/

        binding.listview.isClickable = true
        binding.listview.adapter = ListAdapter(this, appointmentArrayList)
        binding.listview.setOnItemClickListener {
            parent, view, position, id->
            val appointmentTitle = appointmentArrayList[position].appointmentTitle
            val appointmentNote = appointmentArrayList[position].appointmentNote
            val appointmentDate = appointmentArrayList[position].appointmentDate

            val i = Intent(this,AppointmentActivity::class.java)
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getItemsList(): ArrayList<Appointment> {
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        var appointmentList: ArrayList<Appointment> = databaseHandler.viewAppointment()


        return  appointmentList
    }

    private fun sortListByDate(arrayList: ArrayList<Appointment>): ArrayList<Appointment> {
        arrayList.sortBy{ it.appointmentDate }

        return arrayList

    }
}