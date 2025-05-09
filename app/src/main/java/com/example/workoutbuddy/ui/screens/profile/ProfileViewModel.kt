package com.example.workoutbuddy.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutbuddy.data.model.User
import com.example.workoutbuddy.data.repository.UserRepository
import com.example.workoutbuddy.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val userId = userPreferences.getUserId()
    
    val user: Flow<User> = if (userId != null) {
        userRepository.getUserById(userId)
    } else {
        emptyFlow()
    }
    
    fun updateProfile(user: User) {
        viewModelScope.launch {
            userRepository.updateUserProfile(user)
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            userPreferences.clearUserId()
        }
    }
}
