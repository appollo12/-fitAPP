package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.MainViewModel

// -----------------------------------------------------------------------------
// SCREEN 11: COMMUNITY CHALLENGES
// -----------------------------------------------------------------------------
@Composable
fun ChallengesScreen(
    isAmharic: Boolean,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("challenges_back_button")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDarkCoffee)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isAmharic) "የማህበረሰብ ውድድሮች (Challenges)" else "Community Challenges",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDarkCoffee
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Featured Ethiopian Challenge Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = TextDarkCoffee)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = TerracottaPrimary
                ) {
                    Text(
                        text = if (isAmharic) "ወቅታዊ ውድድር" else "FEATURED LOCAL",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isAmharic) "የአዲስ አበባ ሩጫ እና ጥንካሬ 30 ቀን" else "Addis 30-Day Strength & Run Prep",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = if (isAmharic) "ከ1,400+ ኢትዮጵያውያን ጋር በቤት ውስጥ ጥንካሬን ይገንቡ" else "Join 1,400+ Ethiopians building home strength & endurance.",
                    fontSize = 12.sp,
                    color = TextMutedSand
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("join_featured_challenge_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Text(
                        text = if (isAmharic) "ተቀላቀል (Join Challenge)" else "Join Challenge",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "LOCAL GROUP CHALLENGES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMutedSand,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Group Challenge Items
        ChallengeItemCard(
            title = if (isAmharic) "የቦሌ ጥንካሬ ቡድን (Bole Lifting)" else "Bole Fitness Group",
            participants = "420 Members",
            reward = "500 Birr Voucher",
            isJoined = true,
            isAmharic = isAmharic
        )

        Spacer(modifier = Modifier.height(10.dp))

        ChallengeItemCard(
            title = if (isAmharic) "የሽሮ ፕሮቲን 30 ቀን ፈተና" else "Shiro & Protein Consistency 30D",
            participants = "890 Members",
            reward = "YegnaFit Badge 🏅",
            isJoined = false,
            isAmharic = isAmharic
        )
    }
}

@Composable
private fun ChallengeItemCard(
    title: String,
    participants: String,
    reward: String,
    isJoined: Boolean,
    isAmharic: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, CardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = TerracottaContainer,
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Groups, contentDescription = null, tint = TerracottaPrimary)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                Text(text = "$participants • $reward", fontSize = 12.sp, color = TextMutedSand)
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (isJoined) {
                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, StatusAdjustedText)
                ) {
                    Text(text = if (isAmharic) "ተቀላቅለዋል" else "Joined", fontSize = 11.sp, color = StatusAdjustedText)
                }
            } else {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Text(text = if (isAmharic) "ቀላቀል" else "Join", fontSize = 11.sp, color = Color.White)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// SCREEN 12: NOTIFICATIONS
// -----------------------------------------------------------------------------
@Composable
fun NotificationsScreen(
    isAmharic: Boolean,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("notifications_back_button")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDarkCoffee)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isAmharic) "ማሳወቂያዎች (Notifications)" else "Notifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDarkCoffee
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notification List (Screen 12 from reference image)
        NotificationItem(
            icon = Icons.Default.DirectionsRun,
            category = "RECENT ACTIVITY",
            message = if (isAmharic) "የዛሬውን ስልጠና በስኬት አጠናቀዋል። +120 kcal" else "You logged today's workout activity. +120 kcal burned.",
            time = "2 hours ago"
        )

        Spacer(modifier = Modifier.height(10.dp))

        NotificationItem(
            icon = Icons.Default.EmojiEvents,
            category = "GOAL ACHIEVED",
            message = if (isAmharic) "በዚህ ሳምንት 150 ብር በምግብ በጀትዎ ላይ ቆጥበዋል።" else "You saved 150 ETB on your weekly food budget!",
            time = "Yesterday"
        )

        Spacer(modifier = Modifier.height(10.dp))

        NotificationItem(
            icon = Icons.Default.SmartToy,
            category = "COACH MESSAGE",
            message = if (isAmharic) "አሰልጣኝ፡ ጥሩ ታዛዥነት። የዛሬውን የመጨረሻ ስብስብ ትንሽ ጨምሬዋለሁ።" else "Coach: Good adherence. Let me know if you need meal substitutions.",
            time = "2 days ago"
        )
    }
}

@Composable
private fun NotificationItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    category: String,
    message: String,
    time: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, CardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = CircleShape,
                color = TerracottaContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaPrimary)
                    Text(text = time, fontSize = 11.sp, color = TextMutedSand)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message,
                    fontSize = 13.sp,
                    color = TextDarkCoffee,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------
// SCREEN 13: SETTINGS
// -----------------------------------------------------------------------------
@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    isAmharic: Boolean,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    var personalNotifications by remember { mutableStateOf(true) }
    var privacyToggle by remember { mutableStateOf(true) }
    var adaptiveAutoAdjust by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("settings_back_button")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDarkCoffee)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isAmharic) "መቼቶች (Settings)" else "Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDarkCoffee
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Language & Localization Switcher
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = BorderStroke(1.dp, CardStroke)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "App Language", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                    Text(text = if (isAmharic) "አማርኛ" else "English", fontSize = 12.sp, color = TextMutedSand)
                }

                Row {
                    FilterChip(
                        selected = !isAmharic,
                        onClick = { if (isAmharic) viewModel.toggleLanguage() },
                        label = { Text("English") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TerracottaPrimary,
                            selectedLabelColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    FilterChip(
                        selected = isAmharic,
                        onClick = { if (!isAmharic) viewModel.toggleLanguage() },
                        label = { Text("አማርኛ") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TerracottaPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Toggles matching Screen 13
        SettingToggleCard(
            title = if (isAmharic) "ራስ-ሰር ማስተካከያ (Adaptive Auto-Adjust)" else "Adaptive Timeline Auto-Adjust",
            subtitle = if (isAmharic) "እርከን እና የጊዜ ሰሌዳ በስልጠና አፈፃፀም መሰረት ይለወጣል" else "Automatically adjust workouts based on completed sets",
            checked = adaptiveAutoAdjust,
            onCheckedChange = { adaptiveAutoAdjust = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        SettingToggleCard(
            title = if (isAmharic) "የግል ማሳወቂያዎች" else "Personal Notifications",
            subtitle = if (isAmharic) "የስልጠና እና የምግብ ሰዓት ማስታወሻዎች" else "Reminders for scheduled workouts and daily meals",
            checked = personalNotifications,
            onCheckedChange = { personalNotifications = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        SettingToggleCard(
            title = if (isAmharic) "የግላዊነት ጥበቃ (Privacy Guard)" else "Strict Privacy Mode",
            subtitle = if (isAmharic) "ምስሎች በስልክዎ ላይ ብቻ ይጠበቃሉ" else "Physique photos stay private on your local device",
            checked = privacyToggle,
            onCheckedChange = { privacyToggle = it }
        )
    }
}

@Composable
private fun SettingToggleCard(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, CardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                Text(text = subtitle, fontSize = 12.sp, color = TextMutedSand)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = TerracottaPrimary
                )
            )
        }
    }
}

// -----------------------------------------------------------------------------
// SCREEN 14: DATA EXPORT
// -----------------------------------------------------------------------------
@Composable
fun DataExportScreen(
    isAmharic: Boolean,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("data_export_back_button")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDarkCoffee)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isAmharic) "መረጃ ወደ ውጭ ላክ (Data Export)" else "Data Export",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDarkCoffee
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Data Ownership Statement Card (Screen 14 matching design board)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = BorderStroke(1.dp, CardStroke)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = StatusAdjustedText)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAmharic) "ግልጽ የመረጃ ባለቤትነት መግለጫ" else "CLEAR DATA OWNERSHIP STATEMENT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = StatusAdjustedText
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isAmharic)
                        "የእርስዎ መረጃ የእርስዎ ብቻ ነው። YegnaFit መረጃዎን ለሶስተኛ ወገን አይሸጥም። የስልጠና እና የምግብ ታሪክዎን በማንኛውም ጊዜ በPDF ወይም CSV ማውረድ ይችላሉ።"
                    else
                        "Your data remains strictly yours. YegnaFit guarantees total ownership. You can export your full workout log, meal history, and budget metrics anytime in PDF or CSV format.",
                    fontSize = 13.sp,
                    color = TextDarkCoffee,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Solid Download Button
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("download_data_pdf_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAmharic) "PDF ማጠቃለያ አውርድ (Download PDF)" else "Download PDF Summary",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Outlined Download CSV Button
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("download_data_csv_button"),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, TerracottaPrimary)
                ) {
                    Icon(Icons.Default.TableChart, contentDescription = null, tint = TerracottaPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAmharic) "CSV መረጃ አውርድ (Download CSV)" else "Download Raw CSV Data",
                        fontWeight = FontWeight.Bold,
                        color = TerracottaPrimary
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// SCREEN 15: PAYMENT (CHAPA / TELEBIRR)
// -----------------------------------------------------------------------------
@Composable
fun PaymentChapaScreen(
    isAmharic: Boolean,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedGateway by remember { mutableStateOf("Telebirr") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("payment_back_button")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDarkCoffee)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isAmharic) "የክፍያ አማራጮች (Payment Methods)" else "Payment Methods",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDarkCoffee
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Chapa Banner Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = TextDarkCoffee)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CreditCard, contentDescription = null, tint = TerracottaLight, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = "POWERED BY CHAPA ETHIOPIA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaLight)
                    Text(text = "Direct Birr Transactions & Instant Activation", fontSize = 12.sp, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "SELECT PAYMENT GATEWAY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMutedSand,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Gateway Radio Options
        PaymentGatewayOption(
            name = "Telebirr",
            subtext = "Instant Birr Mobile Payment (C8E Birr)",
            icon = Icons.Default.PhoneAndroid,
            isSelected = selectedGateway == "Telebirr",
            onSelect = { selectedGateway = "Telebirr" }
        )

        Spacer(modifier = Modifier.height(10.dp))

        PaymentGatewayOption(
            name = "CBE Birr",
            subtext = "Commercial Bank of Ethiopia Direct (C62 Birr)",
            icon = Icons.Default.AccountBalance,
            isSelected = selectedGateway == "CBE Birr",
            onSelect = { selectedGateway = "CBE Birr" }
        )

        Spacer(modifier = Modifier.height(10.dp))

        PaymentGatewayOption(
            name = "Debit / Credit Card",
            subtext = "Visa, Mastercard or Local Ethiopian Cards",
            icon = Icons.Default.CreditCard,
            isSelected = selectedGateway == "Card",
            onSelect = { selectedGateway = "Card" }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("pay_with_chapa_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
        ) {
            Text(
                text = if (isAmharic) "በ $selectedGateway ክፈል (250 ETB/mo)" else "Pay with $selectedGateway (250 ETB/mo)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun PaymentGatewayOption(
    name: String,
    subtext: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) TerracottaContainer else CardSurface
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) TerracottaPrimary else CardStroke
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(selectedColor = TerracottaPrimary)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(imageVector = icon, contentDescription = name, tint = TerracottaPrimary, modifier = Modifier.size(24.dp))

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                Text(text = subtext, fontSize = 11.sp, color = TextMutedSand)
            }
        }
    }
}
