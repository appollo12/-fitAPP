package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.ui.theme.*

@Composable
fun SubscriptionModalDialog(
    currentTier: String,
    isAmharic: Boolean,
    onDismiss: () -> Unit,
    onSelectTier: (String) -> Unit
) {
    var selectedTier by remember { mutableStateOf(currentTier) }
    var selectedGateway by remember { mutableStateOf("Telebirr") }
    var receiptReference by remember { mutableStateOf("") }
    var isVerified by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(24.dp),
            color = CreamBackground
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isAmharic) "የደንበኝነት ምዝገባ ዕቅዶች" else "Subscription & Payment",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkCoffee
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_subscription_dialog")) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tier Options
                TierSelectionCard(
                    tierName = "STANDARD",
                    price = "150 ETB (ብር) / mo",
                    features = listOf("Adaptive Workout Timeline", "Ethiopian Meal Recommendations", "Daily Progress Rings"),
                    isSelected = selectedTier == "STANDARD",
                    onClick = { selectedTier = "STANDARD" }
                )

                Spacer(modifier = Modifier.height(10.dp))

                TierSelectionCard(
                    tierName = "PREMIUM",
                    price = "349 ETB (ብር) / mo",
                    features = listOf("Everything in Standard", "AI Physique & Aesthetic Rating", "Unlimited AI Coach Chat", "Telebirr & Chapa Auto-Pay"),
                    isSelected = selectedTier == "PREMIUM",
                    onClick = { selectedTier = "PREMIUM" }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Gateway Selector
                Text(
                    text = if (isAmharic) "የክፍያ መንገድ ይምረጡ" else "Select Ethiopian Gateway",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMutedSand
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val gateways = listOf("Telebirr", "Chapa", "CBE Birr")
                    gateways.forEach { gw ->
                        val isGwSelected = selectedGateway == gw
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedGateway = gw },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isGwSelected) TerracottaContainer else CardSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isGwSelected) TerracottaPrimary else CardStroke)
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = gw,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isGwSelected) TerracottaPrimary else TextDarkCoffee
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Receipt Ref / Transaction ID Field
                OutlinedTextField(
                    value = receiptReference,
                    onValueChange = {
                        receiptReference = it
                        isVerified = it.trim().length >= 6
                    },
                    label = { Text(if (isAmharic) "የደረሰኝ ቁጥር (Receipt / Ref No)" else "Transaction / Ref No. (e.g. TL10842)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("receipt_reference_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TerracottaPrimary,
                        unfocusedBorderColor = CardStroke
                    )
                )

                if (isVerified) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        color = StatusCompletedBg
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = StatusCompletedText, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isAmharic) "ደረሰኝ በ $selectedGateway ተረጋገጠ" else "Receipt verified via $selectedGateway Instant Check",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = StatusCompletedText
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onSelectTier(selectedTier) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("confirm_subscription_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Text(
                        text = if (isAmharic) "ምዝገባውን አረጋግጥ ($selectedGateway)" else "Confirm Tier ($selectedGateway)",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun TierSelectionCard(
    tierName: String,
    price: String,
    features: List<String>,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) TerracottaContainer else CardSurface
        ),
        border = androidx.compose.foundation.BorderStroke(2.dp, if (isSelected) TerracottaPrimary else CardStroke)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = tierName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TerracottaPrimary)
                Text(text = price, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = TextDarkCoffee)
            }

            Spacer(modifier = Modifier.height(8.dp))

            features.forEach { feat ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = StatusCompletedText, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = feat, fontSize = 12.sp, color = TextDarkCoffee)
                }
            }
        }
    }
}
