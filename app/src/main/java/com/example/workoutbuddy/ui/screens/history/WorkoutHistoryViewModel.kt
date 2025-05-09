package com.example.workoutbuddy.ui.screens.history

import androidx.lifecycle.ViewModel
import com.example.workoutbuddy.data.model.Workout
import com.example.workoutbuddy.data.repository.WorkoutRepository
import com.example.workoutbuddy.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class WorkoutHistoryViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val workouts: Flow<List<Workout>> = userPreferences.getUserId()?.let { userId ->
        workoutRepository.getAllWorkouts(userId)
    } ?: emptyFlow()
}
