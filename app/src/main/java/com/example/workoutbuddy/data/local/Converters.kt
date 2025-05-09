package com.example.workoutbuddy.data.local

import androidx.room.TypeConverter
import com.example.workoutbuddy.data.model.GoalPeriod
import com.example.workoutbuddy.data.model.GoalType
import com.example.workoutbuddy.data.model.WorkoutType
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromWorkoutType(value: WorkoutType): String {
        return value.name
    }

    @TypeConverter
    fun toWorkoutType(value: String): WorkoutType {
        return WorkoutType.valueOf(value)
    }

    @TypeConverter
    fun fromGoalType(value: GoalType): String {
        return value.name
    }

    @TypeConverter
    fun toGoalType(value: String): GoalType {
        return GoalType.valueOf(value)
    }

    @TypeConverter
    fun fromGoalPeriod(value: GoalPeriod): String {
        return value.name
    }

    @TypeConverter
    fun toGoalPeriod(value: String): GoalPeriod {
        return GoalPeriod.valueOf(value)
    }
}
