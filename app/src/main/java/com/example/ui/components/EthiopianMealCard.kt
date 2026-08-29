package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.MealPlanEntity
import com.example.ui.theme.*

@Composable
fun EthiopianMealCard(
    meal: MealPlanEntity,
    isAmharic: Boolean,
    onToggleMeal: (Int, Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("meal_card_${meal.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = meal.mealType.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TerracottaPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SoftCreamSurface)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = meal.tier,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextMutedSand
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (isAmharic) meal.amharicTitle else meal.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkCoffee
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${meal.calories} kcal",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMutedSand
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "•",
                        fontSize = 13.sp,
                        color = TextLightGrey
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${meal.costEtb} ETB (ብር)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TerracottaPrimary
                    )
                }
            }

            IconButton(
                onClick = { onToggleMeal(meal.id, meal.isCompleted) },
                modifier = Modifier.testTag("toggle_meal_${meal.id}")
            ) {
                Icon(
                    imageVector = if (meal.isCompleted) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                    contentDescription = "Toggle Meal",
                    tint = if (meal.isCompleted) StatusCompletedText else TextLightGrey,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
