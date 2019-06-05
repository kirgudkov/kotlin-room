package com.example.smena.repository

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.smena.dao.ChatsDao
import com.example.smena.dao.MessagesDao
import com.example.smena.dao.UserDao
import com.example.smena.model.Chat
import com.example.smena.model.Message
import com.example.smena.model.User

@Database(entities = [Message::class, User::class, Chat::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messagesDao(): MessagesDao
    abstract fun chatsDao(): ChatsDao
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "smena").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}