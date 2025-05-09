package com.example.workoutbuddy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workoutbuddy.data.model.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal): Long

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("SELECT * FROM goals WHERE id = :goalId")
    fun getGoalById(goalId: Long): Flow<Goal>

    @Query("SELECT * FROM goals WHERE userId = :userId ORDER BY startDate DESC")
    fun getGoalsByUser(userId: String): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE userId = :userId AND completed = :completed ORDER BY startDate DESC")
    fun getGoalsByStatus(userId: String, completed: Boolean): Flow<List<Goal>>
}
