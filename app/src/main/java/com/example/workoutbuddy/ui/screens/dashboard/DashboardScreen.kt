package com.example.workoutbuddy.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.workoutbuddy.data.model.WorkoutType
import com.example.workoutbuddy.navigation.Screen
import com.example.workoutbuddy.ui.components.WorkoutCard
import com.example.workoutbuddy.ui.components.WorkoutSummaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val workouts by viewModel.recentWorkouts.collectAsState(initial = emptyList())
    val activeGoals by viewModel.activeGoals.collectAsState(initial = emptyList())
    val workoutCount by viewModel.totalWorkoutCount.collectAsState(initial = 0)
    val totalDuration by viewModel.totalWorkoutDuration.collectAsState(initial = 0L)
    val totalDistance by viewModel.totalWorkoutDistance.collectAsState(initial = 0f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WorkoutBuddy") }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, contentDescription = "Historie") },
                    label = { Text("Historie") },
                    selected = false,
                    onClick = { navController.navigate(Screen.WorkoutHistory.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShowChart, contentDescription = "Statistieken") },
                    label = { Text("Stats") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Stats.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Doelen") },
                    label = { Text("Doelen") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Goals.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profiel") },
                    label = { Text("Profiel") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Profile.route) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddWorkout.route) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Workout toevoegen")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Samenvatting",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                WorkoutSummaryCard(
                    workoutCount = workoutCount,
                    totalDuration = totalDuration,
                    totalDistance = totalDistance
                )
            }
            
            item {
                Text(
                    text = "Snelle start",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    WorkoutTypeCard(
                        type = WorkoutType.RUNNING,
                        icon = Icons.Default.DirectionsRun,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(Screen.RunningWorkout.route) }
                    )
                    WorkoutTypeCard(
                        type = WorkoutType.WEIGHT_TRAINING,
                        icon = Icons.Default.FitnessCenter,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(Screen.AddWorkout.route) }
                    )
                    WorkoutTypeCard(
                        type = WorkoutType.YOGA,
                        icon = Icons.Default.SelfImprovement,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(Screen.AddWorkout.route) }
                    )
                }
            }
            
            item {
                Text(
                    text = "Actieve doelen",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                if (activeGoals.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Geen actieve doelen",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Stel een doel in om je voortgang bij te houden",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    // Display active goals
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        activeGoals.take(3).forEach { goal ->
                            GoalCard(goal = goal)
                        }
                        
                        if (activeGoals.size > 3) {
                            TextButton(
                                text = "Alle doelen bekijken",
                                onClick = { navController.navigate(Screen.Goals.route) }
                            )
                        }
                    }
                }
            }
            
            item {
                Text(
                    text = "Recente workouts",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            if (workouts.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Geen recente workouts",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Voeg je eerste workout toe om te beginnen",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(workouts.take(5)) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onClick = { /* Navigate to workout details */ }
                    )
                }
                
                if (workouts.size > 5) {
                    item {
                        TextButton(
                            text = "Alle workouts bekijken",
                            onClick = { navController.navigate(Screen.WorkoutHistory.route) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutTypeCard(
    type: WorkoutType,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = type.name,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when (type) {
                    WorkoutType.RUNNING -> "Hardlopen"
                    WorkoutType.WEIGHT_TRAINING -> "Kracht"
                    WorkoutType.YOGA -> "Yoga"
                    else -> type.name
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        androidx.compose.material3.TextButton(
            onClick = onClick
        ) {
            Text(text)
        }
    }
}
