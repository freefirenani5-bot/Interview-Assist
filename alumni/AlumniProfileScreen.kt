package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*
import com.simats.interviewassist.ui.screens.student.ProfileActionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniProfileScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToAssist: () -> Unit = {},
    onNavigateToPosts: () -> Unit = {},
    onNavigateToSettings: () -> Unit,
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToShareExperience: () -> Unit = {},
    onSignOut: () -> Unit
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
                    icon = { Icon(Icons.Outlined.Description, contentDescription = "Posts") },
                    label = { Text("Posts", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToPosts,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 10.sp) },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SelectedRoleBg
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
                            .clickable { }, // Already on profile
                        contentAlignment = Alignment.Center
                    ) {
                        Text("UP", fontSize = 12.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Profile Info Center
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    color = Color(0xFFE5E7EB),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
                ) {
                    // Using a placeholder icon since generation failed, but we'll try to make it look good
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(24.dp),
                        tint = Color.White
                    )
                }
                // Graduation/Verified Badge on Image
                Surface(
                    modifier = Modifier.size(32.dp).offset(x = (-4).dp, y = (-4).dp),
                    shape = CircleShape,
                    color = PrimaryBlue,
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Stars, null, modifier = Modifier.size(18.dp), tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Sarah Chen",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
                Spacer(modifier = Modifier.width(12.dp))
                Surface(
                    color = Color(0xFFD1FAE5),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Verified",
                        fontSize = 12.sp,
                        color = Color(0xFF059669),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.WorkOutline, null, modifier = Modifier.size(18.dp), tint = Color(0xFF6B7280))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Software Engineer",
                    fontSize = 16.sp,
                    color = Color(0xFF6B7280)
                )
                Text(
                    text = "  at  ",
                    fontSize = 14.sp,
                    color = Color(0xFF9CA3AF)
                )
                Text(
                    text = "Google",
                    fontSize = 16.sp,
                    color = Color(0xFF6B7280),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Stats Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AlumniStatCard(
                    label = "Posts",
                    value = "12",
                    labelColor = Color(0xFF4F46E5),
                    modifier = Modifier.weight(1f)
                )
                AlumniStatCard(
                    label = "Likes",
                    value = "345",
                    icon = Icons.Default.ThumbUpOffAlt,
                    labelColor = Color(0xFF10B981),
                    modifier = Modifier.weight(1f)
                )
                AlumniStatCard(
                    label = "Helped",
                    value = "28",
                    icon = Icons.Default.HelpOutline,
                    labelColor = Color(0xFF9333EA),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Action List
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ProfileActionItem(Icons.Outlined.PersonOutline, "Edit Profile", onClick = onNavigateToEditProfile)
                ProfileActionItem(Icons.Outlined.Settings, "Settings", onClick = onNavigateToSettings)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Sign Out Button
            Button(
                onClick = onSignOut,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(20.dp))
                    Text("Sign Out", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
    }
}

@Composable
fun AlumniStatCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    labelColor: Color = PrimaryBlue,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(icon, null, modifier = Modifier.size(14.dp), tint = labelColor)
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = labelColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
