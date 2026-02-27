package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun AlumniPostsScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToAssist: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToShareExperience: () -> Unit,
    onNavigateToEditPost: (String, String) -> Unit = { _, _ -> }
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToHome,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.AddCircleOutline, contentDescription = "Add") },
                    label = { Text("Add", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToShareExperience,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Handshake, contentDescription = "Assist") },
                    label = { Text("Assist", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToAssist,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = "Posts") },
                    label = { Text("Posts", fontSize = 10.sp) },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SelectedRoleBg
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.PersonOutline, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToProfile,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
            }
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Interview Assist",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    Surface(
                        color = SelectedRoleBg,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Alumni",
                            fontSize = 12.sp,
                            color = PrimaryBlue,
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
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(SelectedRoleBg, CircleShape)
                            .clickable { onNavigateToProfile() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("UP", fontSize = 12.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp)
            ) {
            item {
                Text(
                    "My Contributions",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ContributionStatCard(
                        label = "Total Views",
                        value = "1.2k",
                        containerColor = PrimaryBlue,
                        modifier = Modifier.weight(1f)
                    )
                    ContributionStatCard(
                        label = "Helpful Votes",
                        value = "29",
                        containerColor = Color(0xFF6366F1),
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            items(dummyContributions) { post ->
                ContributionItem(post, onEdit = { onNavigateToEditPost(post.company, "1") })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
}

@Composable
fun ContributionStatCard(
    label: String,
    value: String,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(20.dp),
        color = containerColor
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(label, color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(value, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ContributionItem(post: ContributionPost, onEdit: () -> Unit = {}) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp // Added elevation
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header Row: Avatar, Name, Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        color = Color(0xFFE0E7FF) // Lighter blue for avatar bg
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("M", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Me", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextTitle)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.Default.CheckCircle, null, tint = PrimaryBlue, modifier = Modifier.size(16.dp))
                        }
                        Text(post.role, fontSize = 13.sp, color = Color(0xFF6B7280))
                    }
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = post.difficultyColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            post.difficulty,
                            color = post.difficultyColor,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Post",
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            // Status Badge
            Surface(
                color = post.statusBg,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    post.status,
                    color = post.statusColor,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Company Line (Mixed bolding)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Interview at ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextTitle
                )
                Text(
                    text = post.company,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                post.snippet,
                fontSize = 14.sp,
                color = Color(0xFF4B5563),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color(0xFFF3F4F6))
            Spacer(modifier = Modifier.height(16.dp))

            // Footer Row: Date & Helpful
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, null, modifier = Modifier.size(16.dp), tint = Color(0xFF9CA3AF))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(post.date, fontSize = 12.sp, color = Color(0xFF9CA3AF))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ThumbUpOffAlt, null, modifier = Modifier.size(18.dp), tint = Color(0xFF6B7280))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${post.helpfulCount} Helpful", fontSize = 13.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

data class ContributionPost(
    val company: String,
    val role: String,
    val date: String,
    val helpfulCount: Int,
    val snippet: String,
    val difficulty: String,
    val difficultyColor: Color,
    val status: String,
    val statusColor: Color,
    val statusBg: Color
)

val dummyContributions = listOf(
    ContributionPost(
        "Cognizant",
        "Software Engineer",
        "2 days ago",
        24,
        "The interview process consisted of 4 rounds...",
        "Hard",
        Color(0xFFEF4444),
        "approved",
        Color(0xFF059669),
        Color(0xFFD1FAE5)
    ),
    ContributionPost(
        "TCS",
        "SDE-1",
        "1 month ago",
        5,
        "Standard process, focused on arrays and strings...",
        "Medium",
        Color(0xFFF59E0B),
        "pending",
        Color(0xFFB45309),
        Color(0xFFFEF3C7)
    )
)
