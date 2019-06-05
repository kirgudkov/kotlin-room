package com.example.roomChat.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomChat.model.Chat
import com.example.roomChat.model.ChatsWithUsersAndLastMessage

@Dao
interface ChatsDao {
    @Query("SELECT * from chats")
    fun getAll(): LiveData<List<Chat>>

    @Transaction
    @Query("SELECT * from chats")
    fun getChatsWithUser(): LiveData<List<ChatsWithUsersAndLastMessage>>

    @Transaction
    @Query("SELECT * from chats WHERE id = :id")
    suspend fun getChatWithUserById(id: Int): List<ChatsWithUsersAndLastMessage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chat: Chat)
}