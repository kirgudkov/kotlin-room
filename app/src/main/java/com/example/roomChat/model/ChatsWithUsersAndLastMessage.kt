package com.example.roomChat.model

import androidx.room.ColumnInfo
import androidx.room.Relation

data class ChatsWithUsersAndLastMessage(
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @Relation(parentColumn = "id", entityColumn = "chat_id")
    val messages: List<Message>,
    @Relation(parentColumn = "user_id", entityColumn = "id")
    val users: List<User>
) {
    val user: User?
        get() {
            return if (users.isNotEmpty()) users[0] else null
        }
}