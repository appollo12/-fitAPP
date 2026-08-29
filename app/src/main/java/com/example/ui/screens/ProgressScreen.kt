package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.example.ui.localization.Localization
import com.example.ui.theme.*
import com.example.viewmodel.MainViewModel

@Composable
fun ProgressScreen(
    viewModel: MainViewModel,
    isAmharic: Boolean,
    onBack: (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()
    var selectedPeriod by remember { mutableIntStateOf(1) } // 0: Week, 1: Month, 2: 3 Months, 3: Year

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onBack != null) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDarkCoffee)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = if (isAmharic) "የእድገት ማጠቃለያ" else "Progress & Trends",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDarkCoffee
                )
            }
            Icon(Icons.Default.TrendingUp, contentDescription = "Progress", tint = TerracottaPrimary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time Horizon Filter Segmented Control
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            val periods = listOf(
                if (isAmharic) "ሳምንት" else "Week",
                if (isAmharic) "ወር" else "Month",
                if (isAmharic) "3 ወራት" else "3 Months",
                if (isAmharic) "ዓመት" else "Year"
            )
            periods.forEachIndexed { index, periodLabel ->
                SegmentedButton(
                    selected = selectedPeriod == index,
                    onClick = { selectedPeriod = index },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = periods.size),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = TerracottaPrimary,
                        activeContentColor = Color.White,
                        inactiveContainerColor = CardSurface,
                        inactiveContentColor = TextMutedSand
                    )
                ) {
                    Text(text = periodLabel, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // OVERVIEW STATS (Screen 9 from reference image)
        Text(
            text = "OVERVIEW",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMutedSand,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProgressMetricCard(
                title = if (isAmharic) "ስልጠናዎች" else "Workouts",
                value = "18",
                subtext = "+4 this month",
                modifier = Modifier.weight(1f)
            )
            ProgressMetricCard(
                title = if (isAmharic) "ካሎሪ" else "Calories",
                value = "85%",
                subtext = "of goal",
                modifier = Modifier.weight(1f)
            )
            ProgressMetricCard(
                title = if (isAmharic) "እርምጃዎች" else "Steps",
                value = "212K",
                subtext = "+10%",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // WORKOUTS COMPLETED TRENDS BAR CHART
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("progress_workouts_chart_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = if (isAmharic) "የተጠናቀቁ ስልጠናዎች (ስብስብ)" else "Workouts Completed",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkCoffee
                )
                Text(
                    text = if (isAmharic) "ሳምንታዊ ወጥነት" else "Weekly Consistency Record",
                    fontSize = 12.sp,
                    color = TextMutedSand
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Bar Chart Visual Simulation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val bars = listOf(
                        "5 May" to 0.4f,
                        "12 May" to 0.75f,
                        "19 May" to 0.6f,
                        "26 May" to 0.9f
                    )
                    bars.forEach { (weekLabel, heightRatio) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(28.dp)
                                    .fillMaxHeight(heightRatio)
                                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                    .background(TerracottaPrimary)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = weekLabel, fontSize = 10.sp, color = TextMutedSand)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // PERFORMANCE & STRENGTH PROGRESSION
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("progress_strength_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isAmharic) "የጥንካሬ እድገት (Strength)" else "Strength Performance",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkCoffee
                        )
                        Text(
                            text = if (isAmharic) "የክብደት እና የደግሞሽ እድገት" else "Progressive Overload",
                            fontSize = 12.sp,
                            color = TextMutedSand
                        )
                    }
                    Text(
                        text = "+18%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = StatusCompletedText
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = { 0.82f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape),
                    color = TerracottaPrimary,
                    trackColor = SoftCreamSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Chest Press: +5kg", fontSize = 12.sp, color = TextMutedSand)
                    Text(text = "Squat: +10kg", fontSize = 12.sp, color = TextMutedSand)
                    Text(text = "Pull Ups: +3 reps", fontSize = 12.sp, color = TextMutedSand)
                }
            }
        }
    }
}

@Composable
private fun ProgressMetricCard(
    title: String,
    value: String,
    subtext: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = title, fontSize = 11.sp, color = TextMutedSand)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextDarkCoffee)
            Spacer(modifier = Modifier.height(2.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = StatusCompletedBg
            ) {
                Text(
                    text = subtext,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = StatusCompletedText,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}
