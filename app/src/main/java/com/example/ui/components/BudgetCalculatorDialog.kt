package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun BudgetCalculatorDialog(
    isAmharic: Boolean,
    resultText: String,
    onCalculate: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var budgetInput by remember { mutableStateOf("100") }

    LaunchedEffect(Unit) {
        onCalculate(100)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            color = CreamBackground
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Restaurant, contentDescription = null, tint = TerracottaPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAmharic) "በ X ብር የሚመከሩ ምግቦች" else "Budget Meal Generator",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkCoffee
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_budget_dialog")) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = budgetInput,
                    onValueChange = { budgetInput = it },
                    label = { Text("Budget in ETB (ብር)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("budget_input_field"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val amount = budgetInput.toIntOrNull() ?: 100
                        onCalculate(amount)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("generate_budget_meals_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Text(text = if (isAmharic) "አማራጮችን አውጣ" else "Generate Options", fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (resultText.isNotBlank()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
                    ) {
                        Box(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = resultText,
                                fontSize = 13.sp,
                                color = TextDarkCoffee,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
