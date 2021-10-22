package com.example.calendarapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.calendarapp.databinding.ActivityAppointmentBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAppointmentBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appointmentId =  intent.getIntExtra("id",-99)
        val appointmentTitle =  intent.getStringExtra("title")
        val appointmentNote =  intent.getStringExtra("note")
        val appointmentDate =  intent.getStringExtra("date")

        val date = LocalDate.parse(appointmentDate)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        binding.appointmentTitle.text = appointmentTitle
        binding.appointmentNote.text = appointmentNote
        binding.appointmentDate.text = date.format(formatter).toString()

        binding.buttonDelete.isClickable = true
        binding.buttonDelete.setOnClickListener {
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            databaseHandler.deleteAppointment(appointmentId)
            val i = Intent(this,CalendarActivity::class.java)
            startActivity(i)
        }
    }
}