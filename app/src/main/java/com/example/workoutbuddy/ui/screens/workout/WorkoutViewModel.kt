package com.example.workoutbuddy.ui.screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutbuddy.data.model.Workout
import com.example.workoutbuddy.data.model.WorkoutType
import com.example.workoutbuddy.data.repository.WorkoutRepository
import com.example.workoutbuddy.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    fun addWorkout(
        type: WorkoutType,
        date: Date,
        duration: Long,
        distance: Float? = null,
        caloriesBurned: Int? = null,
        notes: String? = null,
        feeling: Int? = null,
        routeData: String? = null,
        onResult: (Boolean) -> Unit = {}
    ) {
        viewModelScope.launch {
            val userId = userPreferences.getUserId()
            if (userId != null) {
                val workout = Workout(
                    userId = userId,
                    type = type,
                    date = date,
                    duration = duration,
                    distance = distance,
                    caloriesBurned = caloriesBurned,
                    notes = notes,
                    feeling = feeling,
                    routeData = routeData
                )
                
                val result = workoutRepository.addWorkout(workout)
                result.fold(
                    onSuccess = { onResult(true) },
                    onFailure = { onResult(false) }
                )
            } else {
                onResult(false)
            }
        }
    }
    
    fun updateWorkout(workout: Workout, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val result = workoutRepository.updateWorkout(workout)
            result.fold(
                onSuccess = { onResult(true) },
                onFailure = { onResult(false) }
            )
        }
    }
    
    fun deleteWorkout(workout: Workout, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val result = workoutRepository.deleteWorkout(workout)
            result.fold(
                onSuccess = { onResult(true) },
                onFailure = { onResult(false) }
            )
        }
    }
}
