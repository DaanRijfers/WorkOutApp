package com.example.workoutbuddy.data.repository

import com.example.workoutbuddy.data.local.UserDao
import com.example.workoutbuddy.data.model.User
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun registerUser(email: String, username: String, password: String): Result<User> {
        return try {
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                Result.failure(Exception("User with this email already exists"))
            } else {
                val newUser = User(
                    id = UUID.randomUUID().toString(),
                    email = email,
                    username = username,
                    password = password
                )
                userDao.insertUser(newUser)
                Result.success(newUser)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val user = userDao.login(email, password)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid email or password"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(user: User): Result<Unit> {
        return try {
            userDao.updateUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getUserById(userId: String): Flow<User> {
        return userDao.getUserById(userId)
    }
}
