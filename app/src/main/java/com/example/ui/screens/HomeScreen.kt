package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.localization.Localization
import com.example.ui.theme.*
import com.example.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit,
    onOpenPhysiqueCheck: () -> Unit,
    onOpenBudgetCalculator: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onOpenOnboarding: () -> Unit
) {
    val scrollState = rememberScrollState()
    val layoutVariant by viewModel.layoutVariant.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = Localization.get("good_morning", isAmharic),
                    fontSize = 13.sp,
                    color = TextMutedSand
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Abel",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDarkCoffee
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "👑", fontSize = 18.sp)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onOpenOnboarding,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .background(CardSurface)
                        .testTag("onboarding_setup_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Setup & Adaptability",
                        tint = TerracottaPrimary
                    )
                }

                IconButton(
                    onClick = { viewModel.toggleLanguage() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(CardSurface)
                        .testTag("language_toggle_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Language Toggle",
                        tint = TerracottaPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // EDITORIAL HOMESCREEN CONTENT
        EditorialLayoutContent(
            isAmharic = isAmharic,
            onOpenWorkoutSession = onOpenWorkoutSession,
            onOpenPhysiqueCheck = onOpenPhysiqueCheck,
            onOpenBudgetCalculator = onOpenBudgetCalculator,
            onNavigateToTimeline = onNavigateToTimeline,
            onNavigateToProgress = onNavigateToProgress
        )
    }
}

// --------------------------------------------------
// EDITORIAL STANDARD LAYOUT
// --------------------------------------------------
@Composable
private fun EditorialLayoutContent(
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit,
    onOpenPhysiqueCheck: () -> Unit,
    onOpenBudgetCalculator: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToProgress: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // HERO CARD: TODAY'S MISSION (Upper Body)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("hero_todays_mission_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = TextDarkCoffee),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = Localization.get("todays_mission", isAmharic),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TerracottaLight,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (isAmharic) "የሰውነት በላይኛው ክፍል" else "Upper Body",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = if (isAmharic) "35 ደቂቃ • 3 / 7 እንቅስቃሴዎች" else "35 min • 3 / 7 exercises",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onOpenWorkoutSession,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                        modifier = Modifier
                            .testTag("start_workout_button")
                            .height(44.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = Localization.get("start_workout", isAmharic),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Stylized Vector Graphic Icon Badge
                Surface(
                    shape = CircleShape,
                    color = TerracottaPrimary.copy(alpha = 0.2f),
                    modifier = Modifier.size(76.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = "Workout Icon",
                            tint = TerracottaLight,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // DAY PROGRESS RINGS / METRICS
        Text(
            text = Localization.get("day_progress", isAmharic),
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
            ProgressCardItem(
                title = Localization.get("workout", isAmharic),
                value = "3 / 7",
                progress = 0.42f,
                modifier = Modifier.weight(1f)
            )
            ProgressCardItem(
                title = Localization.get("nutrition", isAmharic),
                value = "1,420 / 2K",
                progress = 0.71f,
                modifier = Modifier.weight(1f)
            )
            ProgressCardItem(
                title = Localization.get("steps", isAmharic),
                value = "6,842 / 10K",
                progress = 0.68f,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // AI COACH INSIGHT CARD
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToTimeline() }
                .testTag("ai_coach_insight_card"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SoftCreamSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = Localization.get("ai_coach_insight", isAmharic),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TerracottaPrimary,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (isAmharic)
                        "በዚህ ሳምንት ውጤታማ ነበሩ። የዛሬውን ስልጠና በእርስዎ የጊዜ ሰሌዳ መሰረት አስተካክለዋለሁ።"
                    else
                        "You've been consistent this week. I've adjusted today's workout based on your schedule.",
                    fontSize = 14.sp,
                    color = TextDarkCoffee,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = Localization.get("view_details", isAmharic),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TerracottaPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = TerracottaPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // QUICK ACTIONS GRID
        Text(
            text = Localization.get("quick_actions", isAmharic),
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
            QuickActionButton(
                label = Localization.get("physique_check", isAmharic),
                icon = Icons.Default.AutoAwesome,
                onClick = onOpenPhysiqueCheck,
                isHighlight = true,
                modifier = Modifier.weight(1f)
            )
            QuickActionButton(
                label = if (isAmharic) "በጀት አሰላ" else "100 ETB Meals",
                icon = Icons.Default.AttachMoney,
                onClick = onOpenBudgetCalculator,
                isHighlight = false,
                modifier = Modifier.weight(1f)
            )
            QuickActionButton(
                label = if (isAmharic) "እድገት" else "Progress",
                icon = Icons.Default.TrendingUp,
                onClick = onNavigateToProgress,
                isHighlight = false,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// --------------------------------------------------
// VARIATION 2: BENTO GRID MODULAR LAYOUT
// --------------------------------------------------
@Composable
private fun BentoGridLayoutContent(
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit,
    onOpenPhysiqueCheck: () -> Unit,
    onOpenBudgetCalculator: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToProgress: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "ቤንቶ ግሪድ (BENTO GRID LAYOUT)" else "MODULAR BENTO DASHBOARD",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TerracottaPrimary,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        // BENTO TILE 1: Main Mission Banner with Radial Progress
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = TextDarkCoffee)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = TerracottaPrimary
                    ) {
                        Text(
                            text = if (isAmharic) "የዛሬ ግብ" else "TODAY'S WORKOUT",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Upper Body Sculpt",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "35 Mins • Dumbbells & Bench",
                        fontSize = 12.sp,
                        color = TextMutedSand
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onOpenWorkoutSession,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text(text = "Start Session ▶", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Radial Progress Percentage Box
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f))
                ) {
                    CircularProgressIndicator(
                        progress = { 0.42f },
                        modifier = Modifier.fillMaxSize(),
                        color = TerracottaPrimary,
                        strokeWidth = 6.dp,
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "42%", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        Text(text = "Done", fontSize = 9.sp, color = TextMutedSand)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // BENTO TILE 2 & 3: Side-by-Side Asymmetric Widgets
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Bento Tile 2: AI Coach Quick Tag
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToTimeline() },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = TerracottaContainer),
                border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaPrimary)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "AI Adapt Signal", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                    Text(
                        text = "Thursday volume condensed by 15%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkCoffee,
                        lineHeight = 16.sp
                    )
                }
            }

            // Bento Tile 3: Ethiopian Nutrition Quick Suggestion
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOpenBudgetCalculator() },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SoftCreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Icon(Icons.Default.Restaurant, contentDescription = null, tint = TextDarkCoffee, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "100 ETB Meal", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMutedSand)
                    Text(
                        text = "Shiro Wot + 2 Boiled Eggs (32g P)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkCoffee,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // BENTO TILE 4: 7-Day Consistency Heat Map Graph
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "ADAPTIVE CONSISTENCY (7 DAYS)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextMutedSand)
                    Text(text = "5 / 7 Days", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                }

                Spacer(modifier = Modifier.height(12.dp))

                val days = listOf("M", "T", "W", "T", "F", "S", "S")
                val status = listOf(true, true, true, false, true, true, false)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    days.forEachIndexed { idx, day ->
                        val isDone = status[idx]
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isDone) TerracottaPrimary else SoftCreamSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDone) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                } else {
                                    Text(text = "-", color = TextMutedSand)
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = day, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                        }
                    }
                }
            }
        }
    }
}

// --------------------------------------------------
// VARIATION 3: MINIMALIST CARDLESS LAYOUT
// --------------------------------------------------
@Composable
private fun MinimalLayoutContent(
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit,
    onOpenPhysiqueCheck: () -> Unit,
    onOpenBudgetCalculator: () -> Unit,
    onNavigateToProgress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = if (isAmharic) "ቀላል እና ንፁህ እይታ (MINIMAL LAYOUT)" else "EDITORIAL MINIMAL VIEW",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMutedSand,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Display Heading
        Text(
            text = "Today's Upper Body Protocol",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDarkCoffee,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Inline Stat Metrics Bar
        Text(
            text = "3 / 7 Movements Completed  •  1,420 kcal  •  6,842 steps",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TerracottaPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = CardStroke, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // Clean Frameless Exercise Checklist Items
        val minimalExercises = listOf(
            "1. Dumbbell Incline Press" to "3 sets x 10 reps (24 kg)",
            "2. Bodyweight Push-ups" to "3 sets x 15 reps (Pause at bottom)",
            "3. Dumbbell Lateral Raises" to "4 sets x 12 reps (8 kg)",
            "4. Standing Overhead Press" to "3 sets x 8 reps (Pending)"
        )

        minimalExercises.forEachIndexed { idx, (title, sub) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (idx < 3) TerracottaPrimary else TextMutedSand)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                    Text(text = sub, fontSize = 12.sp, color = TextMutedSand)
                }
                if (idx < 3) {
                    Text(text = "DONE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                }
            }
            Divider(color = CardStroke.copy(alpha = 0.5f), thickness = 0.5.dp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onOpenWorkoutSession,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TextDarkCoffee)
        ) {
            Text(text = "Continue Protocol Session →", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

// --------------------------------------------------
// VARIATION 4: FOCUS HERO EXECUTION LAYOUT
// --------------------------------------------------
@Composable
private fun FocusLayoutContent(
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit,
    onOpenPhysiqueCheck: () -> Unit,
    onNavigateToTimeline: () -> Unit
) {
    var check1 by remember { mutableStateOf(true) }
    var check2 by remember { mutableStateOf(true) }
    var check3 by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "የነቃ እንቅስቃሴ እይታ (ACTION FOCUS LAYOUT)" else "ACTION EXECUTION FOCUS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TerracottaPrimary,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Massive Timer / Execution Hero Unit
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = TerracottaPrimary)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ACTIVE SESSION COUNTDOWN",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "35 : 00",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Text(
                    text = "Target: 4 Compound Exercises • Upper Body",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onOpenWorkoutSession,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TextDarkCoffee)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Launch Player Controller", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "IN-LINE EXERCISE CHECKLIST",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMutedSand,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        FocusChecklistItem("Dumbbell Incline Bench Press", "3 Sets x 10 Reps", check1) { check1 = !check1 }
        Spacer(modifier = Modifier.height(6.dp))
        FocusChecklistItem("Bodyweight Push-ups", "3 Sets x 15 Reps", check2) { check2 = !check2 }
        Spacer(modifier = Modifier.height(6.dp))
        FocusChecklistItem("Dumbbell Lateral Raises", "4 Sets x 12 Reps", check3) { check3 = !check3 }
    }
}

@Composable
private fun FocusChecklistItem(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = if (isChecked) TerracottaContainer else CardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isChecked) TerracottaPrimary else CardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(checkedColor = TerracottaPrimary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                Text(text = subtitle, fontSize = 12.sp, color = TextMutedSand)
            }
        }
    }
}

@Composable
private fun ProgressCardItem(
    title: String,
    value: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextMutedSand)
            Spacer(modifier = Modifier.height(6.dp))
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(54.dp)) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = TerracottaPrimary,
                    trackColor = SoftCreamSurface,
                    strokeWidth = 5.dp
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDarkCoffee
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkCoffee,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun QuickActionButton(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isHighlight: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(72.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (isHighlight) TerracottaContainer else CardSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isHighlight) TerracottaPrimary else CardStroke)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = TerracottaPrimary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkCoffee
            )
        }
    }
}

// --------------------------------------------------
// VARIATION 5: CYBER HUD TELEMETRY GRID
// --------------------------------------------------
@Composable
private fun CyberHudLayoutContent(
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onOpenBudgetCalculator: () -> Unit
) {
    var overdriveActive by remember { mutableStateOf(false) }
    var fastRestActive by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "የሳይበር ስልጠና ዳሽቦርድ (CYBER HUD GRID)" else "CYBER HUD TELEMETRY GRID",
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TerracottaPrimary,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Cyber Mesh Main Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = TextDarkCoffee),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, TerracottaPrimary)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Top Live Telemetry Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00E676))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "AI METRIC SYNC: 98% OPTIMAL",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00E676)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = TerracottaPrimary.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaPrimary)
                    ) {
                        Text(
                            text = "HUD MODE v2.4",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = TerracottaPrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Central Circular Telemetry Dial Display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.05f))
                            .border(2.dp, TerracottaPrimary, CircleShape)
                    ) {
                        CircularProgressIndicator(
                            progress = { 0.78f },
                            modifier = Modifier.fillMaxSize(),
                            color = TerracottaPrimary,
                            strokeWidth = 8.dp,
                            trackColor = Color.White.copy(alpha = 0.1f)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "78%", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                            Text(text = "LOAD", fontSize = 8.sp, color = TextMutedSand)
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "TARGET: HYPERTROPHY TAPERING",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TerracottaPrimary
                        )
                        Text(
                            text = "Upper Body Intensity Matrix",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Calculated Rest: 60s • 4 Compound Sets",
                            fontSize = 11.sp,
                            color = TextMutedSand
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Interactive HUD Toggles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        onClick = { overdriveActive = !overdriveActive },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = if (overdriveActive) TerracottaPrimary else Color.White.copy(alpha = 0.08f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (overdriveActive) TerracottaPrimary else Color.White.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Speed, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (overdriveActive) "OVERDRIVE ON" else "+10% LOAD",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Surface(
                        onClick = { fastRestActive = !fastRestActive },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = if (fastRestActive) TerracottaPrimary else Color.White.copy(alpha = 0.08f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (fastRestActive) TerracottaPrimary else Color.White.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Timer, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (fastRestActive) "45s REST" else "60s REST",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onOpenWorkoutSession,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "ENGAGE CYBER WORKOUT SESSION", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

// --------------------------------------------------
// VARIATION 6: STORY DECK & VISUAL HIGHLIGHTS
// --------------------------------------------------
@Composable
private fun StoryDeckLayoutContent(
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit,
    onOpenPhysiqueCheck: () -> Unit,
    onOpenBudgetCalculator: () -> Unit,
    onNavigateToTimeline: () -> Unit
) {
    var selectedStoryIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "የታሪክ ካርዶች እይታ (STORY DECK FLOW)" else "VISUAL HIGHLIGHTS & STORY DECK",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TerracottaPrimary,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Horizontal Story Circles Row
        val storyItems = listOf(
            "Protocol" to Icons.Default.FitnessCenter,
            "100 ETB Fuel" to Icons.Default.Restaurant,
            "Recovery" to Icons.Default.BatteryChargingFull,
            "Physique" to Icons.Default.CameraAlt,
            "AI Adapt" to Icons.Default.AutoAwesome
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(storyItems) { idx, (label, icon) ->
                val isSelected = selectedStoryIndex == idx
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { selectedStoryIndex = idx }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) TerracottaContainer else CardSurface)
                            .border(2.dp, if (isSelected) TerracottaPrimary else CardStroke, CircleShape)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = TerracottaPrimary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) TerracottaPrimary else TextDarkCoffee
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Interactive Story Deck Main Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                when (selectedStoryIndex) {
                    1 -> { // Nutrition Story
                        Text(text = "STORY HIGHLIGHT #2 • ETHIOPIAN FUEL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "High Protein 100 ETB Shiro & Eggs", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                        Text(text = "32g Protein • 540 kcal • Budget Friendly", fontSize = 12.sp, color = TextMutedSand)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onOpenBudgetCalculator,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                        ) {
                            Text(text = "Open Ethiopian Meal Planner →", fontWeight = FontWeight.Bold)
                        }
                    }
                    2 -> { // Recovery Story
                        Text(text = "STORY HIGHLIGHT #3 • RECOVERY SCORE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "92% Recovery Index", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                        Text(text = "7.5 Hours Sleep Logged • Low Muscle Fatigue", fontSize = 12.sp, color = TextMutedSand)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onNavigateToTimeline,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                        ) {
                            Text(text = "View AI Recovery Timeline →", fontWeight = FontWeight.Bold)
                        }
                    }
                    3 -> { // Physique Scan Story
                        Text(text = "STORY HIGHLIGHT #4 • PHYSIQUE SCAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "AI V-Taper Comparison", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                        Text(text = "+1.2 cm Shoulder Width • -0.8 cm Waist", fontSize = 12.sp, color = TextMutedSand)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onOpenPhysiqueCheck,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                        ) {
                            Text(text = "Launch Physique Check AI →", fontWeight = FontWeight.Bold)
                        }
                    }
                    else -> { // Default Workout Story
                        Text(text = "STORY HIGHLIGHT #1 • TODAY'S LIFT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Upper Body Hypertrophy Session", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                        Text(text = "35 Mins • Incline Dumbbell Press & Push-ups", fontSize = 12.sp, color = TextMutedSand)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onOpenWorkoutSession,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                        ) {
                            Text(text = "Start Workout Protocol ▶", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// --------------------------------------------------
// VARIATION 7: MAGAZINE EDITORIAL SPLIT JOURNAL
// --------------------------------------------------
@Composable
private fun MagazineSplitLayoutContent(
    isAmharic: Boolean,
    onOpenWorkoutSession: () -> Unit,
    onOpenBudgetCalculator: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToProgress: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "የመፅሔት እና ጆርናል እይታ (EDITORIAL MAGAZINE)" else "MAGAZINE EDITORIAL JOURNAL",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TerracottaPrimary,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SoftCreamSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Header Journal Edition
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "YEGNAFIT ATHLETE JOURNAL",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TerracottaPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "VOL. 24 • ISSUE #08",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMutedSand
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // High Impact Article Headline
                Text(
                    text = "The Science of High-Protein Shiro & Hypertrophy",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDarkCoffee,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "\"Combining traditional Ethiopian legume wots with boiled egg albumin delivers an optimal 3.2g Leucine spike per meal at minimal cost.\"",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextDarkCoffee.copy(alpha = 0.8f),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = CardStroke)
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "TODAY'S PROTOCOL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                        Text(text = "35 Min Upper Body", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                    }

                    Button(
                        onClick = onOpenWorkoutSession,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TextDarkCoffee)
                    ) {
                        Text(text = "Read & Launch →", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

