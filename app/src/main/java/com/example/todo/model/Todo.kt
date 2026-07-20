package com.example.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,

    val contents: String = "",

    val isCompleted: Boolean = false,

    val createdAt: Instant = Instant.now()
)