package com.example.workoutbuddy.data.repository

import com.example.workoutbuddy.data.local.WorkoutDao
import com.example.workoutbuddy.data.model.Workout
import com.example.workoutbuddy.data.model.WorkoutType
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao
) {
    suspend fun addWorkout(workout: Workout): Result<Long> {
        return try {
            val id = workoutDao.insertWorkout(workout)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateWorkout(workout: Workout): Result<Unit> {
        return try {
            workoutDao.updateWorkout(workout)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteWorkout(workout: Workout): Result<Unit> {
        return try {
            workoutDao.deleteWorkout(workout)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getWorkoutById(workoutId: Long): Flow<Workout> {
        return workoutDao.getWorkoutById(workoutId)
    }

    fun getAllWorkouts(userId: String): Flow<List<Workout>> {
        return workoutDao.getWorkoutsByUser(userId)
    }

    fun getWorkoutsByType(userId: String, type: WorkoutType): Flow<List<Workout>> {
        return workoutDao.getWorkoutsByType(userId, type)
    }

    fun getWorkoutsForCurrentWeek(userId: String): Flow<List<Workout>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startDate = calendar.time
        
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val endDate = calendar.time
        
        return workoutDao.getWorkoutsByDateRange(userId, startDate, endDate)
    }

    fun getWorkoutsForCurrentMonth(userId: String): Flow<List<Workout>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = calendar.time
        
        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endDate = calendar.time
        
        return workoutDao.getWorkoutsByDateRange(userId, startDate, endDate)
    }

    fun getTotalWorkoutCount(userId: String): Flow<Int> {
        return workoutDao.getTotalWorkoutCount(userId)
    }

    fun getTotalWorkoutDuration(userId: String): Flow<Long?> {
        return workoutDao.getTotalWorkoutDuration(userId)
    }

    fun getTotalWorkoutDistance(userId: String): Flow<Float?> {
        return workoutDao.getTotalWorkoutDistance(userId)
    }
}
