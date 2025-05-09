package com.example.workoutbuddy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workoutbuddy.data.model.Workout
import com.example.workoutbuddy.data.model.WorkoutType
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout): Long

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    fun getWorkoutById(workoutId: Long): Flow<Workout>

    @Query("SELECT * FROM workouts WHERE userId = :userId ORDER BY date DESC")
    fun getWorkoutsByUser(userId: String): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getWorkoutsByDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE userId = :userId AND type = :workoutType ORDER BY date DESC")
    fun getWorkoutsByType(userId: String, workoutType: WorkoutType): Flow<List<Workout>>

    @Query("SELECT COUNT(*) FROM workouts WHERE userId = :userId")
    fun getTotalWorkoutCount(userId: String): Flow<Int>

    @Query("SELECT SUM(duration) FROM workouts WHERE userId = :userId")
    fun getTotalWorkoutDuration(userId: String): Flow<Long?>

    @Query("SELECT SUM(distance) FROM workouts WHERE userId = :userId AND distance IS NOT NULL")
    fun getTotalWorkoutDistance(userId: String): Flow<Float?>
}
