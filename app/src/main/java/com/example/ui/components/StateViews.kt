package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

// -----------------------------------------------------------------------------
// LOADING STATE CARD (UI Board States)
// -----------------------------------------------------------------------------
@Composable
fun LoadingStateCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, CardStroke)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(14.dp)
                    .background(SoftCreamSurface, RoundedCornerShape(4.dp))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(20.dp)
                    .background(SoftCreamSurface, RoundedCornerShape(4.dp))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(14.dp)
                    .background(SoftCreamSurface, RoundedCornerShape(4.dp))
            )
        }
    }
}

// -----------------------------------------------------------------------------
// EMPTY STATE (UI Board States)
// -----------------------------------------------------------------------------
@Composable
fun EmptyStateCard(
    isAmharic: Boolean,
    onStartWorkout: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, CardStroke)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = SoftCreamSurface,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = TextMutedSand,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isAmharic) "እስካሁን ምንም የካታሎግ ስልጠና የለም" else "No workouts logged yet",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkCoffee
            )

            Text(
                text = if (isAmharic) "የመጀመሪያዎን ስልጠና አሁን ይጀምሩ" else "Start your first workout to log activity",
                fontSize = 12.sp,
                color = TextMutedSand
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartWorkout,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                modifier = Modifier.testTag("empty_state_start_workout_button")
            ) {
                Text(
                    text = if (isAmharic) "ስልጠና ጀምር" else "Start Workout",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------
// ERROR STATE (UI Board States)
// -----------------------------------------------------------------------------
@Composable
fun ErrorStateCard(
    isAmharic: Boolean,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, CardStroke)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFFDE8E8),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null,
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isAmharic) "ስህተት ተከስቷል" else "Something went wrong",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkCoffee
            )

            Text(
                text = if (isAmharic) "እባክዎን ደግመው ይሞክሩ" else "Please try again later.",
                fontSize = 12.sp,
                color = TextMutedSand
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRetry,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                modifier = Modifier.testTag("error_state_retry_button")
            ) {
                Text(
                    text = if (isAmharic) "ደግመህ ሞክር" else "Retry",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
