package com.simats.interviewassist.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReviewsScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToUsers: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToHome,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Shield, contentDescription = "Dashboard") },
                    label = { Text("Dashboard", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToDashboard,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = "Reviews") },
                    label = { Text("Reviews", fontSize = 10.sp) },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SelectedRoleBg
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.People, contentDescription = "Users") },
                    label = { Text("Users", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToUsers,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
                    label = { Text("Settings", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToSettings,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding)
        ) {
            // Top Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Interview Assist",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    Surface(
                        color = Color(0xFFF3E8FF),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Admin",
                            fontSize = 12.sp,
                            color = Color(0xFF9333EA),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onNavigateToNotifications) {
                        Icon(Icons.Default.NotificationsNone, "Notifications", tint = TextTitle)
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, "Settings", tint = TextTitle)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(SelectedRoleBg, CircleShape)
                            .clickable { onNavigateToProfile() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("UP", fontSize = 14.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp)
            ) {
                item {
                    Text(
                        text = "Pending Reviews",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    ModerationCard()
                }
            }
        }
    }
}

@Composable
fun ModerationCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFDBEAFE), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("DK", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "David Kim",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = "Verified",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "SDE-2",
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280)
                    )
                }
                
                Surface(
                    color = Color(0xFFFEE2E2),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Hard",
                        color = Color(0xFFEF4444),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Interview at",
                    fontSize = 15.sp,
                    color = TextTitle
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Amazon",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Leadership principles are key. Prepare stories for each principle using STAR method.",
                fontSize = 15.sp,
                color = Color(0xFF4B5563),
                lineHeight = 22.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CalendarToday,
                        null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF9CA3AF)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Today",
                        fontSize = 14.sp,
                        color = Color(0xFF9CA3AF)
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ThumbUpOffAlt,
                        null,
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFF6B7280)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "0 Helpful",
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Reject", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
                
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00966D))
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Approve", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
