package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.ExerciseEntity
import com.example.ui.theme.*

@Composable
fun WorkoutSessionDialog(
    exercises: List<ExerciseEntity>,
    isAmharic: Boolean,
    onDismiss: () -> Unit,
    onToggleExercise: (Int, Boolean) -> Unit
) {
    var restTimeSeconds by remember { mutableIntStateOf(45) }
    var isTimerRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isTimerRunning, restTimeSeconds) {
        if (isTimerRunning && restTimeSeconds > 0) {
            kotlinx.coroutines.delay(1000)
            restTimeSeconds--
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            color = CreamBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isAmharic) "የሰውነት በላይኛው ክፍል ስልጠና" else "Upper Body Session",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkCoffee
                        )
                        Text(
                            text = if (isAmharic) "25 ደቂቃ • 3 / 7 እንቅስቃሴዎች" else "25 min • 3 / 7 exercises",
                            fontSize = 13.sp,
                            color = TextMutedSand
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_workout_session")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextDarkCoffee)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Exercise List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(exercises) { exercise ->
                        ExerciseItemCard(
                            exercise = exercise,
                            isAmharic = isAmharic,
                            onToggle = { onToggleExercise(exercise.id, exercise.isCompleted) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Rest Timer Banner matching screen 5 from UI guide
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isAmharic) "የእረፍት ጊዜ ቆጣሪ" else "Rest Timer",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMutedSand
                            )
                            val formattedTime = String.format("%02d:%02d", restTimeSeconds / 60, restTimeSeconds % 60)
                            Text(
                                text = formattedTime,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextDarkCoffee
                            )
                        }

                        IconButton(
                            onClick = { isTimerRunning = !isTimerRunning },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(SoftCreamSurface)
                        ) {
                            Icon(
                                imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Pause Rest Timer",
                                tint = TerracottaPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Button
                Button(
                    onClick = {
                        val firstIncomplete = exercises.firstOrNull { !it.isCompleted }
                        if (firstIncomplete != null) {
                            onToggleExercise(firstIncomplete.id, false)
                        } else {
                            onDismiss()
                        }
                        restTimeSeconds = 45
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("complete_set_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Text(
                        text = if (isAmharic) "ስብስብ አጠናቅቅ (Complete Set)" else "Complete Set",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseItemCard(
    exercise: ExerciseEntity,
    isAmharic: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (exercise.isCompleted) SoftCreamSurface else CardSurface
        ),
        border = if (exercise.isCompleted) null else androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (exercise.isCompleted) StatusCompletedBg else TerracottaContainer)
                ) {
                    if (exercise.isCompleted) {
                        Icon(Icons.Default.Check, contentDescription = "Done", tint = StatusCompletedText)
                    } else {
                        Text(
                            text = "${exercise.sets}x${exercise.reps}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TerracottaPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = if (isAmharic) exercise.amharicName else exercise.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextDarkCoffee
                    )
                    Text(
                        text = "${exercise.sets} sets • ${exercise.reps} reps (${exercise.targetMuscle})",
                        fontSize = 12.sp,
                        color = TextMutedSand
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (exercise.isCompleted) StatusCompletedText else Color.Transparent)
            ) {
                if (exercise.isCompleted) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}
