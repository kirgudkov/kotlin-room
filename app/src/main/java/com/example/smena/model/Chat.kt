package com.example.smena.model

import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "chats",
    indices = [Index(value = ["id", "user_id"])],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class Chat(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int
) : Serializable