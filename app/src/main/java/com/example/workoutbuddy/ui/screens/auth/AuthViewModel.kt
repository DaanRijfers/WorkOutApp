package com.example.workoutbuddy.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutbuddy.data.repository.UserRepository
import com.example.workoutbuddy.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = userRepository.loginUser(email, password)
            result.fold(
                onSuccess = { user ->
                    userPreferences.saveUserId(user.id)
                    onResult(true)
                },
                onFailure = {
                    onResult(false)
                }
            )
        }
    }

    fun register(email: String, username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = userRepository.registerUser(email, username, password)
            result.fold(
                onSuccess = { user ->
                    userPreferences.saveUserId(user.id)
                    onResult(true)
                },
                onFailure = {
                    onResult(false)
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearUserId()
        }
    }
}
