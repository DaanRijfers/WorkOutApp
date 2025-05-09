package com.example.workoutbuddy.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutbuddy.data.model.Goal
import com.example.workoutbuddy.data.model.Workout
import com.example.workoutbuddy.data.repository.GoalRepository
import com.example.workoutbuddy.data.repository.WorkoutRepository
import com.example.workoutbuddy.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val goalRepository: GoalRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val userId = userPreferences.getUserId()
    
    val recentWorkouts: Flow<List<Workout>> = if (userId != null) {
        workoutRepository.getAllWorkouts(userId).map { it.sortedByDescending { workout -> workout.date } }
    } else {
        emptyFlow()
    }
    
    val activeGoals: Flow<List<Goal>> = if (userId != null) {
        goalRepository.getActiveGoals(userId)
    } else {
        emptyFlow()
    }
    
    val totalWorkoutCount: Flow<Int> = if (userId != null) {
        workoutRepository.getTotalWorkoutCount(userId)
    } else {
        emptyFlow()
    }
    
    val totalWorkoutDuration: Flow<Long> = if (userId != null) {
        workoutRepository.getTotalWorkoutDuration(userId).map { it ?: 0L }
    } else {
        emptyFlow()
    }
    
    val totalWorkoutDistance: Flow<Float> = if (userId != null) {
        workoutRepository.getTotalWorkoutDistance(userId).map { it ?: 0f }
    } else {
        emptyFlow()
    }
    
    init {
        checkGoalProgress()
    }
    
    private fun checkGoalProgress() {
        viewModelScope.launch {
            // Logic to check if goals are completed based on workout data
            // This would update goals that have been achieved
        }
    }
}
