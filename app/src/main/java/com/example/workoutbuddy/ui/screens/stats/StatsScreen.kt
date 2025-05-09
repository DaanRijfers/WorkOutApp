package com.example.workoutbuddy.ui.screens.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.workoutbuddy.data.model.Workout
import com.example.workoutbuddy.data.model.WorkoutType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val weeklyWorkouts by viewModel.weeklyWorkouts.collectAsState(initial = emptyList())
    val monthlyWorkouts by viewModel.monthlyWorkouts.collectAsState(initial = emptyList())
    val totalWorkoutCount by viewModel.totalWorkoutCount.collectAsState(initial = 0)
    val totalWorkoutDuration by viewModel.totalWorkoutDuration.collectAsState(initial = 0L)
    val totalWorkoutDistance by viewModel.totalWorkoutDistance.collectAsState(initial = 0f)
    
    var selectedPeriod by remember { mutableStateOf(0) } // 0 = week, 1 = month
    var selectedMetric by remember { mutableStateOf(0) } // 0 = count, 1 = duration, 2 = distance
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistieken") },
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
            // Summary cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryCard(
                    title = "Totaal",
                    value = totalWorkoutCount.toString(),
                    subtitle = "workouts",
                    modifier = Modifier.weight(1f)
                )
                
                SummaryCard(
                    title = "Totale tijd",
                    value = formatDuration(totalWorkoutDuration),
                    subtitle = "uren",
                    modifier = Modifier.weight(1f)
                )
                
                SummaryCard(
                    title = "Afstand",
                    value = String.format("%.1f", totalWorkoutDistance),
                    subtitle = "km",
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Period selector
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = selectedPeriod == 0,
                    onClick = { selectedPeriod = 0 },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                ) {
                    Text("Week")
                }
                SegmentedButton(
                    selected = selectedPeriod == 1,
                    onClick = { selectedPeriod = 1 },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                ) {
                    Text("Maand")
                }
            }
            
            // Metric selector
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = selectedMetric == 0,
                    onClick = { selectedMetric = 0 },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3)
                ) {
                    Text("Aantal")
                }
                SegmentedButton(
                    selected = selectedMetric == 1,
                    onClick = { selectedMetric = 1 },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3)
                ) {
                    Text("Tijd")
                }
                SegmentedButton(
                    selected = selectedMetric == 2,
                    onClick = { selectedMetric = 2 },
                    shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3)
                ) {
                    Text("Afstand")
                }
            }
            
            // Chart
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val workouts = if (selectedPeriod == 0) weeklyWorkouts else monthlyWorkouts
                    
                    if (workouts.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Geen data beschikbaar",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Voeg workouts toe om statistieken te zien",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        when (selectedMetric) {
                            0 -> WorkoutCountChart(workouts, selectedPeriod)
                            1 -> WorkoutDurationChart(workouts, selectedPeriod)
                            2 -> WorkoutDistanceChart(workouts, selectedPeriod)
                        }
                    }
                }
            }
            
            // Workout type distribution
            Text(
                text = "Workout verdeling",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            val workoutsByType = (if (selectedPeriod == 0) weeklyWorkouts else monthlyWorkouts)
                .groupBy { it.type }
                .mapValues { it.value.size }
            
            if (workoutsByType.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Geen data beschikbaar",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        workoutsByType.forEach { (type, count) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height(16.dp)
                                        .padding(end = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Canvas(modifier = Modifier.fillMaxSize()) {
                                        drawCircle(
                                            color = getColorForWorkoutType(type),
                                            radius = size.minDimension / 2
                                        )
                                    }
                                }
                                
                                Spacer(modifier = Modifier.width(8.dp))
                                
                                Text(
                                    text = getWorkoutTypeName(type),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                
                                Spacer(modifier = Modifier.weight(1f))
                                
                                Text(
                                    text = count.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun WorkoutCountChart(workouts: List<Workout>, periodType: Int) {
    val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val data = if (periodType == 0) {
        // Weekly data
        val calendar = Calendar.getInstance()
        val dayNames = (0..6).map {
            calendar.set(Calendar.DAY_OF_WEEK, it + 1)
            dateFormat.format(calendar.time)
        }
        
        val counts = dayNames.map { dayName ->
            workouts.count { workout ->
                val cal = Calendar.getInstance()
                cal.time = workout.date
                dateFormat.format(cal.time) == dayName
            }
        }
        
        dayNames.zip(counts)
    } else {
        // Monthly data
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dayLabels = (1..daysInMonth step (daysInMonth / min(daysInMonth, 10))).map { it.toString() }
        
        val counts = dayLabels.map { day ->
            val dayNum = day.toInt()
            workouts.count { workout ->
                val cal = Calendar.getInstance()
                cal.time = workout.date
                cal.get(Calendar.DAY_OF_MONTH) == dayNum && cal.get(Calendar.MONTH) == currentMonth
            }
        }
        
        dayLabels.zip(counts)
    }
    
    LineChart(
        data = data,
        maxValue = data.maxOfOrNull { it.second }?.toFloat() ?: 1f,
        lineColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun WorkoutDurationChart(workouts: List<Workout>, periodType: Int) {
    val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val data = if (periodType == 0) {
        // Weekly data
        val calendar = Calendar.getInstance()
        val dayNames = (0..6).map {
            calendar.set(Calendar.DAY_OF_WEEK, it + 1)
            dateFormat.format(calendar.time)
        }
        
        val durations = dayNames.map { dayName ->
            workouts.filter { workout ->
                val cal = Calendar.getInstance()
                cal.time = workout.date
                dateFormat.format(cal.time) == dayName
            }.sumOf { it.duration }.toInt()
        }
        
        dayNames.zip(durations)
    } else {
        // Monthly data
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dayLabels = (1..daysInMonth step (daysInMonth / min(daysInMonth, 10))).map { it.toString() }
        
        val durations = dayLabels.map { day ->
            val dayNum = day.toInt()
            workouts.filter { workout ->
                val cal = Calendar.getInstance()
                cal.time = workout.date
                cal.get(Calendar.DAY_OF_MONTH) == dayNum && cal.get(Calendar.MONTH) == currentMonth
            }.sumOf { it.duration }.toInt()
        }
        
        dayLabels.zip(durations)
    }
    
    LineChart(
        data = data,
        maxValue = data.maxOfOrNull { it.second }?.toFloat() ?: 1f,
        lineColor = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun WorkoutDistanceChart(workouts: List<Workout>, periodType: Int) {
    val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val data = if (periodType == 0) {
        // Weekly data
        val calendar = Calendar.getInstance()
        val dayNames = (0..6).map {
            calendar.set(Calendar.DAY_OF_WEEK, it + 1)
            dateFormat.format(calendar.time)
        }
        
        val distances = dayNames.map { dayName ->
            workouts.filter { workout ->
                val cal = Calendar.getInstance()
                cal.time = workout.date
                dateFormat.format(cal.time) == dayName
            }.sumOf { (it.distance ?: 0f).toDouble() }.toInt()
        }
        
        dayNames.zip(distances)
    } else {
        // Monthly data
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dayLabels = (1..daysInMonth step (daysInMonth / min(daysInMonth, 10))).map { it.toString() }
        
        val distances = dayLabels.map { day ->
            val dayNum = day.toInt()
            workouts.filter { workout ->
                val cal = Calendar.getInstance()
                cal.time = workout.date
                cal.get(Calendar.DAY_OF_MONTH) == dayNum && cal.get(Calendar.MONTH) == currentMonth
            }.sumOf { (it.distance ?: 0f).toDouble() }.toInt()
        }
        
        dayLabels.zip(distances)
    }
    
    LineChart(
        data = data,
        maxValue = data.maxOfOrNull { it.second }?.toFloat() ?: 1f,
        lineColor = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun LineChart(
    data: List<Pair<String, Int>>,
    maxValue: Float,
    lineColor: Color
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val padding = 40f
            
            // Draw x-axis
            drawLine(
                color = Color.Gray,
                start = Offset(padding, height - padding),
                end = Offset(width - padding, height - padding),
                strokeWidth = 2f
            )
            
            // Draw y-axis
            drawLine(
                color = Color.Gray,
                start = Offset(padding, padding),
                end = Offset(padding, height - padding),
                strokeWidth = 2f
            )
            
            if (data.isNotEmpty() && maxValue > 0) {
                val xStep = (width - 2 * padding) / (data.size - 1)
                val yStep = (height - 2 * padding) / maxValue
                
                // Draw the line
                val path = Path()
                var firstPoint = true
                
                data.forEachIndexed { index, (_, value) ->
                    val x = padding + index * xStep
                    val y = height - padding - (value * yStep)
                    
                    if (firstPoint) {
                        path.moveTo(x, y)
                        firstPoint = false
                    } else {
                        path.lineTo(x, y)
                    }
                }
                
                drawPath(
                    path = path,
                    color = lineColor,
                    style = Stroke(width = 3f)
                )
                
                // Draw data points
                data.forEachIndexed { index, (_, value) ->
                    val x = padding + index * xStep
                    val y = height - padding - (value * yStep)
                    
                    drawCircle(
                        color = lineColor,
                        radius = 6f,
                        center = Offset(x, y)
                    )
                }
                
                // Draw x-axis labels
                data.forEachIndexed { index, (label, _) ->
                    val x = padding + index * xStep
                    drawContext.canvas.nativeCanvas.drawText(
                        label,
                        x,
                        height - padding + 20,
                        androidx.compose.ui.graphics.drawscope.DrawScope.DefaultFontSize.toPx(),
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.GRAY
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }
                
                // Draw y-axis labels
                val yLabels = 5
                for (i in 0..yLabels) {
                    val value = (maxValue / yLabels) * i
                    val y = height - padding - (value * yStep)
                    drawContext.canvas.nativeCanvas.drawText(
                        value.toInt().toString(),
                        padding - 20,
                        y + 5,
                        androidx.compose.ui.graphics.drawscope.DrawScope.DefaultFontSize.toPx(),
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.GRAY
                            textAlign = android.graphics.Paint.Align.RIGHT
                        }
                    )
                }
            }
        }
    }
}

fun getColorForWorkoutType(type: WorkoutType): Color {
    return when (type) {
        WorkoutType.RUNNING -> Color(0xFF4CAF50)
        WorkoutType.CYCLING -> Color(0xFF2196F3)
        WorkoutType.SWIMMING -> Color(0xFF03A9F4)
        WorkoutType.WEIGHT_TRAINING -> Color(0xFFFF9800)
        WorkoutType.YOGA -> Color(0xFF9C27B0)
        WorkoutType.HIIT -> Color(0xFFF44336)
        WorkoutType.OTHER -> Color(0xFF607D8B)
    }
}

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

fun formatDuration(minutes: Long): String {
    val hours = minutes / 60
    val mins = minutes % 60
    return if (hours > 0) {
        "$hours:${mins.toString().padStart(2, '0')}"
    } else {
        mins.toString()
    }
}
