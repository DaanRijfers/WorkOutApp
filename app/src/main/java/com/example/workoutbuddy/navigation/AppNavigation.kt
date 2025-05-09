package com.example.workoutbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.workoutbuddy.ui.screens.auth.LoginScreen
import com.example.workoutbuddy.ui.screens.auth.RegisterScreen
import com.example.workoutbuddy.ui.screens.dashboard.DashboardScreen
import com.example.workoutbuddy.ui.screens.goals.GoalsScreen
import com.example.workoutbuddy.ui.screens.history.WorkoutHistoryScreen
import com.example.workoutbuddy.ui.screens.profile.ProfileScreen
import com.example.workoutbuddy.ui.screens.stats.StatsScreen
import com.example.workoutbuddy.ui.screens.workout.AddWorkoutScreen
import com.example.workoutbuddy.ui.screens.workout.RunningWorkoutScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.AddWorkout.route) {
            AddWorkoutScreen(navController = navController)
        }
        composable(Screen.WorkoutHistory.route) {
            WorkoutHistoryScreen(navController = navController)
        }
        composable(Screen.Stats.route) {
            StatsScreen(navController = navController)
        }
        composable(Screen.Goals.route) {
            GoalsScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.RunningWorkout.route) {
            RunningWorkoutScreen(navController = navController)
        }
    }
}
