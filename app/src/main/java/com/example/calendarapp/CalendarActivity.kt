package com.example.calendarapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendarapp.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCalendarBinding
    private lateinit var appointmentArrayList : ArrayList<Appointment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val appointmentDates = arrayOf(
            "13-10-2021",
            "15-10-2021",
            "16-10-2021",
            "17-10-2021"
        )

        appointmentArrayList = ArrayList()
        for(i in appointmentTitles.indices){
            val appointment = Appointment(appointmentTitles[i],appointmentNotes[i],appointmentDates[i])
            appointmentArrayList.add(appointment)
        }

        binding.listview.isClickable = true
        binding.listview.adapter = ListAdapter(this, appointmentArrayList)
        binding.listview.setOnItemClickListener {
            parent, view, position, id->
            val appointmentTitle = appointmentTitles[position]
            val appointmentNote = appointmentNotes[position]
            val appointmentDate = appointmentDates[position]

            val i = Intent(this,AppointmentActivity::class.java)
            i.putExtra("title", appointmentTitle)
            i.putExtra("note", appointmentNote)
            i.putExtra("date", appointmentDate)
            startActivity(i)
        }
    }
}