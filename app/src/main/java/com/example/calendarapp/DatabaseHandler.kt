package com.example.calendarapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "AppointmentDatabase"

        private val TABLE_CALENDAR = "AppointmentTable"

        private val KEY_ID = "_id"
        private val KEY_DATE = "date"
        private val KEY_TITLE= "title"
        private val KEY_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CALENDAR + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_TITLE + " TEXT,"+ KEY_NOTE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CALENDAR")
        onCreate(db)
    }
    fun addAppointment(appointment: Appointment): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_DATE, appointment.appointmentDate.toString()) // Appointment date
        contentValues.put(KEY_TITLE, appointment.appointmentTitle) // Appointment title
        contentValues.put(KEY_NOTE, appointment.appointmentNote) // Appointment note

        // Inserting employee details using insert query.
        val success = db.insert(TABLE_CALENDAR, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }
    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    fun viewAppointment(): ArrayList<Appointment> {

        val appointmentList: ArrayList<Appointment> = ArrayList<Appointment>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_CALENDAR"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var date: String
        var title: String
        var note: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                note = cursor.getString(cursor.getColumnIndex(KEY_NOTE))


                var formattedDate = LocalDate.parse(date)

                val currAppointment = Appointment(id = id, appointmentDate = formattedDate, appointmentTitle = title, appointmentNote = note)
                appointmentList.add(currAppointment)

            } while (cursor.moveToNext())
        }
        return appointmentList
    }
}