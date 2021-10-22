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

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "AppointmentDatabase"

        private const val TABLE_CALENDAR = "AppointmentTable"

        private const val KEY_ID = "_id"
        private const val KEY_DATE = "date"
        private const val KEY_TITLE= "title"
        private const val KEY_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CALENDAR + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_TITLE + " TEXT,"+ KEY_NOTE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CALENDAR")
        onCreate(db)
    }
    fun deleteAppointment(id : Int): Int {
        val db = this.writableDatabase

        val success = db.delete(TABLE_CALENDAR, "$KEY_ID=$id",null)

        db.close()
        return success
    }
    fun addAppointment(appointment: Appointment): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_DATE, appointment.appointmentDate.toString())
        contentValues.put(KEY_TITLE, appointment.appointmentTitle)
        contentValues.put(KEY_NOTE, appointment.appointmentNote)

        val success = db.insert(TABLE_CALENDAR, null, contentValues)

        db.close()
        return success
    }
    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    fun viewAppointment(): ArrayList<Appointment> {

        val appointmentList: ArrayList<Appointment> = ArrayList()

        val selectQuery = "SELECT  * FROM $TABLE_CALENDAR"

        val db = this.readableDatabase
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


                val formattedDate = LocalDate.parse(date)

                val currAppointment = Appointment(id = id, appointmentDate = formattedDate, appointmentTitle = title, appointmentNote = note)
                appointmentList.add(currAppointment)

            } while (cursor.moveToNext())
        }
        return appointmentList
    }
}