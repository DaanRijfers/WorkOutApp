package com.example.workoutbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.workoutbuddy.data.model.Workout
import com.example.workoutbuddy.data.model.WorkoutType
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCard(
    workout: Workout,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getIconForWorkoutType(workout.type),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = getWorkoutTypeName(workout.type),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault()).format(workout.date),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${workout.duration} min",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                if (workout.distance != null) {
                    Text(
                        text = "${String.format("%.2f", workout.distance)} km",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun getIconForWorkoutType(type: WorkoutType): ImageVector {
    return when (type) {
        WorkoutType.RUNNING -> Icons.Default.DirectionsRun
        WorkoutType.CYCLING -> Icons.Default.DirectionsRun
        WorkoutType.SWIMMING -> Icons.Default.Pool
        WorkoutType.WEIGHT_TRAINING -> Icons.Default.FitnessCenter
        WorkoutType.YOGA -> Icons.Default.SelfImprovement
        WorkoutType.HIIT -> Icons.Default.FitnessCenter
        WorkoutType.OTHER -> Icons.Default.FitnessCenter
    }
}

@Composable
fun getWorkoutTypeName(type: WorkoutType): String {
    return when (type) {
        WorkoutType.RUNNING -> "Hardlopen"
        WorkoutType.CYCLING -> "Fietsen"
        WorkoutType.SWIMMING -> "Zwemmen"
        WorkoutType.WEIGHT_TRAINING -> "Krachttraining"
        WorkoutType.YOGA -> "Yoga"
        WorkoutType.HIIT -> "HIIT"
        WorkoutType.OTHER -> "Anders"
    }
}
