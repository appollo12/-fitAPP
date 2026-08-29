package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.remote.PhysiqueRatingResult
import com.example.ui.theme.*

@Composable
fun PhysiqueRatingCard(
    result: PhysiqueRatingResult,
    isAmharic: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("physique_rating_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header Row with Score Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Aesthetic Analysis",
                        tint = TerracottaPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = if (isAmharic) "የሰውነት ቅርፅ እና አሰላለፍ ግምገማ" else "AI Physique & Aesthetic Analysis",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextDarkCoffee
                        )
                        Text(
                            text = if (isAmharic) "በተመጣጣኝነት እና ቅርፅ የተመሰረተ" else "Symmetry, Proportion & Definition",
                            fontSize = 12.sp,
                            color = TextMutedSand
                        )
                    }
                }

                // Rating Score Badge (1 - 10)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(TerracottaPrimary, TerracottaSecondary)
                            )
                        )
                        .border(2.dp, GoldCrown, CircleShape)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${result.aestheticScore}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "/10",
                            fontSize = 9.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = CardStroke)
            Spacer(modifier = Modifier.height(16.dp))

            // 1. HIGHLIGHTS (Top 2 Strong Aesthetic Features)
            Text(
                text = if (isAmharic) "🌟 ዋና ዋና ጎልተው የወጡ strong ጐኖች (Highlights)" else "🌟 Aesthetic Highlights (Strongest Features)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TerracottaPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChipFeature(text = result.highlight1, isHighlight = true, modifier = Modifier.weight(1f))
                ChipFeature(text = result.highlight2, isHighlight = true, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. GROWTH AREAS (Lagging Focus Areas)
            Text(
                text = if (isAmharic) "🎯 ትኩረት የሚሹ ክፍሎች (Growth Focus Areas)" else "🎯 Growth Focus Areas (Lagging Balance)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkCoffee
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChipFeature(text = result.growthArea1, isHighlight = false, modifier = Modifier.weight(1f))
                ChipFeature(text = result.growthArea2, isHighlight = false, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. POSTURE CHECK
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = SoftCreamSurface
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Posture",
                        tint = StatusCompletedText,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isAmharic) "የቁመና አሰላለፍ ሁኔታ (Posture Check)" else "Posture & Alignment Check",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = TextDarkCoffee
                        )
                        Text(
                            text = result.postureCheck,
                            fontSize = 12.sp,
                            color = TextMutedSand
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. ACTIONABLE TIP
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = TerracottaContainer
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = "Actionable Tip",
                        tint = TerracottaPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isAmharic) "የሚመከር የስልጠና ማስተካከያ (Actionable Tip)" else "Recommended Focus Cue & Exercise Tweak",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = TerracottaPrimary
                        )
                        Text(
                            text = result.actionableTip,
                            fontSize = 12.sp,
                            color = TextDarkCoffee
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. SAFETY & GUARDRAILS DISCLAIMER BANNER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF3F0EB))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Disclaimer",
                    tint = TextLightGrey,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = result.disclaimer,
                    fontSize = 11.sp,
                    color = TextMutedSand,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
private fun ChipFeature(
    text: String,
    isHighlight: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = if (isHighlight) StatusCompletedBg else StatusAdjustedBg
    ) {
        Box(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isHighlight) StatusCompletedText else StatusAdjustedText,
                lineHeight = 16.sp
            )
        }
    }
}
