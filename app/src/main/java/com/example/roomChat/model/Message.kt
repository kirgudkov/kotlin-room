package com.example.roomChat.model

import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "messages",
    indices = [Index(value = ["id", "sender_id", "chat_id"])],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["sender_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class Message(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "chat_id")
    val chatId: Int,
    val text: String,
    @ColumnInfo(name = "sender_id")
    val senderId: Int,
    val incoming: Boolean
) : Serializable