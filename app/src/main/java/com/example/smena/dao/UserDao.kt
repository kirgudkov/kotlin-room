package com.example.smena.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smena.model.User

@Dao
interface UserDao {
    @Query("SELECT * from users")
    suspend fun getAll(): List<User>

    @Query("SELECT * from users WHERE id = :id")
    suspend fun getById(id: Int): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE from users")
    suspend fun deleteAll()
}