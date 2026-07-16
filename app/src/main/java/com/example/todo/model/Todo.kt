package com.example.todo.model

import java.time.Instant

data class Todo(
    val id : Long = System.currentTimeMillis(),
    val title : String,
    val contents : String = "",
    val isCompleted : Boolean = false,
    val createdAt : Instant = Instant.now()
)