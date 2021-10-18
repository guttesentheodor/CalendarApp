package com.example.calendarapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import com.example.calendarapp.databinding.ActivityAppointmentBinding
import com.example.calendarapp.databinding.ActivityMakeAppointmentBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MakeAppointmentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMakeAppointmentBinding
    private lateinit var databaseHandler : DatabaseHandler

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
    private fun addAppointment(){
        var day = binding.datepickerDate.dayOfMonth
        var month = binding.datepickerDate.month
        var year =  binding.datepickerDate.year

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var dateString = ""+day+"-"+(month+1)+"-"+year
        var date = LocalDate.parse(dateString, formatter)

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val status = databaseHandler.addAppointment(Appointment(0,binding.edittextTitle.text.toString(),binding.edittextNote.text.toString(),date))
        if(status>-1){
            Toast.makeText(applicationContext,"Appointment saved", Toast.LENGTH_LONG)
        }
        else{
            Toast.makeText(applicationContext,"Fail", Toast.LENGTH_LONG)
        }
        //insert into database
    }
}