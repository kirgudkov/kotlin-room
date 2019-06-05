package com.example.roomChat.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.roomChat.model.Message

@Dao
interface MessagesDao {
    @Query("SELECT * from messages WHERE chat_id = :chatId")
    suspend fun getAll(chatId: Int):List<Message>

    @Query("SELECT * FROM messages WHERE chat_id = :chatId ORDER BY ID DESC LIMIT 1")
    fun getLast(chatId: Int): LiveData<Message>

    @Insert(onConflict = REPLACE)
    suspend fun insert(message: Message)

    @Query("DELETE from messages")
    suspend fun deleteAll()
}