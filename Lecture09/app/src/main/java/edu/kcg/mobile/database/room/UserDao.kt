package edu.kcg.mobile.database.room

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE name LIKE :name")
    fun findByName(name: String): List<User>

    @Insert
    fun insert(user: User): Long

    @Update
    fun update(users: List<User>): Int

    @Delete
    fun delete(users: List<User>): Int

}