package com.example.smena.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users", indices = [Index(value = ["id"])])
data class User(
    @PrimaryKey
    val id: Int,
    val name: String,
    val avatar: String
) : Serializable