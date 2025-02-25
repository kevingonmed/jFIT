package com.example.jfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.* // Import for navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
// New import fro video and third screen navigation
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.border
import android.content.Intent
import android.net.Uri


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Setup NavHost here to handle the navigation between screens
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home_screen") {
                composable("home_screen") {
                    WorkoutApp(navController = navController)
                }
                composable("exercise_screen/{exerciseName}") { backStackEntry ->
                    val exerciseName = backStackEntry.arguments?.getString("exerciseName")
                    ExerciseScreen(navController = navController, exerciseName = exerciseName)
                }
                composable("video_screen") {
                    VideoScreen()
                }
            }
        }
    }
}

@Composable
fun WorkoutApp(navController: NavHostController) {
    // List to modify the list of exercise
    val exercises = listOf("BICEPS", "LEGS", "CHEST", "BACK", "SHOULDERS", "TRICEPS")

    // Used rememberSaveable instead of remember as it is deprecated in material3
    var currentExercise by rememberSaveable { mutableStateOf<String?>(null) }
    //Third screen this button will allow to navigate to my video screen
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { navController.navigate("video_screen") },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text("Go to Video Screen", color = Color.White)
    }

    // Background Image
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background), // Use your image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Daily Workout",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Used card for boxes container
            exercises.forEach { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Navigate to the exercise screen when clicked
                            navController.navigate("exercise_screen/$exercise")
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.Cyan.copy(alpha = 0.35f))
                ) {
                    // Added box and Alignment.Center to center the text inside the boxes
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.dp),
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
            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = { currentExercise = exercises.random() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Text(
                    "Start Workout",
                    color = Color.White,
                    fontFamily = FontFamily.Serif
                )
            }

            currentExercise?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Current Exercise: $it", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }
        }
    }
}

//Second Screen
@Composable
fun ExerciseScreen(navController: NavHostController, exerciseName: String?) {
   val workouts = listOf(
       "Barbell Preacher Curl" to R.drawable.barbell_preacher_curl,
       "Dumbbell Concentration Curl" to R.drawable.dumbbell_concentration_curl,
       "Inclined Dumbbell Curl" to R.drawable.inclined_dumbbell_curl,
       "Cable Biceps Curl" to R.drawable.cable_biceps_curl
   )
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Text(text = exerciseName ?: "Workout", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        workouts.forEach { (workoutName, imageRes) ->
            Card (modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate("workout_detail/$workoutName")},
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



//Third Screen
@Composable
fun VideoScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Upper half: square placeholder for the video.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(width = 2.dp, color = Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text("Video Placeholder", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Middle: clickable text link that opens a YouTube video.
        Text(
            text = "Watch video on YouTube",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
            modifier = Modifier.clickable {
                val youtubeUrl = "https://www.youtube.com/watch?v=YOUR_VIDEO_ID" // Replace with your actual URL.
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                context.startActivity(intent)
            }
        )
    }
}

