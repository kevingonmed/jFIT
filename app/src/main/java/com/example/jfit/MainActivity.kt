package com.example.jfit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home_screen") {
                composable("home_screen") {
                    WorkoutApp(navController = navController)
                }
                composable("exercise_screen/{exerciseName}") { backStackEntry ->
                    val exerciseName = backStackEntry.arguments?.getString("exerciseName")
                    ExerciseScreen(exerciseName = exerciseName)
                }
            }
        }
    }
}

@Composable
fun WorkoutApp(navController: NavHostController) {
    val exercises = listOf("BICEPS", "LEGS", "CHEST", "BACK", "SHOULDERS", "TRICEPS")
    var currentExercise by rememberSaveable { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Daily Workout",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(30.dp))

            exercises.forEach { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("exercise_screen/$exercise")
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.Cyan.copy(alpha = 0.35f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = exercise,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { currentExercise = exercises.random() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Text("Start Workout", color = Color.White)
            }

            currentExercise?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Current Exercise: $it", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }
        }
    }
}

// Second Screen - Exercise Details (Clicking a workout opens YouTube)
@Composable
fun ExerciseScreen(exerciseName: String?) {
    val context = LocalContext.current

    val workouts = listOf(
        "Barbell Preacher Curl" to Pair(R.drawable.barbell_preacher_curl, "https://www.youtube.com/watch?v=YOUR_VIDEO_ID"),
        "Dumbbell Concentration Curl" to Pair(R.drawable.dumbbell_concentration_curl, "https://www.youtube.com/watch?v=YOUR_VIDEO_ID2"),
        "Inclined Dumbbell Curl" to Pair(R.drawable.inclined_dumbbell_curl, "https://www.youtube.com/watch?v=YOUR_VIDEO_ID3"),
        "Cable Biceps Curl" to Pair(R.drawable.cable_biceps_curl, "https://www.youtube.com/watch?v=YOUR_VIDEO_ID4")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = exerciseName ?: "Workout", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        workouts.forEach { (workoutName, data) ->
            val (imageRes, videoUrl) = data

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                        context.startActivity(intent) // Open YouTube video when clicked
                    },
                colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.8f))
            ) {
                Column {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = workoutName,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )

                    Text(
                        text = workoutName,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}
