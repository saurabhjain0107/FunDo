package com.example.fundo.view

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.fundo.model.AuthListener
import com.example.fundo.model.Notes

class MyDBHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION) {

    private val TABLE_NAME = "Note_Table"
    private val TABLE_TITLE = "Title"
    private val TABLE_SUBTITLE = "SubTitle"
    private val TABLE_NOTE = "Notes"
    override fun onCreate(db: SQLiteDatabase?) {


        val noteTable = "CREATE TABLE" +
                TABLE_NAME + "(" +
                BaseColumns._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_TITLE + "TEXT," +
                TABLE_SUBTITLE  + "TEXT," +
                TABLE_NOTE + "TEXT," + ")"
        db?.execSQL(noteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    companion object{
        private const val DATABASE_NAME = "NotesDB"
        private const val DATABASE_VERSION = 1

    }
    fun sqlInsertData(title :String = "", subTitle : String = "", notes : String = ""):Boolean{
        val db : SQLiteDatabase = this.writableDatabase
        val constentValues = ContentValues()
        constentValues.put(TABLE_TITLE,title)
        constentValues.put(TABLE_SUBTITLE,subTitle)
        constentValues.put(TABLE_NOTE,notes)
        val insert_data = db.insert(TABLE_NAME,null, constentValues)
        db.close()
        return !insert_data.equals(-1)



    }
}