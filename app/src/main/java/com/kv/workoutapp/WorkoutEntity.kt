package com.kv.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History-table")
data class WorkoutEntity(
    @PrimaryKey
    val date: String
)
