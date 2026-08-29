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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.localization.Localization
import com.example.ui.theme.*
import com.example.viewmodel.MainViewModel

@Composable
fun ProfileScreen(
    viewModel: MainViewModel,
    isAmharic: Boolean,
    onOpenSubscriptionModal: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onOpenOnboarding: () -> Unit,
    onOpenChallenges: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenDataExport: () -> Unit,
    onOpenPayment: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val scrollState = rememberScrollState()

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
            Text(
                text = if (isAmharic) "መገለጫ እና መቼቶች" else "Profile & Settings",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDarkCoffee
            )
            IconButton(onClick = onOpenNotifications) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = TerracottaPrimary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(TerracottaPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "AT", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = userProfile?.name ?: "Abel Tesfaye",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkCoffee
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "👑", fontSize = 16.sp)
                    }

                    Surface(
                        modifier = Modifier.padding(top = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = TerracottaContainer
                    ) {
                        Text(
                            text = "${userProfile?.subscriptionTier ?: "PREMIUM"} MEMBER",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TerracottaPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Subscription Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("manage_subscription_banner"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = TextDarkCoffee)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "YEGNAFIT PREMIUM", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaLight)
                    Text(text = "Adaptive AI Coaching & Physique Check", fontSize = 12.sp, color = Color.White)
                }

                Button(
                    onClick = onOpenPayment,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Text(text = "Manage", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // SETTINGS LIST
        Text(
            text = "SETTINGS & PREFERENCES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMutedSand,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        ProfileSettingItem(
            icon = Icons.Default.Groups,
            title = if (isAmharic) "የማህበረሰብ ውድድሮች" else "Community & Local Challenges",
            subtitle = if (isAmharic) "ከሌሎች አትሌቶች ጋር ይወዳደሩ" else "Addis Ababa lifting groups & runner prep",
            onClick = onOpenChallenges
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProfileSettingItem(
            icon = Icons.Default.TrendingUp,
            title = if (isAmharic) "የእድገት ማጠቃለያ እና ታሪክ" else "Progress & Performance Analytics",
            subtitle = if (isAmharic) "የሳምንት እና የወር ውጤቶች" else "Workout consistency, calories, strength curve",
            onClick = onNavigateToProgress
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProfileSettingItem(
            icon = Icons.Default.Settings,
            title = if (isAmharic) "የመተግበሪያ መቼቶች" else "App Settings & Localization",
            subtitle = if (isAmharic) "ቋንቋ፣ ማሳወቂያዎች እና አውቶማቲክ ማስተካከያ" else "Language, units, notification preferences",
            onClick = onOpenSettings
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProfileSettingItem(
            icon = Icons.Default.Download,
            title = if (isAmharic) "መረጃ ወደ ውጭ ላክ (Data Export)" else "Data Export & Privacy Statement",
            subtitle = if (isAmharic) "የስልጠና ማጠቃለያ በPDF/CSV አውርድ" else "Download PDF summary & CSV backup",
            onClick = onOpenDataExport
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProfileSettingItem(
            icon = Icons.Default.Payments,
            title = "Ethiopian Payment Gateways (Chapa)",
            subtitle = "Telebirr • Chapa • CBE Birr",
            onClick = onOpenPayment
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProfileSettingItem(
            icon = Icons.Default.AutoAwesome,
            title = if (isAmharic) "የስልጠና ማስተካከያ መጠይቅ" else "Setup & Adaptability Questionnaire",
            subtitle = if (isAmharic) "ግብ፣ መሳሪያዎች፣ የጊዜ ሰሌዳ እና በጀት ማስተካከያ" else "Goals, schedule, busy days, equipment & budget",
            onClick = onOpenOnboarding
        )
    }
}

@Composable
private fun ProfileSettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SoftCreamSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = TerracottaPrimary, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkCoffee)
                Text(text = subtitle, fontSize = 12.sp, color = TextMutedSand)
            }

            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextLightGrey)
        }
    }
}
