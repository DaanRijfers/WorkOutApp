package com.example.workoutbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.workoutbuddy.data.model.Goal
import com.example.workoutbuddy.data.model.GoalPeriod
import com.example.workoutbuddy.data.model.GoalType
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GoalCard(
    goal: Goal,
    onMarkComplete: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (goal.type) {
                        GoalType.WORKOUT_FREQUENCY -> Icons.Default.Star
                        GoalType.DISTANCE -> Icons.Default.DirectionsRun
                        GoalType.DURATION -> Icons.Default.Timer
                        GoalType.WEIGHT_LOSS -> Icons.Default.Star
                        GoalType.CUSTOM -> Icons.Default.Star
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = getGoalDescription(goal),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = getPeriodText(goal.period),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "Start: ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(goal.startDate)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (goal.completed) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Voltooid",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress indicator (mock data for now)
            val progress = if (goal.completed) 1f else 0.6f
            
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                strokeCap = StrokeCap.Round
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${(progress * 100).toInt()}% voltooid",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                if (!goal.completed && onMarkComplete != null) {
                    Button(
                        onClick = onMarkComplete
                    ) {
                        Text("Voltooid")
                    }
                }
            }
        }
    }
}

@Composable
fun getGoalDescription(goal: Goal): String {
    return when (goal.type) {
        GoalType.WORKOUT_FREQUENCY -> "${goal.targetValue.toInt()} workouts"
        GoalType.DISTANCE -> "${goal.targetValue} km ${goal.workoutType?.let { getWorkoutTypeName(it) } ?: ""}"
        GoalType.DURATION -> "${goal.targetValue.toInt()} minuten ${goal.workoutType?.let { getWorkoutTypeName(it) } ?: ""}"
        GoalType.WEIGHT_LOSS -> "${goal.targetValue} kg afvallen"
        GoalType.CUSTOM -> goal.description ?: "Aangepast doel"
    }
}

@Composable
fun getPeriodText(period: GoalPeriod): String {
    return when (period) {
        GoalPeriod.DAILY -> "Dagelijks"
        GoalPeriod.WEEKLY -> "Wekelijks"
        GoalPeriod.MONTHLY -> "Maandelijks"
    }
}
