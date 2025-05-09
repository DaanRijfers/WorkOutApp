package com.example.workoutbuddy.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

enum class WorkoutType {
    RUNNING,
    CYCLING,
    SWIMMING,
    WEIGHT_TRAINING,
    YOGA,
    HIIT,
    OTHER
}

@Entity(
    tableName = "workouts",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val type: WorkoutType,
    val date: Date,
    val duration: Long, // in minutes
    val distance: Float? = null, // in kilometers
    val caloriesBurned: Int? = null,
    val notes: String? = null,
    val feeling: Int? = null, // 1-5 rating
    val routeData: String? = null // JSON string of GPS coordinates
)
