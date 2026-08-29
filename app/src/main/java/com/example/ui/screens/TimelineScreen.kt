package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.data.local.WorkoutPlanEntity
import com.example.ui.components.StatusBadge
import com.example.ui.localization.Localization
import com.example.ui.theme.*
import com.example.viewmodel.MainViewModel

@Composable
fun TimelineScreen(
    viewModel: MainViewModel,
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit
) {
    val workoutPlans by viewModel.workoutPlans.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Top Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Localization.get("adaptive_timeline", isAmharic),
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDarkCoffee
            )
            Icon(Icons.Default.DateRange, contentDescription = "Calendar", tint = TerracottaPrimary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Week Calendar Bar (13 to 19 May)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val days = listOf("M" to "13", "T" to "14", "W" to "15", "T" to "16", "F" to "17", "S" to "18", "S" to "19")
            days.forEach { (dayLetter, dateNum) ->
                val isToday = dateNum == "16"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isToday) TerracottaPrimary else CardSurface)
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = dayLetter,
                        fontSize = 11.sp,
                        color = if (isToday) Color.White.copy(alpha = 0.8f) else TextMutedSand
                    )
                    Text(
                        text = dateNum,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isToday) Color.White else TextDarkCoffee
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // DAY DETAIL (ADAPTATION EXPLANATION CARD - SCREEN 4 FROM UI GUIDE)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("day_detail_adaptation_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Date & Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isAmharic) "ሐሙስ፣ ግንቦት 8" else "Thursday, 16 May",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkCoffee
                        )
                    }
                    StatusBadge(status = "Adjusted")
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Adjustment Banner
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = SoftCreamSurface
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = "Adjusted", tint = TerracottaPrimary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (isAmharic)
                                "የዛሬው ስልጠና ወደ 25 ደቂቃ ተቀንሷል። ሥራ በዝቶብዎት ስለነበር ስልጠናውን አስተካክለዋለሁ።"
                            else
                                "Workout reduced to 25 min. You had a busy day, so I adjusted your workout to fit your time.",
                            fontSize = 13.sp,
                            color = TextDarkCoffee,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = CardStroke)
                Spacer(modifier = Modifier.height(16.dp))

                // WHAT CHANGED
                Text(
                    text = Localization.get("what_changed", isAmharic),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TerracottaPrimary,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Original Plan", fontSize = 11.sp, color = TextMutedSand)
                        Text(text = "Upper Body", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                        Text(text = "45 min • 7 exercises", fontSize = 12.sp, color = TextMutedSand)
                    }

                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Arrow",
                        tint = TerracottaPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Adjusted Plan", fontSize = 11.sp, color = TerracottaPrimary)
                        Text(text = "Upper Body", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                        Text(text = "25 min • 4 exercises", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = CardStroke)
                Spacer(modifier = Modifier.height(16.dp))

                // WHY THIS CHANGE?
                Text(
                    text = Localization.get("why_this_change", isAmharic),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TerracottaPrimary,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                ReasonItem(text = if (isAmharic) "ብዙውን ጊዜ በሐሙስ ቀናት ያነሰ ጊዜ አለዎት" else "You usually have less time on Thursdays")
                ReasonItem(text = if (isAmharic) "የማገገም ሁኔታዎ ከተለመደው አነስተኛ ነበር" else "Your recovery was slightly below average")
                ReasonItem(text = if (isAmharic) "በዚህ ሳምንት 2 ዘግይተው ያደሩ ቀናት ነበሩዎት" else "You had 2 late nights this week")

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = CardStroke)
                Spacer(modifier = Modifier.height(16.dp))

                // IMPACT ON YOUR WEEK
                Text(
                    text = if (isAmharic) "በሳምንትዎ ላይ ያለው ተፅዕኖ" else "IMPACT ON YOUR WEEK",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TerracottaPrimary,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ImpactItem(title = "Calorie Goal", subtitle = "On Track")
                    ImpactItem(title = "Workout Streak", subtitle = "5 days")
                    ImpactItem(title = "Consistency", subtitle = "92%")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action button
                Button(
                    onClick = onOpenWorkoutSession,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("view_todays_workout_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Text(
                        text = if (isAmharic) "የዛሬውን ስልጠና ተመልከት" else "View Today's Workout",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TIMELINE LIST
        Text(
            text = if (isAmharic) "የሳምንቱ ዕቅድ ታሪክ" else "WEEK'S ADAPTIVE TIMELINE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMutedSand,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        workoutPlans.forEach { plan ->
            TimelineRowCard(plan = plan, isAmharic = isAmharic, onClick = onOpenWorkoutSession)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ReasonItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 3.dp)
    ) {
        Icon(
            Icons.Default.Schedule,
            contentDescription = null,
            tint = TextMutedSand,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 12.sp, color = TextDarkCoffee)
    }
}

@Composable
private fun ImpactItem(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, fontSize = 11.sp, color = TextMutedSand)
        Text(text = subtitle, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
    }
}

@Composable
private fun TimelineRowCard(
    plan: WorkoutPlanEntity,
    isAmharic: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = plan.dayOfWeek,
                    fontSize = 11.sp,
                    color = TextMutedSand
                )
                Text(
                    text = if (isAmharic) plan.amharicTitle else plan.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkCoffee
                )
                if (plan.durationMin > 0) {
                    Text(
                        text = "${plan.durationMin} min",
                        fontSize = 12.sp,
                        color = TextMutedSand
                    )
                }
            }

            StatusBadge(status = plan.status)
        }
    }
}
