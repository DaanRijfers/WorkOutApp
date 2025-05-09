package com.example.workoutbuddy.ui.screens.stats

import androidx.lifecycle.ViewModel
import com.example.workoutbuddy.data.model.Workout
import com.example.workoutbuddy.data.repository.WorkoutRepository
import com.example.workoutbuddy.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val userId = userPreferences.getUserId()
    
    val weeklyWorkouts: Flow<List<Workout>> = if (userId != null) {
        workoutRepository.getWorkoutsForCurrentWeek(userId)
    } else {
        emptyFlow()
    }
    
    val monthlyWorkouts: Flow<List<Workout>> = if (userId != null) {
        workoutRepository.getWorkoutsForCurrentMonth(userId)
    } else {
        emptyFlow()
    }
    
    val totalWorkoutCount: Flow<Int> = if (userId != null) {
        workoutRepository.getTotalWorkoutCount(userId)
    } else {
        emptyFlow()
    }
    
    val totalWorkoutDuration: Flow<Long> = if (userId != null) {
        workoutRepository.getTotalWorkoutDuration(userId).let { flow ->
            flow
        }
    } else {
        emptyFlow()
    }
    
    val totalWorkoutDistance: Flow<Float> = if (userId != null) {
        workoutRepository.getTotalWorkoutDistance(userId).let { flow ->
            flow
        }
    } else {
        emptyFlow()
    }
}
