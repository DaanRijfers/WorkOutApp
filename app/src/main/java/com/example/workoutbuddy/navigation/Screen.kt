package com.example.workoutbuddy.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object AddWorkout : Screen("add_workout")
    object WorkoutHistory : Screen("workout_history")
    object Stats : Screen("stats")
    object Goals : Screen("goals")
    object Profile : Screen("profile")
    object RunningWorkout : Screen("running_workout")
}
