package com.example.todo.data

import androidx.room.TypeConverter
import java.time.Instant

class DateTimeConverter {
    @TypeConverter
    fun fromInstant(instant: Instant) : Long{
        return instant.toEpochMilli()
    }

    @TypeConverter
    fun toInstant(value: Long) : Instant {
        return Instant.ofEpochMilli(value)
    }
}