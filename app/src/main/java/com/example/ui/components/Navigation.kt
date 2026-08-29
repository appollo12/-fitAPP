package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class NavItemData(
    val titleEn: String,
    val titleAm: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
)

@Composable
fun YegnaFitBottomNavigation(
    currentTab: Int,
    isAmharic: Boolean,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        NavItemData("Home", "መነሻ", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
        NavItemData("Timeline", "ታሪክ", Icons.Filled.DateRange, Icons.Outlined.DateRange, "nav_timeline"),
        NavItemData("Coach", "አሰልጣኝ", Icons.Filled.SmartToy, Icons.Outlined.SmartToy, "nav_coach"),
        NavItemData("Nutrition", "ምግብ", Icons.Filled.Restaurant, Icons.Outlined.Restaurant, "nav_nutrition"),
        NavItemData("Profile", "መገለጫ", Icons.Filled.Person, Icons.Outlined.Person, "nav_profile")
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        color = CardSurface,
        tonalElevation = 6.dp,
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = currentTab == index
                val label = if (isAmharic) item.titleAm else item.titleEn

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .testTag(item.tag)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onTabSelected(index) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(32.dp)
                            .widthIn(min = 48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) TerracottaContainer else Color.Transparent)
                            .padding(horizontal = 12.dp)
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = label,
                            tint = if (isSelected) TerracottaPrimary else TextMutedSand,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                        color = if (isSelected) TerracottaPrimary else TextMutedSand
                    )
                }
            }
        }
    }
}
