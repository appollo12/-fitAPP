package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

@Composable
fun OnboardingScreen(
    isAmharic: Boolean,
    onToggleLanguage: () -> Unit,
    onCompleteOnboarding: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }

    // User Adaptability Collected Answers
    var selectedGoal by remember { mutableStateOf("Muscle Hypertrophy & Taper") }
    var selectedWorkoutDays by remember { mutableStateOf("4 Days / Week") }
    var selectedTimeLimit by remember { mutableStateOf("30 - 45 Minutes") }
    var selectedBusyDay by remember { mutableStateOf("Thursday & Busy Evenings") }
    var selectedEquipment by remember { mutableStateOf("Dumbbells & Resistance Bands") }
    var selectedBudgetTier by remember { mutableStateOf("150 - 250 ETB / Day (Balanced)") }
    var selectedSleep by remember { mutableStateOf("6 - 7 Hours (Moderate)") }
    var autoAdjustEnabled by remember { mutableStateOf(true) }

    val totalSteps = 6
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header with Step Counter & Language Indicator Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "YegnaFit",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TerracottaPrimary
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Language Toggle Pill
                Surface(
                    onClick = onToggleLanguage,
                    shape = RoundedCornerShape(20.dp),
                    color = SoftCreamSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaPrimary.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .testTag("onboarding_language_toggle")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "Language Switcher",
                            tint = TerracottaPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isAmharic) "አማርኛ" else "English",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkCoffee
                        )
                    }
                }

                if (currentStep in 1..totalSteps) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SoftCreamSurface,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (isAmharic) "ደረጃ $currentStep ከ $totalSteps" else "Step $currentStep of $totalSteps",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkCoffee,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
                TextButton(
                    onClick = onCompleteOnboarding,
                    modifier = Modifier.testTag("skip_onboarding_button")
                ) {
                    Text(
                        text = if (isAmharic) "ወደ ዳሽቦርድ ሂድ" else "Skip to Dashboard",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TerracottaPrimary
                    )
                }
            }
        }

        // Linear Progress Bar for questionnaire steps
        if (currentStep in 1..totalSteps) {
            LinearProgressIndicator(
                progress = { currentStep.toFloat() / totalSteps },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = TerracottaPrimary,
                trackColor = SoftCreamSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Screen Body Content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            when (currentStep) {
                0 -> WelcomeStep(
                    isAmharic = isAmharic,
                    onToggleLanguage = onToggleLanguage,
                    onStart = { currentStep = 1 },
                    onSkip = onCompleteOnboarding
                )
                1 -> GoalSelectionStep(
                    isAmharic = isAmharic,
                    selectedGoal = selectedGoal,
                    onSelectGoal = { selectedGoal = it }
                )
                2 -> ScheduleAdaptabilityStep(
                    isAmharic = isAmharic,
                    selectedDays = selectedWorkoutDays,
                    onSelectDays = { selectedWorkoutDays = it },
                    selectedTime = selectedTimeLimit,
                    onSelectTime = { selectedTimeLimit = it },
                    selectedBusyDay = selectedBusyDay,
                    onSelectBusyDay = { selectedBusyDay = it }
                )
                3 -> EquipmentLocationStep(
                    isAmharic = isAmharic,
                    selectedEquipment = selectedEquipment,
                    onSelectEquipment = { selectedEquipment = it }
                )
                4 -> NutritionBudgetStep(
                    isAmharic = isAmharic,
                    selectedBudgetTier = selectedBudgetTier,
                    onSelectBudget = { selectedBudgetTier = it }
                )
                5 -> RecoveryAdaptationStep(
                    isAmharic = isAmharic,
                    selectedSleep = selectedSleep,
                    onSelectSleep = { selectedSleep = it },
                    autoAdjustEnabled = autoAdjustEnabled,
                    onToggleAutoAdjust = { autoAdjustEnabled = it }
                )
                6 -> OnboardingSummaryStep(
                    isAmharic = isAmharic,
                    goal = selectedGoal,
                    schedule = "$selectedWorkoutDays ($selectedTimeLimit)",
                    equipment = selectedEquipment,
                    budget = selectedBudgetTier,
                    autoAdjust = autoAdjustEnabled,
                    onFinish = onCompleteOnboarding
                )
            }
        }

        // Bottom Navigation Actions for Steps 1..5
        if (currentStep in 1..5) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { currentStep-- },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("onboarding_back_button")
                ) {
                    Text(text = if (isAmharic) "ተመለስ" else "Back", color = TextDarkCoffee)
                }

                Button(
                    onClick = { currentStep++ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("onboarding_next_button")
                ) {
                    Text(
                        text = if (currentStep == 5)
                            (if (isAmharic) "ዕቅድ ፍጠር" else "Generate Plan")
                        else
                            (if (isAmharic) "ቀጥል" else "Next"),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}

@Composable
private fun WelcomeStep(
    isAmharic: Boolean,
    onToggleLanguage: () -> Unit,
    onStart: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Language Switcher Selector Card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SoftCreamSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "Core Language",
                        tint = TerracottaPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAmharic) "የቋንቋ መምረጫ (Language)" else "Select Core Language",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkCoffee
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        onClick = { if (!isAmharic) onToggleLanguage() },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isAmharic) TerracottaPrimary else CardSurface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isAmharic) TerracottaPrimary else CardStroke
                        )
                    ) {
                        Text(
                            text = "አማርኛ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isAmharic) Color.White else TextDarkCoffee,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    Surface(
                        onClick = { if (isAmharic) onToggleLanguage() },
                        shape = RoundedCornerShape(10.dp),
                        color = if (!isAmharic) TerracottaPrimary else CardSurface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (!isAmharic) TerracottaPrimary else CardStroke
                        )
                    ) {
                        Text(
                            text = "English",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isAmharic) Color.White else TextDarkCoffee,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Column {
                    Text(
                        text = if (isAmharic) "የእርስዎ ዕቅድ" else "Your plan",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDarkCoffee,
                        lineHeight = 36.sp
                    )
                    Text(
                        text = if (isAmharic) "ይቀየራል" else "changes",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TerracottaPrimary,
                        lineHeight = 36.sp
                    )
                    Text(
                        text = if (isAmharic) "እርስዎ ሲቀየሩ።" else "as you change.",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDarkCoffee,
                        lineHeight = 36.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (isAmharic)
                        "ለእርስዎ የተሰራ በ AI የሚሰራ የአካል ብቃት፣ አመጋገብ እና የህይወት ዘይቤ አሰልጣኝ።"
                    else
                        "AI-powered fitness, nutrition and lifestyle coaching built for you.",
                    fontSize = 14.sp,
                    color = TextMutedSand,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Feature Highlights Container
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FeatureHighlightRow(
                        icon = Icons.Default.EventRepeat,
                        title = if (isAmharic) "ለእርስዎ የሚስማማ ዕቅድ" else "Plan Adaptation",
                        desc = if (isAmharic) "እርስዎ ሲቀየሩ ዕቅድዎም አብሮ ይቀየራል" else "Plans change as your life changes"
                    )
                    FeatureHighlightRow(
                        icon = Icons.Default.Restaurant,
                        title = if (isAmharic) "የሀገር ውስጥ ምግብ እና በጀት" else "Local Nutrition",
                        desc = if (isAmharic) "የኢትዮጵያ உணቦች እና የበጀት ቁጥጥር" else "Real Ethiopian foods & budget aware"
                    )
                    FeatureHighlightRow(
                        icon = Icons.Default.SmartToy,
                        title = if (isAmharic) "በ AI የሚመራ አሰልጣኝ" else "AI Coach Guidance",
                        desc = if (isAmharic) "በማንኛውም ጊዜ የሚሰጥ ምክር" else "Personal guidance when you need it"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("onboarding_get_started_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
        ) {
            Text(
                text = if (isAmharic) "ጀምር" else "Get Started",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onSkip,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("welcome_enter_dashboard_button")
        ) {
            Text(
                text = if (isAmharic) "መለያ አለኝ" else "I have an account",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkCoffee
            )
        }
    }
}

@Composable
private fun GoalSelectionStep(
    isAmharic: Boolean,
    selectedGoal: String,
    onSelectGoal: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "ዋናው የአካል ብቃት ግብዎ ምንድነው?" else "What is your primary fitness goal?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkCoffee
        )
        Text(
            text = if (isAmharic) "AI አሰልጣኙ የልምምድ ዓይነቶችን በዚህ መሰረት ያዘጋጃል።" else "AI Coach will tailor volume & intensity accordingly.",
            fontSize = 13.sp,
            color = TextMutedSand
        )

        Spacer(modifier = Modifier.height(16.dp))

        val goals = listOf(
            "Muscle Hypertrophy & Taper" to (if (isAmharic) "የጡንቻ መጠን መጨመር እና ቅርፅ" else "Build lean muscle & V-taper frame"),
            "Fat Loss & Lean Physique" to (if (isAmharic) "ስብ መቀነስ እና የሰውነት መስመር" else "Burn body fat while retaining muscle"),
            "Functional Strength & Power" to (if (isAmharic) "ተግባራዊ ጥንካሬ እና ጉልበት" else "Compound movement performance"),
            "Endurance & General Health" to (if (isAmharic) "ፅናት እና አጠቃላይ ጤና" else "Stamina, cardio, and joint health")
        )

        goals.forEach { (title, subtitle) ->
            SelectableCardOption(
                title = title,
                subtitle = subtitle,
                isSelected = selectedGoal == title,
                onClick = { onSelectGoal(title) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun ScheduleAdaptabilityStep(
    isAmharic: Boolean,
    selectedDays: String,
    onSelectDays: (String) -> Unit,
    selectedTime: String,
    onSelectTime: (String) -> Unit,
    selectedBusyDay: String,
    onSelectBusyDay: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "የጊዜ ሰሌዳዎ እና የሥራ ሁኔታዎ" else "Schedule & Time Constraints",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkCoffee
        )
        Text(
            text = if (isAmharic) "ጊዜ የሌለዎት ቀናት ሲያጋጥሙ AI ስልጠናውን በራሱ ይቀንሳል።" else "YegnaFit automatically scales sessions down on busy days.",
            fontSize = 13.sp,
            color = TextMutedSand
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "DAYS PER WEEK", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
        Spacer(modifier = Modifier.height(6.dp))
        val daysOptions = listOf("3 Days / Week", "4 Days / Week", "5 Days / Week")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            daysOptions.forEach { option ->
                ChoiceChip(text = option, isSelected = selectedDays == option, onClick = { onSelectDays(option) }, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "AVERAGE SESSION WINDOW", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
        Spacer(modifier = Modifier.height(6.dp))
        val timeOptions = listOf("15 - 30 Min", "30 - 45 Min", "45 - 60 Min")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            timeOptions.forEach { option ->
                ChoiceChip(text = option, isSelected = selectedTime == option, onClick = { onSelectTime(option) }, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "TYPICAL BUSY DAYS (FOR AUTO-ADAPTATION)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
        Spacer(modifier = Modifier.height(6.dp))

        val busyOptions = listOf(
            "Thursday & Busy Evenings",
            "Mondays & Start of Week",
            "Variable / Shift Schedule"
        )
        busyOptions.forEach { bOpt ->
            SelectableCardOption(
                title = bOpt,
                subtitle = if (isAmharic) "በነዚህ ቀናት ስልጠናው ወደ 20-25 ደቂቃ ይቀነሳል" else "Sessions will auto-condense to key compound movements",
                isSelected = selectedBusyDay == bOpt,
                onClick = { onSelectBusyDay(bOpt) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun EquipmentLocationStep(
    isAmharic: Boolean,
    selectedEquipment: String,
    onSelectEquipment: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "የስልጠና ቦታ እና መሳሪያዎች" else "Equipment & Workout Environment",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkCoffee
        )
        Text(
            text = if (isAmharic) "ያለዎት መሳሪያ ብቻ ጥቅም ላይ ይውላል።" else "Workouts adapt seamlessly whether at home or gym.",
            fontSize = 13.sp,
            color = TextMutedSand
        )

        Spacer(modifier = Modifier.height(16.dp))

        val equipList = listOf(
            "Bodyweight & Calisthenics (At Home)" to (if (isAmharic) "የሰውነት ክብደት እና ቤት ውስጥ" else "No gear required. Pull-up bar optional."),
            "Dumbbells & Resistance Bands" to (if (isAmharic) "ዳምበል እና የመለጠጫ ባንድ" else "Home gym or light dumbbell setup."),
            "Full Gym & Barbell Access" to (if (isAmharic) "ሙሉ ጂም እና ባርቤል" else "Full commercial gym access.")
        )

        equipList.forEach { (title, subtitle) ->
            SelectableCardOption(
                title = title,
                subtitle = subtitle,
                isSelected = selectedEquipment == title,
                onClick = { onSelectEquipment(title) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun NutritionBudgetStep(
    isAmharic: Boolean,
    selectedBudgetTier: String,
    onSelectBudget: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "የኢትዮጵያ ምግብ እና የዕለት በጀት" else "Ethiopian Nutrition & Meal Budget",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkCoffee
        )
        Text(
            text = if (isAmharic) "በጀትዎ ላይ የተመሰረተ የምግብ ምክር እና የፕሮቲን መጠን።" else "Meal plans built around authentic local Ethiopian dishes & Birr costs.",
            fontSize = 13.sp,
            color = TextMutedSand
        )

        Spacer(modifier = Modifier.height(16.dp))

        val budgets = listOf(
            "100 - 150 ETB / Day (Economy)" to (if (isAmharic) "ሽሮ፣ እንቁላል፣ ምስር ወጥ፣ ቅንጬ" else "Shiro Wot, Boiled Eggs, Misir Wot, Kinche"),
            "150 - 250 ETB / Day (Balanced)" to (if (isAmharic) "እንቁላል ፍርፍር፣ የዶሮ ጥብስ፣ የጡት ሥጋ" else "Egg Firfir, Chicken Tibs, Fish, Vegetables"),
            "300+ ETB / Day (High Protein)" to (if (isAmharic) "የበሬ ጥብስ፣ የተመረጡ ፍራፍሬዎች፣ እርጎ" else "Beef Tibs, Avocados, Protein Smoothies, Dairy")
        )

        budgets.forEach { (title, subtitle) ->
            SelectableCardOption(
                title = title,
                subtitle = subtitle,
                isSelected = selectedBudgetTier == title,
                onClick = { onSelectBudget(title) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun RecoveryAdaptationStep(
    isAmharic: Boolean,
    selectedSleep: String,
    onSelectSleep: (String) -> Unit,
    autoAdjustEnabled: Boolean,
    onToggleAutoAdjust: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (isAmharic) "ማገገም እና የነቃ AI ቅንብር" else "Recovery & Auto-Adaptability Signals",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkCoffee
        )
        Text(
            text = if (isAmharic) "የእንቅልፍ እና የድካም ምልክቶችን ለመረዳት ይረዳል።" else "Allows AI Coach to modify session load on low recovery days.",
            fontSize = 13.sp,
            color = TextMutedSand
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "AVERAGE SLEEP DURATION", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
        Spacer(modifier = Modifier.height(6.dp))

        val sleepOpts = listOf(
            "< 6 Hours (High Fatigue Potential)",
            "6 - 7 Hours (Moderate)",
            "7 - 8+ Hours (Optimal Recovery)"
        )
        sleepOpts.forEach { sl ->
            SelectableCardOption(
                title = sl,
                subtitle = "",
                isSelected = selectedSleep == sl,
                onClick = { onSelectSleep(sl) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Auto-Adjust Switch Toggle Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SoftCreamSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isAmharic) "ስልጠናዎችን በራሱ አስተካክል" else "Enable Active Auto-Adaptation",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkCoffee
                    )
                    Text(
                        text = if (isAmharic) "ድካም ወይም በጀት ሲቀየር AI በራሱ ዕቅዱን ያስተካክላል" else "Auto-condense workouts when sleep is low or time is restricted",
                        fontSize = 12.sp,
                        color = TextMutedSand
                    )
                }
                Switch(
                    checked = autoAdjustEnabled,
                    onCheckedChange = onToggleAutoAdjust,
                    colors = SwitchDefaults.colors(checkedThumbColor = TerracottaPrimary)
                )
            }
        }
    }
}

@Composable
private fun OnboardingSummaryStep(
    isAmharic: Boolean,
    goal: String,
    schedule: String,
    equipment: String,
    budget: String,
    autoAdjust: Boolean,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = TerracottaPrimary,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isAmharic) "ለእርስዎ የተስተካከለ AI ዕቅድ ተዘጋጅቷል!" else "Your Adaptive Plan is Ready!",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDarkCoffee
        )

        Text(
            text = if (isAmharic) "በቀን በቀን ለውጦችዎ መሰረት እያደገ የሚሄድ ስልጠና።" else "AI Coach has configured your adaptive timeline parameters.",
            fontSize = 13.sp,
            color = TextMutedSand
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(text = "ADAPTATION PARAMETERS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                Spacer(modifier = Modifier.height(12.dp))

                SummaryRowItem(label = if (isAmharic) "ግብ" else "Goal", value = goal)
                SummaryRowItem(label = if (isAmharic) "የጊዜ ሰሌዳ" else "Schedule", value = schedule)
                SummaryRowItem(label = if (isAmharic) "መሳሪያዎች" else "Equipment", value = equipment)
                SummaryRowItem(label = if (isAmharic) "ምግብ በጀት" else "Nutrition Budget", value = budget)
                SummaryRowItem(label = if (isAmharic) "ራስ-ሰር ማስተካከያ" else "Auto-Adapt", value = if (autoAdjust) "ACTIVE" else "MANUAL")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("enter_yegnafit_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
        ) {
            Text(
                text = if (isAmharic) "ወደ የኛFit ግባ" else "Enter YegnaFit Command Center",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun SummaryRowItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = TextMutedSand)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
    }
}

@Composable
private fun ChoiceChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(42.dp),
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) TerracottaContainer else CardSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TerracottaPrimary else CardStroke)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(
                text = text,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) TerracottaPrimary else TextDarkCoffee
            )
        }
    }
}

@Composable
private fun SelectableCardOption(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) TerracottaContainer else CardSurface
        ),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, if (isSelected) TerracottaPrimary else CardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) TerracottaPrimary else TextDarkCoffee
                )
                if (subtitle.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = TextMutedSand
                    )
                }
            }

            if (isSelected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = TerracottaPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun FeatureHighlightRow(
    icon: ImageVector,
    title: String,
    desc: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SoftCreamSurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = TerracottaContainer,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = TerracottaPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkCoffee
                )
                Text(
                    text = desc,
                    fontSize = 11.sp,
                    color = TextMutedSand
                )
            }
        }
    }
}
