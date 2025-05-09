package com.example.workoutbuddy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val email: String,
    val username: String,
    val password: String,
    val height: Float? = null,
    val weight: Float? = null,
    val age: Int? = null,
    val profilePictureUrl: String? = null
)
