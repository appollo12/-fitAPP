package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PhysiqueRatingCard
import com.example.ui.localization.Localization
import com.example.ui.theme.*
import com.example.viewmodel.MainViewModel

@Composable
fun CoachScreen(
    viewModel: MainViewModel,
    isAmharic: Boolean
) {
    val chatMessages by viewModel.chatMessages.collectAsState()
    val physiqueRatings by viewModel.physiqueRatings.collectAsState()
    val isAnalyzing by viewModel.isAnalyzingPhysique.collectAsState()
    val latestPhysiqueResult by viewModel.latestPhysiqueResult.collectAsState()

    var inputMessageText by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: AI Chat, 1: AI Physique Rating Tool

    val context = LocalContext.current

    // Photo picker for AI Physique Analysis
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            }
            viewModel.analyzePhysiquePhoto(bitmap)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(TerracottaContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.SmartToy, contentDescription = "AI Coach", tint = TerracottaPrimary)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = Localization.get("ai_coach", isAmharic),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkCoffee
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(StatusCompletedText)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Live Intelligence", fontSize = 12.sp, color = TextMutedSand)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sub-tabs: 0 = Context & Chat, 1 = AI Physique Rating Feature
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = CardSurface,
            contentColor = TerracottaPrimary,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text(if (isAmharic) "የአይአይ ውይይት (Chat)" else "AI Coach Chat", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(if (isAmharic) "የሰውነት ቅርፅ ግምገማ" else "Physique Rating", fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedTab == 0) {
            // TAB 0: CONTEXTUAL CARDS & CHAT
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 60.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Avatar + Welcome Chat Bubble (Screen 8)
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = TerracottaContainer,
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.SmartToy, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(24.dp))
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Card(
                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = CardSurface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isAmharic) "በዚህ ሳምንት ባሳዩት ወጥነት ጥሩ ስራ ሰርተዋል!" else "Great job staying consistent this week!",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextDarkCoffee,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }

                // 1. WORKOUT CARD
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = SoftCreamSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = CardSurface,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(20.dp))
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "WORKOUT", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = TerracottaPrimary)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (isAmharic)
                                        "ጥሩ ታዛዥነት። የዛሬውን የእንቅስቃሴ አፈፃፀም ለማሻሻል አዳዲስ ልምምዶችን ጨምረናል።"
                                    else
                                        "Good adherence. Let's add 5lbs to your shoulder press performance.",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextDarkCoffee
                                )
                            }
                        }
                    }
                }

                // 2. NUTRITION CARD
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = SoftCreamSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = CardSurface,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Restaurant, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(20.dp))
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "NUTRITION", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = TerracottaPrimary)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (isAmharic)
                                        "ምግብዎ ከበጀትዎ ጋር ይጣጣማል። ቀጣይ፡ ዝቅተኛ ወጪ ያለው የፕሮቲን አማራጭ።"
                                    else
                                        "Your diet is matching your budget. Next a cheaper option.",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextDarkCoffee
                                )
                            }
                        }
                    }
                }

                // 3. RECOVERY CARD
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = SoftCreamSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = CardSurface,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Bedtime, contentDescription = null, tint = TerracottaPrimary, modifier = Modifier.size(20.dp))
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "RECOVERY", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = TerracottaPrimary)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (isAmharic)
                                        "እረፍትዎ እየተሻሻለ ነው። የመተኛት ጊዜዎን በቅደም ተከተል ይጠብቁ።"
                                    else
                                        "Your sleep is improving. Keep prioritizing your bedtime.",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextDarkCoffee
                                )
                            }
                        }
                    }
                }

                items(chatMessages) { msg ->
                    ChatBubble(msg = msg.message, isUser = msg.sender == "user")
                }
            }

            // Sticky "Ask Coach" Terracotta Button at bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Button(
                    onClick = {
                        if (inputMessageText.isNotBlank()) {
                            viewModel.sendMessageToCoach(inputMessageText)
                            inputMessageText = ""
                        } else {
                            viewModel.sendMessageToCoach("Can you review my nutrition budget and workout recovery today?")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("ask_coach_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                ) {
                    Text(
                        text = if (isAmharic) "አሰልጣኙን ጠይቅ (Ask Coach)" else "Ask Coach",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        } else {
            // TAB 1: AI PHYSIQUE & AESTHETIC RATING FEATURE
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardStroke)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Physique Rating",
                                tint = TerracottaPrimary,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (isAmharic) "የሰውነት ቅርፅ ግምገማ (AI Physique Check)" else "AI Physique & Aesthetic Rating",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkCoffee
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isAmharic)
                                    "የተመጣጣኝነት፣ የቅርፅ እና የቁመና አሰላለፍ AI ግምገማ ለማግኘት ፎቶ ያውርዱ።"
                                else
                                    "Upload a physique photo to receive structured aesthetic feedback on symmetry, proportion, and posture.",
                                fontSize = 13.sp,
                                color = TextMutedSand,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { photoPickerLauncher.launch("image/*") },
                                enabled = !isAnalyzing,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("upload_physique_photo_button")
                            ) {
                                if (isAnalyzing) {
                                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Analyzing Symmetry...", color = Color.White)
                                } else {
                                    Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = Color.White)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isAmharic) "ፎቶ መርጥ እና ገምግም" else "Select & Analyze Photo",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                // Render latest result card or previous saved card
                if (latestPhysiqueResult != null) {
                    item {
                        PhysiqueRatingCard(result = latestPhysiqueResult!!, isAmharic = isAmharic)
                    }
                } else if (physiqueRatings.isNotEmpty()) {
                    val last = physiqueRatings.first()
                    item {
                        val parsedResult = com.example.data.remote.PhysiqueRatingResult(
                            aestheticScore = last.aestheticScore,
                            highlight1 = last.highlight1,
                            highlight2 = last.highlight2,
                            growthArea1 = last.growthArea1,
                            growthArea2 = last.growthArea2,
                            postureCheck = last.postureCheck,
                            actionableTip = last.actionableTip,
                            disclaimer = last.disclaimerText
                        )
                        PhysiqueRatingCard(result = parsedResult, isAmharic = isAmharic)
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(msg: String, isUser: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            color = if (isUser) TerracottaPrimary else CardSurface,
            shadowElevation = 2.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = msg,
                    fontSize = 14.sp,
                    color = if (isUser) Color.White else TextDarkCoffee,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
