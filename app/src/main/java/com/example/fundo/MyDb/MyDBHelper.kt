package com.example.fundo.MyDb

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf
import com.example.fundo.model.NoteListener
import com.example.fundo.model.Notes

class MyDBHelper(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    private val TABLE_NAME = "Note"
    private val TABLE_TITLE = "Title"
    private val TABLE_SUBTITLE = "SubTitle"
    private val TABLE_NOTE = "Notes"
    private val FIREBASE_ID = "F_ID"

    override fun onCreate(db: SQLiteDatabase?) {


        val noteTable = "CREATE TABLE " +
                TABLE_NAME + "(" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_TITLE + " TEXT," +
                TABLE_SUBTITLE  + " TEXT," +
                TABLE_NOTE + " TEXT," +
                FIREBASE_ID + " TEXT" + ")"
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
    fun sqlInsertData(note: Notes,listener: (NoteListener) -> Unit){
        val db : SQLiteDatabase = this.writableDatabase
        val constentValues = ContentValues()
        constentValues.put(TABLE_TITLE,note.title)
        constentValues.put(TABLE_SUBTITLE,note.subTitle)
        constentValues.put(TABLE_NOTE,note.notes)
        constentValues.put(FIREBASE_ID,note.id)

        val insert_data = db.insert(TABLE_NAME,null, constentValues)
        if(insert_data>0){
            listener(NoteListener(true,"Data inserted successfully"))
        }else {
            listener(NoteListener(true, "Data inserted failed"))
        }
//        db.close(
    }
    fun fetchData(){
        val db : SQLiteDatabase = this.readableDatabase
        var curser: Cursor = db.rawQuery("select * from $TABLE_NAME", null,null)
        val noteList = ArrayList<Notes>()
        if(curser.moveToNext()){
            noteList.add(Notes(curser.getString(1),curser.getString(2),curser.getString(3),curser.getString(4)))
        }
        curser.close()
    }

    fun updateNote(note: Notes, listener: (NoteListener) -> Unit) {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TABLE_TITLE,note.title)
        contentValues.put(TABLE_SUBTITLE,note.subTitle)
        contentValues.put(TABLE_NOTE,note.notes)
//        contentValues.put(FIREBASE_ID,note.id)

        val insert_data = db.update(TABLE_NAME,contentValues, "$FIREBASE_ID = \"${note.id}\"",null)
        if(insert_data>0){
            listener(NoteListener(true,"Data updated successfully"))
        }else {
            listener(NoteListener(true, "Data updated failed"))
        }
    //        db.close()
    }
    fun deleteNoteFromSql(noteId : String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, " $FIREBASE_ID =  \"${noteId}\"",null)
//        db.close()
    }
}