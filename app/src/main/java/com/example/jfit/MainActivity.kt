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

    // Used rememberSaveable instead of remember as it is deprecated in material3
    var currentExercise by rememberSaveable { mutableStateOf<String?>(null) }

    // Background Image
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image2), // Use your image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier.fillMaxSize().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        { Text(
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
                        .clickable { currentExercise = exercise },
                    colors = CardDefaults.cardColors(containerColor = Color.Cyan.copy(alpha = 0.35f))


                ) {  // Added box and Alignment.Center to center the text inside the boxes
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
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
                Text("Current Exercise: $it", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


