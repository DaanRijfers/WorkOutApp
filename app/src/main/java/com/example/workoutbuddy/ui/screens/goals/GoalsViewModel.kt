package com.example.workoutbuddy.ui.screens.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutbuddy.data.model.Goal
import com.example.workoutbuddy.data.repository.GoalRepository
import com.example.workoutbuddy.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val userId = userPreferences.getUserId()
    
    val activeGoals: Flow<List<Goal>> = if (userId != null) {
        goalRepository.getActiveGoals(userId)
    } else {
        emptyFlow()
    }
    
    val completedGoals: Flow<List<Goal>> = if (userId != null) {
        goalRepository.getCompletedGoals(userId)
    } else {
        emptyFlow()
    }
    
    fun markGoalAsCompleted(goal: Goal) {
        viewModelScope.launch {
            val updatedGoal = goal.copy(completed = true)
            goalRepository.updateGoal(updatedGoal)
        }
    }
    
    fun addGoal(goal: Goal, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val result = goalRepository.addGoal(goal)
            result.fold(
                onSuccess = { onResult(true) },
                onFailure = { onResult(false) }
            )
        }
    }
    
    fun deleteGoal(goal: Goal, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val result = goalRepository.deleteGoal(goal)
            result.fold(
                onSuccess = { onResult(true) },
                onFailure = { onResult(false) }
            )
        }
    }
}
