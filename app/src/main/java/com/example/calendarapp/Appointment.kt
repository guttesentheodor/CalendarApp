package com.example.calendarapp

import java.time.LocalDate

data class Appointment(var id : Int, var appointmentTitle : String, var appointmentNote : String, var appointmentDate : LocalDate)
