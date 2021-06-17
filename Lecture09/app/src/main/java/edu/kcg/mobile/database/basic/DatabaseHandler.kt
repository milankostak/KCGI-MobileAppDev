package edu.kcg.mobile.database.basic

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context, name: String, version: Int = 1) :
    SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_USERS(
                id INTEGER PRIMARY KEY autoincrement,
                $COLUMN_NAME varchar(100) NOT NULL,
                $COLUMN_AGE INTEGER NOT NULL
            );"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // empty for now
    }

}