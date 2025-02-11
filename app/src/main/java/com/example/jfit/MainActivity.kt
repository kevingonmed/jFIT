package com.example.jfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutApp()
        }
    }
}

@Composable
fun WorkoutApp() {
    val exercises = listOf("Push-ups", "Squats", "Lunges", "Plank", "Jumping Jacks")
    var currentExercise by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Daily Workout", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        exercises.forEach { exercise ->
            Text(
                text = exercise,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { currentExercise = exercise }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { currentExercise = exercises.random() }) {
            Text("Start Workout")
        }

        currentExercise?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Current Exercise: $it", style = MaterialTheme.typography.bodyLarge)
        }
    }
}