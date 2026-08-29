package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
fun NutritionScreen(
    viewModel: MainViewModel,
    isAmharic: Boolean,
    onOpenBudgetCalculator: () -> Unit
) {
    val mealPlans by viewModel.mealPlans.collectAsState()
    val selectedTier by viewModel.selectedMealTier.collectAsState()

    var activeSubTab by remember { mutableIntStateOf(0) } // 0: Today's Nutrition, 1: Weekly Budget Plan

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Header: Today's Nutrition
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Localization.get("todays_nutrition", isAmharic),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDarkCoffee
                )
                
                Surface(
                    onClick = onOpenBudgetCalculator,
                    shape = RoundedCornerShape(20.dp),
                    color = TerracottaContainer,
                    border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaPrimary)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Calculate, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isAmharic) "የበጀት ማስያ" else "ETB Budget",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TerracottaPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Sub-tabs
            TabRow(
                selectedTabIndex = activeSubTab,
                containerColor = CardSurface,
                contentColor = TerracottaPrimary,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = activeSubTab == 0,
                    onClick = { activeSubTab = 0 },
                    text = { Text(if (isAmharic) "የዛሬ ምግቦች (Today)" else "Today's Nutrition", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeSubTab == 1,
                    onClick = { activeSubTab = 1 },
                    text = { Text(if (isAmharic) "የሳምንት በጀት (Weekly)" else "Meal Plan (Week)", fontWeight = FontWeight.Bold) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (activeSubTab == 0) {
                // TODAY'S NUTRITION (SCREEN 6 MATCH)
                
                // Date Bar Selector: < Thursday, 16 May >
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Prev Day", tint = TextDarkCoffee)
                    }
                    Text(
                        text = "Thursday, 16 May",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkCoffee,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    IconButton(onClick = {}, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.ChevronRight, contentDescription = "Next Day", tint = TextDarkCoffee)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Large Calorie Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "CALORIES", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMutedSand, letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = "1,420", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextDarkCoffee)
                                Text(text = " / 2,000 total", fontSize = 13.sp, color = TextMutedSand, modifier = Modifier.padding(bottom = 2.dp))
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Remaining", fontSize = 10.sp, color = TextMutedSand)
                                Text(text = "400 ETB", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = { 0.71f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = TerracottaPrimary,
                            trackColor = SoftCreamSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "MEALS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMutedSand,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Scrollable List of Meal Cards
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 60.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(mealPlans) { meal ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("meal_card_${meal.id}"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CardSurface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (meal.isCompleted) StatusCompletedText else CardStroke)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Checkbox
                                Icon(
                                    imageVector = if (meal.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = "Status",
                                    tint = if (meal.isCompleted) StatusCompletedText else TextMutedSand,
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clickable {
                                            viewModel.toggleMealCompletion(meal.id, meal.isCompleted)
                                        }
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = meal.mealType.uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextMutedSand
                                    )
                                    Text(
                                        text = meal.title,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDarkCoffee
                                    )
                                    Text(
                                        text = "${meal.calories} kcal • ${meal.costEtb} ETB",
                                        fontSize = 12.sp,
                                        color = TerracottaPrimary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                // Circular Food Icon / Graphic
                                Surface(
                                    shape = CircleShape,
                                    color = TerracottaContainer,
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Restaurant,
                                            contentDescription = meal.title,
                                            tint = TerracottaPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // WEEKLY MEAL PLAN TAB
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = TextDarkCoffee)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(text = "WEEKLY BUDGET", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaLight)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "1,050 ETB", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        Text(text = "Spent: 330 ETB • Remaining: 720 ETB", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onOpenBudgetCalculator,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Icon(Icons.Default.Calculate, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Calculate Meal for Budget", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(14.dp))

                val weekDays = listOf(
                    Triple("Mon", "Shiro, Injera, Eggs", "150 ETB"),
                    Triple("Tue", "Mitmita Firfir, Eggs", "160 ETB"),
                    Triple("Wed", "Tibs, Tef Injera", "180 ETB"),
                    Triple("Thu", "Firfir, Shiro Wot", "150 ETB"),
                    Triple("Fri", "Kik Wot, Injera", "130 ETB"),
                    Triple("Sat", "Pasta Wot", "120 ETB"),
                    Triple("Sun", "Free Meal / Choice", "170 ETB")
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(weekDays) { (day, meal, price) ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = CardSurface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = day, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = TerracottaPrimary)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(text = meal, fontSize = 13.sp, color = TextDarkCoffee)
                                }
                                Text(text = price, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextMutedSand)
                            }
                        }
                    }
                }
            }
        }

        // Sticky "Add Meal" Terracotta Button at bottom
        if (activeSubTab == 0) {
            Button(
                onClick = onOpenBudgetCalculator,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("add_meal_sticky_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = if (isAmharic) "+ ምግብ ጨምር (Add Meal)" else "Add Meal",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
