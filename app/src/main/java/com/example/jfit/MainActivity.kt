package com.example.jfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import coil.compose.AsyncImage
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.border
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.jfit.ui.theme.PurpleGrey40

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
                    ExerciseScreen(navController = navController, exerciseName = exerciseName)
                }
                composable("video_screen") {
                    VideoScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun WorkoutApp(navController: NavHostController) {
    val exercises = listOf("BICEPS", "LEGS", "CHEST", "BACK", "SHOULDERS", "TRICEPS")

    var currentExercise by rememberSaveable { mutableStateOf<String?>(null) }

    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { navController.navigate("video_screen") },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text("Go to Video Screen", color = Color.White)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
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
                fontWeight = FontWeight.ExtraBold
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

// Second Screen
@Composable
fun ExerciseScreen(navController: NavHostController, exerciseName: String?) {
    val workouts = listOf(
        "Barbell Preacher Curl" to R.drawable.barbell_preacher_curl,
        "Dumbbell Concentration Curl" to R.drawable.dumbbell_concentration_curl,
        "Inclined Dumbbell Curl" to R.drawable.inclined_dumbbell_curl,
        "Cable Biceps Curl" to R.drawable.cable_biceps_curl
    )

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = exerciseName ?: "Workout",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {  // Adjusted for button spacing
            items(workouts) { (workoutName, imageRes) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("video_screen")
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
                            color = Color.White,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Return", color = Color.White)
        }
    }
}


// Third Screen
@Composable
fun VideoScreen(navController: NavHostController) {
    val context = LocalContext.current
    var isExerciseDone by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Contenedor para los dos GIFs (placeholders)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Placeholder para el primer GIF
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("GIF 1", color = Color.White)
                }

                // Placeholder para el segundo GIF
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("GIF 2", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Placeholder para la imagen del músculo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Image Muscle", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto
            Text(
                text = "How to do the Exercise:\n\n" +
                        "1. Place your hands on the bar.\n" +
                        "2. Lift the weight slowly.\n" +
                        "3. Lower the weight in a controlled manner.\n" +
                        "4. Repeat the movement.",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para marcar el ejercicio como hecho
            Button(
                onClick = { isExerciseDone = !isExerciseDone },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExerciseDone) Color.Green else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = if (isExerciseDone) "Ejercicio Hecho ✅" else "Marcar como Hecho",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.height(16.dp))

            // Botón para regresar
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Return", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true, name = "Preview de VideoScreen")
@Composable
fun PreviewVideoScreen() {
    val navController = rememberNavController()
    VideoScreen(navController = navController)
}
