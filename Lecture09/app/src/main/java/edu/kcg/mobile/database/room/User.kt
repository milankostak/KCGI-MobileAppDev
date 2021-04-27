package edu.kcg.mobile.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "age") var age: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
