package com.example.calendarapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendarapp.databinding.ActivityAppointmentBinding

class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAppointmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appointmentTitle =  intent.getStringExtra("title")
        val appointmentNote =  intent.getStringExtra("note")
        val appointmentDate =  intent.getStringExtra("date")

        binding.appointmentTitle.text = appointmentTitle
        binding.appointmentNote.text = appointmentNote
        binding.appointmentDate.text = appointmentDate


    }
}