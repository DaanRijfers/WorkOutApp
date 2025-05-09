package com.example.workoutbuddy.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

enum class GoalType {
    WORKOUT_FREQUENCY,
    DISTANCE,
    DURATION,
    WEIGHT_LOSS,
    CUSTOM
}

enum class GoalPeriod {
    DAILY,
    WEEKLY,
    MONTHLY
}

@Entity(
    tableName = "goals",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val type: GoalType,
    val period: GoalPeriod,
    val targetValue: Float,
    val startDate: Date,
    val endDate: Date? = null,
    val workoutType: WorkoutType? = null,
    val description: String? = null,
    val completed: Boolean = false
)
