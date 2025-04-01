package com.example.jfit

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.*
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign

// Data class representing an exercise with a name, a static image, and two GIF URLs
data class Exercise(
    val name: String,
    val imageRes: Int,
    val gifUrl1: String,
    val gifUrl2: String
)

// Workout map using the Exercise data class for each muscle group
val workoutMap: Map<String, List<Exercise>> = mapOf(
    "BICEPS" to listOf(
        Exercise(
            name = "Barbell Preacher Curl",
            imageRes = R.drawable.barbell_preacher_curl,
            gifUrl1 = "https://www.inspireusafoundation.org/wp-content/uploads/2022/03/ez-bar-preacher-curl.gif",
            gifUrl2 = "https://www.garagegymreviews.com/wp-content/uploads/EZ-bar-preacher-curl.gif"
        ),
        Exercise(
            name = "Dumbbell Concentration Curl",
            imageRes = R.drawable.dumbbell_concentration_curl,
            gifUrl1 = "https://archive.prosto.academy/uploads/images/gallery/2023-07/54ccfHau7wxm9Ije-2-2-4.gif",
            gifUrl2 = "https://app.gogain.me/wp-content/uploads/2023/10/concentration_curls_dumbbell.gif"
        ),
        Exercise(
            name = "Inclined Dumbbell Curl",
            imageRes = R.drawable.inclined_dumbbell_curl,
            gifUrl1 = "https://framerusercontent.com/images/yUIzv64bZNcoNFLjwOGQU0tH4E.gif",
            gifUrl2 = "https://fitliferegime.com/wp-content/uploads/2023/08/How-To-Do-Spider-Curl.gif"
        ),
        Exercise(
            name = "Cable Biceps Curl",
            imageRes = R.drawable.cable_biceps_curl,
            gifUrl1 = "https://burnfit.io/wp-content/uploads/2023/11/CABLE_CURL.gif",
            gifUrl2 = "https://www.inspireusafoundation.org/wp-content/uploads/2022/12/cable-drag-curl.gif"
        )
    ),
    "LEGS" to listOf(
        Exercise(
            name = "Leg Press",
            imageRes = R.drawable.leg_press,
            gifUrl1 = "https://th.bing.com/th/id/OIP.-vhsPNmpUVJ93zUpYlYW-gHaHa?rs=1&pid=ImgDetMain",
            gifUrl2 = "https://media2.giphy.com/media/7D32x6UCJEkdxYyE0F/200.gif"
        ),
        Exercise(
            name = "Dumbbell Lunges",
            imageRes = R.drawable.dumbbell_lunges,
            gifUrl1 = "https://hips.hearstapps.com/hmg-prod/images/workouts/2016/03/dumbbelllunge-1457044372.gif?resize=1120:*",
            gifUrl2 = "https://liftmanual.com/wp-content/uploads/2023/04/dumbbell-walking-lunges.gif"
        ),
        Exercise(
            name = "Bulgarian Split Squat",
            imageRes = R.drawable.bulgarian_split_squat,
            gifUrl1 = "https://www.inspireusafoundation.org/wp-content/uploads/2023/08/deficit-bulgarian-split-squat.gif",
            gifUrl2 = "https://i.pinimg.com/originals/57/e3/8e/57e38edfbdd8d82192356411d2414c09.gif"
        ),
        Exercise(
            name = "Barbell Squat",
            imageRes = R.drawable.barbell_squat,
            gifUrl1 = "https://www.inspireusafoundation.org/wp-content/uploads/2022/10/negative-squat.gif",
            gifUrl2 = "https://th.bing.com/th/id/OIP.wMKX6YaTUj0eHNKVOl_SFgHaHa?rs=1&pid=ImgDetMain"
        )
    ),
    "CHEST" to listOf(
        Exercise(
            name = "Flat Bench Press",
            imageRes = R.drawable.flat_bench_press,
            gifUrl1 = "https://th.bing.com/th/id/R.8e34bb41d30ceb2f65aa7873a87a4371?rik=mO0G25RGfKf61w&pid=ImgRaw&r=0",
            gifUrl2 = "https://149874912.v2.pressablecdn.com/wp-content/uploads/2021/09/bench-press.gif"
        ),
        Exercise(
            name = "Incline Dumbbell Press",
            imageRes = R.drawable.incline_dumbbell_press,
            gifUrl1 = "https://th.bing.com/th/id/OIP.EDO7IAySGRy-BGpI5-kJCQHaHa?rs=1&pid=ImgDetMain",
            gifUrl2 = "https://burnfit.io/wp-content/uploads/2023/11/BB_INC_PRESS.gif"
        ),
        Exercise(
            name = "Chest Fly Machine",
            imageRes = R.drawable.chest_fly_machine,
            gifUrl1 = "https://th.bing.com/th/id/OIP.LFblfA1EyRz-sLWPuqAbiwHaHa?rs=1&pid=ImgDetMain",
            gifUrl2 = "https://i.makeagif.com/media/9-28-2015/-z-AFG.gif"
        ),
        Exercise(
            name = "Push Ups",
            imageRes = R.drawable.push_ups,
            gifUrl1 = "https://th.bing.com/th/id/OIP.7EIPMBoNcylXVcRebA5QSwHaE7?rs=1&pid=ImgDetMain",
            gifUrl2 = "https://th.bing.com/th/id/R.9b0fe771b589223c7a3cb002c5868c5f?rik=38ctSmQxs0IHvg&riu=http%3a%2f%2fwww.fuelrunning.com%2farticlecontent%2ffitness%2fhow-to-do-the-perfect-push-up%2fPerfectPushup.gif&ehk=kUSRx2OeHL9GNLk0K8%2bxqjwXZDjuqHPe5Jet224WHBU%3d&risl=&pid=ImgRaw&r=0"
        )
    ),
    "BACK" to listOf(
        Exercise(
            name = "Lat Pulldown",
            imageRes = R.drawable.lat_pulldown,
            gifUrl1 = "https://www.inspireusafoundation.org/wp-content/uploads/2023/11/supinated-lat-pulldown.gif",
            gifUrl2 = "https://fitnessvolt.com/wp-content/uploads/2023/09/wide-grip-lat-pulldown.gif"
        ),
        Exercise(
            name = "Deadlift",
            imageRes = R.drawable.deadlift,
            gifUrl1 = "https://th.bing.com/th/id/R.6c3bfe8213788083161dfe66db06947c?rik=bc%2b6JjEJnpoLoA&pid=ImgRaw&r=0",
            gifUrl2 = "https://cdn.shopify.com/s/files/1/0449/8453/3153/files/barbell-deadlift_600x600.gif?v=1690860568"
        ),
        Exercise(
            name = "Seated Cable Row",
            imageRes = R.drawable.seated_cable_row,
            gifUrl1 = "https://th.bing.com/th/id/OIP.rWdSZTQ3PZLF-DDNOeKLtgHaHa?rs=1&pid=ImgDetMain",
            gifUrl2 = "https://th.bing.com/th/id/OIP.8aO_KirHF24pbBjBVOxVpAAAAA?rs=1&pid=ImgDetMain"
        ),
        Exercise(
            name = "Pull Ups",
            imageRes = R.drawable.pull_ups,
            gifUrl1 = "https://fitliferegime.com/wp-content/uploads/2024/03/Archer-Pull-Up.gif",
            gifUrl2 = "https://media0.giphy.com/media/dInGm95ZDWWjelaHRp/200w.gif?cid=82a1493bu24q6qtf3r37a7fuwarxrl7ci4w5bp43wq1mcc90&ep=v1_gifs_related&rid=200w.gif&ct=g"
        )
    ),
    "SHOULDERS" to listOf(
        Exercise(
            name = "Overhead Press",
            imageRes = R.drawable.overhead_press,
            gifUrl1 = "https://th.bing.com/th/id/R.81f1d1557442ad58e9be2bb7cb207161?rik=bZwG1MOXTAhEVA&pid=ImgRaw&r=0",
            gifUrl2 = "https://th.bing.com/th/id/OIP.QVOdww5MgDw6Uz_j-Cte1AHaHa?rs=1&pid=ImgDetMain"
        ),
        Exercise(
            name = "Lateral Raise",
            imageRes = R.drawable.lateral_raise,
            gifUrl1 = "https://th.bing.com/th/id/OIP.6iZin3w2TnX2JtfIoSsg6AHaHa?rs=1&pid=ImgDetMain",
            gifUrl2 = "https://cdn.jefit.com/assets/img/exercises/gifs/1140.gif"
        ),
        Exercise(
            name = "Front Raise",
            imageRes = R.drawable.front_raise,
            gifUrl1 = "https://livelifehealthily.com/wp-content/uploads/2023/05/plate-front-raise.gif",
            gifUrl2 = "https://www.spotebi.com/wp-content/uploads/2014/10/dumbbell-front-raise-exercise-illustration.gif"
        ),
        Exercise(
            name = "Reverse Pec Deck",
            imageRes = R.drawable.reverse_pec_deck,
            gifUrl1 = "https://cdn.jefit.com/assets/img/exercises/gifs/1047.gif",
            gifUrl2 = "https://i.pinimg.com/originals/f1/fd/36/f1fd36181f97d4938e3fb4cc501bde53.gif"
        )
    ),
    "TRICEPS" to listOf(
        Exercise(
            name = "Tricep Pushdown",
            imageRes = R.drawable.tricep_pushdown,
            gifUrl1 = "https://th.bing.com/th/id/OIP.6jfOU-Krv2xlyE45KRMyfAHaHa?rs=1&pid=ImgDetMain",
            gifUrl2 = "https://www.inspireusafoundation.org/wp-content/uploads/2022/03/cable-tricep-overhead-extensions.gif"
        ),
        Exercise(
            name = "Skull Crushers",
            imageRes = R.drawable.skull_crushers,
            gifUrl1 = "https://www.verywellfit.com/thmb/_nPcYa4MjZ-Vcg1iqFS2bSRiEL0=/1100x0/filters:no_upscale():max_bytes(150000):strip_icc()/94-3498313--Lying-triceps-extenGIF-0d0e624392d74f9897a698fbe5c4ea3b.gif",
            gifUrl2 = "https://fitliferegime.com/wp-content/uploads/2023/09/Incline-Barbell-Skull-Crusher.gif"
        ),
        Exercise(
            name = "Overhead Tricep Extension",
            imageRes = R.drawable.overhead_tricep_extension,
            gifUrl1 = "https://legionathletics.com/wp-content/uploads/2022/08/Single-Arm-Overhead-Triceps-Extension-1.jpg",
            gifUrl2 = "https://th.bing.com/th/id/OIP.0GuvZOCR3_uiDJw4XsaiawAAAA?rs=1&pid=ImgDetMain"
        ),
        Exercise(
            name = "Bench Dips",
            imageRes = R.drawable.bench_dips,
            gifUrl1 = "https://cdn.shopify.com/s/files/1/0449/8453/3153/files/Bench_Dips.gif?v=1722411995",
            gifUrl2 = "https://fitliferegime.com/wp-content/uploads/2023/06/Tricep-Bench-Dip.gif"
        )
    )
)


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
                // Optional workoutName parameter with a default value
                composable(
                    route = "video_screen?workoutName={workoutName}",
                    arguments = listOf(
                        navArgument("workoutName") {
                            type = NavType.StringType
                            defaultValue = "DefaultWorkout"
                            nullable = true
                        }
                    )
                ) { backStackEntry ->
                    val workoutName = backStackEntry.arguments?.getString("workoutName")
                    VideoScreen(navController = navController, workoutName = workoutName)
                }
            }
        }
    }
}

@Composable
fun WorkoutApp(navController: NavHostController) {
    // List of muscle groups to choose from
    val muscleGroups = listOf("BICEPS", "LEGS", "CHEST", "BACK", "SHOULDERS", "TRICEPS")
    var currentExercise by rememberSaveable { mutableStateOf<String?>(null) }

    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { navController.navigate("video_screen") },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text("Go to Video Screen", color = Color.White)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Column containing the title and muscle groups
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
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // For each muscle group, navigate to its ExerciseScreen
            muscleGroups.forEach { muscle ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("exercise_screen/$muscle")
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.Cyan.copy(alpha = 0.35f))
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = muscle,
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
                onClick = { currentExercise = muscleGroups.random() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "Start Workout",
                    color = Color.White,
                    fontFamily = FontFamily.Serif
                )
            }

            currentExercise?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Current Exercise: $it",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ExerciseScreen(navController: NavHostController, exerciseName: String?) {
    // Retrieve the list of exercises for the selected muscle group
    val exercises = workoutMap[exerciseName?.uppercase()] ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = exerciseName ?: "Workout",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List each exercise for the muscle group
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(exercises) { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Navigate to VideoScreen passing the exercise name
                            navController.navigate("video_screen?workoutName=${exercise.name}")
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.8f))
                ) {
                    Column {
                        // Show unique static image for the exercise
                        Image(
                            painter = painterResource(id = exercise.imageRes),
                            contentDescription = exercise.name,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        // Exercise name
                        Text(
                            text = exercise.name,
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

@Composable
fun VideoScreen(navController: NavHostController, workoutName: String?) {
    // Find the exercise matching the name
    val selectedExercise = workoutMap.values.flatten().find { it.name == workoutName }
    var isExerciseDone by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Display Exercise Name at the top
            Text(
                text = selectedExercise?.name ?: "Exercise",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            // Display the two GIFs side by side
            if (selectedExercise != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AsyncImage(
                        model = selectedExercise.gifUrl1,
                        contentDescription = "GIF 1",
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop
                    )

                    AsyncImage(
                        model = selectedExercise.gifUrl2,
                        contentDescription = "GIF 2",
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Displays static image of the exercise
                Image(
                    painter = painterResource(id = selectedExercise.imageRes),
                    contentDescription = "Exercise Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Exercise Instructions
                Text(
                    text = "How to do ${selectedExercise.name}:\n\n" +
                            "1. Get into position.\n" +
                            "2. Execute the movement with control.\n" +
                            "3. Focus on muscle contraction.\n" +
                            "4. Breathe steadily.",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                Text(
                    text = "Exercise not found.",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to mark exercise as Completed Button
            Button(
                onClick = { isExerciseDone = !isExerciseDone },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExerciseDone) Color.Green else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = if (isExerciseDone) "Exercise Done ✅" else "Mark as Completed",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //  Return Button
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Return", color = Color.White)
            }
        }
    }
}


@Preview(showBackground = true, name = "Preview VideoScreen")
@Composable
fun PreviewVideoScreen() {
    val navController = rememberNavController()
    VideoScreen(navController = navController, workoutName = "Barbell Preacher Curl")
}
