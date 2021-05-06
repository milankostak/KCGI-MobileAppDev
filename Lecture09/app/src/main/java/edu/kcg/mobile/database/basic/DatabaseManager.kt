package edu.kcg.mobile.database.basic

import android.content.ContentValues

class DatabaseManager(private val db: DatabaseHandler) {

    fun insertValues(user: User): Long {
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_AGE, user.age)
        }
        return db.writableDatabase.insert(TABLE_USERS, null, contentValues)
    }

    fun updateByName(user: User): Int {
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_AGE, user.age)
        }
        return db.writableDatabase
            .update(TABLE_USERS, contentValues, "$COLUMN_NAME LIKE ?", listOf(user.name).toTypedArray())
    }

    fun deleteByName(name: String): Int {
        return db.writableDatabase
            .delete(TABLE_USERS, "$COLUMN_NAME LIKE ?", listOf(name).toTypedArray())
    }

    fun getAll(): MutableList<User> {
        val projection = arrayOf(COLUMN_NAME, COLUMN_AGE)
        val sortOrder = "$COLUMN_NAME DESC"

        val cursor = db.writableDatabase
            .query(TABLE_USERS, projection, null, null, null, null, sortOrder)

        val users = mutableListOf<User>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val age = cursor.getInt(1)
            users.add(User(name, age))
        }
        cursor.close()

        return users
    }

}