package com.example.workoutbuddy.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.workoutbuddy.data.model.WorkoutType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    var workoutType by remember { mutableStateOf(WorkoutType.RUNNING) }
    var duration by remember { mutableStateOf("30") }
    var distance by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var feeling by remember { mutableStateOf(3f) }
    var date by remember { mutableStateOf(Date()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    
    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout toevoegen") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Terug")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Workout Type Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = when (workoutType) {
                        WorkoutType.RUNNING -> "Hardlopen"
                        WorkoutType.CYCLING -> "Fietsen"
                        WorkoutType.SWIMMING -> "Zwemmen"
                        WorkoutType.WEIGHT_TRAINING -> "Krachttraining"
                        WorkoutType.YOGA -> "Yoga"
                        WorkoutType.HIIT -> "HIIT"
                        WorkoutType.OTHER -> "Anders"
                    },
                    onValueChange = { },
                    label = { Text("Type workout") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Hardlopen") },
                        onClick = {
                            workoutType = WorkoutType.RUNNING
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Fietsen") },
                        onClick = {
                            workoutType = WorkoutType.CYCLING
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Zwemmen") },
                        onClick = {
                            workoutType = WorkoutType.SWIMMING
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Krachttraining") },
                        onClick = {
                            workoutType = WorkoutType.WEIGHT_TRAINING
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Yoga") },
                        onClick = {
                            workoutType = WorkoutType.YOGA
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("HIIT") },
                        onClick = {
                            workoutType = WorkoutType.HIIT
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Anders") },
                        onClick = {
                            workoutType = WorkoutType.OTHER
                            expanded = false
                        }
                    )
                }
            }
            
            // Date Picker
            OutlinedTextField(
                value = dateFormatter.format(date),
                onValueChange = { },
                label = { Text("Datum") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Kies datum")
                    }
                }
            )
            
            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = date.time
                )
                
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let {
                                    date = Date(it)
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDatePicker = false }
                        ) {
                            Text("Annuleren")
                        }
                    }
                )
            }
            
            // Duration
            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duur (minuten)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            // Distance (only for certain workout types)
            if (workoutType in listOf(WorkoutType.RUNNING, WorkoutType.CYCLING, WorkoutType.SWIMMING)) {
                OutlinedTextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text("Afstand (km)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
            
            // Calories
            OutlinedTextField(
                value = calories,
                onValueChange = { calories = it },
                label = { Text("Calorieën verbrand") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            // Feeling
            Text(
                text = "Hoe voelde je je?",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Slider(
                    value = feeling,
                    onValueChange = { feeling = it },
                    valueRange = 1f..5f,
                    steps = 3
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Zwaar", style = MaterialTheme.typography.bodySmall)
                    Text("Gemiddeld", style = MaterialTheme.typography.bodySmall)
                    Text("Geweldig", style = MaterialTheme.typography.bodySmall)
                }
            }
            
            // Notes
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notities") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    viewModel.addWorkout(
                        type = workoutType,
                        date = date,
                        duration = duration.toLongOrNull() ?: 0,
                        distance = distance.toFloatOrNull(),
                        caloriesBurned = calories.toIntOrNull(),
                        notes = notes,
                        feeling = feeling.toInt()
                    ) { success ->
                        if (success) {
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Workout opslaan")
            }
        }
    }
}
