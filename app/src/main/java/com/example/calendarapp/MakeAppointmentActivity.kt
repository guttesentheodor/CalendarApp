package com.example.calendarapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.calendarapp.databinding.ActivityMakeAppointmentBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MakeAppointmentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMakeAppointmentBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMakeAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.buttonDone.isClickable = true
        binding.buttonDone.setOnClickListener {
           addAppointment()
            val i = Intent(this,CalendarActivity::class.java)
            startActivity(i)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(): LocalDate {
        val day = binding.datepickerDate.dayOfMonth
        val month = binding.datepickerDate.month+1
        val year =  binding.datepickerDate.year

        var formattedDay = day.toString()
        if(day.toString().length==1){
            formattedDay = "0$day"
        }
        var formattedMonth = month.toString()
        if(month.toString().length==1){
            formattedMonth = "0$month"
        }
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dateString = "$formattedDay-$formattedMonth-$year"
        return LocalDate.parse(dateString, formatter)
    }

    private fun addAppointment(){
        val databaseHandler = DatabaseHandler(this)
        val status = databaseHandler.addAppointment(Appointment(0,binding.edittextTitle.text.toString(),binding.edittextNote.text.toString(),getDate()))
        if(status>-1){
            Toast.makeText(applicationContext,"Appointment saved", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(applicationContext,"Fail", Toast.LENGTH_LONG).show()
        }
    }
}