package com.example.workoutbuddy.di

import android.content.Context
import androidx.room.Room
import com.example.workoutbuddy.data.local.GoalDao
import com.example.workoutbuddy.data.local.UserDao
import com.example.workoutbuddy.data.local.WorkoutDao
import com.example.workoutbuddy.data.local.WorkoutDatabase
import com.example.workoutbuddy.data.repository.GoalRepository
import com.example.workoutbuddy.data.repository.UserRepository
import com.example.workoutbuddy.data.repository.WorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWorkoutDatabase(@ApplicationContext context: Context): WorkoutDatabase {
        return Room.databaseBuilder(
            context,
            WorkoutDatabase::class.java,
            "workout_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: WorkoutDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(database: WorkoutDatabase): WorkoutDao {
        return database.workoutDao()
    }

    @Provides
    @Singleton
    fun provideGoalDao(database: WorkoutDatabase): GoalDao {
        return database.goalDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideWorkoutRepository(workoutDao: WorkoutDao): WorkoutRepository {
        return WorkoutRepository(workoutDao)
    }

    @Provides
    @Singleton
    fun provideGoalRepository(goalDao: GoalDao): GoalRepository {
        return GoalRepository(goalDao)
    }
}
