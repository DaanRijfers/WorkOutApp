package com.example.workoutbuddy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workoutbuddy.data.model.Goal
import com.example.workoutbuddy.data.model.User
import com.example.workoutbuddy.data.model.Workout

@Database(
    entities = [User::class, Workout::class, Goal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun goalDao(): GoalDao
}
