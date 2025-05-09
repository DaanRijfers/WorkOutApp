package com.example.workoutbuddy.data.repository

import com.example.workoutbuddy.data.local.GoalDao
import com.example.workoutbuddy.data.model.Goal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalRepository @Inject constructor(
    private val goalDao: GoalDao
) {
    suspend fun addGoal(goal: Goal): Result<Long> {
        return try {
            val id = goalDao.insertGoal(goal)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateGoal(goal: Goal): Result<Unit> {
        return try {
            goalDao.updateGoal(goal)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteGoal(goal: Goal): Result<Unit> {
        return try {
            goalDao.deleteGoal(goal)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getGoalById(goalId: Long): Flow<Goal> {
        return goalDao.getGoalById(goalId)
    }

    fun getAllGoals(userId: String): Flow<List<Goal>> {
        return goalDao.getGoalsByUser(userId)
    }

    fun getActiveGoals(userId: String): Flow<List<Goal>> {
        return goalDao.getGoalsByStatus(userId, false)
    }

    fun getCompletedGoals(userId: String): Flow<List<Goal>> {
        return goalDao.getGoalsByStatus(userId, true)
    }
}
