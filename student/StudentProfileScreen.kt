package com.simats.interviewassist.ui.screens.student

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@Composable
fun StudentProfileScreen(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToHelpSupport: () -> Unit = {},
    onNavigateToMyQuestions: () -> Unit = {},
    onNavigateToBecomeAlumni: () -> Unit = {},
    onNavigateToSaved: () -> Unit = {},
    onSignOut: () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = onNavigateToHome,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.BookmarkBorder, contentDescription = "Saved") },
                    label = { Text("Saved") },
                    selected = false,
                    onClick = onNavigateToSaved,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f),
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back Button Row
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextTitle)
                }
            }

            // Profile Header
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = Color(0xFFE5E7EB)
            ) {
                // Placeholder for profile image
                Icon(
                    Icons.Default.Person, 
                    contentDescription = null, 
                    modifier = Modifier.padding(20.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Alex Johnson", 
                fontSize = 22.sp, 
                fontWeight = FontWeight.Bold, 
                color = TextTitle
            )
            Text(
                "Computer Science", 
                fontSize = 14.sp, 
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Stats Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    icon = Icons.Outlined.BookmarkBorder,
                    value = "12",
                    label = "Saved",
                    onClick = onNavigateToSaved,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Icons.Outlined.HelpOutline,
                    value = "12",
                    label = "My Questions",
                    onClick = onNavigateToMyQuestions,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Academic Info
            AcademicInfoSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Become an Alumni Banner
            AlumniBanner(onClick = onNavigateToBecomeAlumni)

            Spacer(modifier = Modifier.height(24.dp))

            // Action Items
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ProfileActionItem(Icons.Outlined.PersonOutline, "Edit Profile", onClick = onNavigateToEditProfile)
                ProfileActionItem(Icons.Outlined.Settings, "App Settings", onClick = onNavigateToSettings)
                ProfileActionItem(Icons.Outlined.HelpOutline, "Help & Support", onClick = onNavigateToHelpSupport)
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

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Version 1.0.0 • Interview Assist", 
                fontSize = 12.sp, 
                color = Color(0xFF9CA3AF)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatCard(
    icon: ImageVector, 
    value: String, 
    label: String, 
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = PrimaryBlue, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextTitle)
            Text(text = label, fontSize = 12.sp, color = PrimaryBlue, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun AcademicInfoSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Academic Info", 
                fontSize = 16.sp, 
                fontWeight = FontWeight.Bold, 
                color = TextTitle
            )
            Spacer(modifier = Modifier.height(20.dp))
            AcademicItem(Icons.Outlined.School, "Class of", "Final Year (2024)")
            Spacer(modifier = Modifier.height(16.dp))
            AcademicItem(Icons.Outlined.Email, "Email", "alex.j@university.edu")
        }
    }
}

@Composable
fun AcademicItem(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF3F7FF)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(20.dp), tint = PrimaryBlue)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color(0xFF9CA3AF))
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextTitle)
        }
    }
}

@Composable
fun AlumniBanner(onClick: () -> Unit = {}) {
    Surface(
        color = Color(0xFFF3F7FF),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF6366F1), Color(0xFFA855F7))
                    )
                )
                .clickable { onClick() }
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Become an Alumni", 
                        color = Color.White, 
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "New", 
                            color = Color.White, 
                            fontSize = 10.sp, 
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Got placed? Share your experiences and help juniors!", 
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
            Icon(Icons.Default.ArrowOutward, null, tint = Color.White)
        }
    }
}

@Composable
fun ProfileActionItem(icon: ImageVector, title: String, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                title, 
                modifier = Modifier.weight(1f), 
                fontWeight = FontWeight.Medium, 
                color = TextTitle,
                fontSize = 15.sp
            )
            Icon(Icons.Default.ChevronRight, null, tint = Color(0xFFD1D5DB), modifier = Modifier.size(20.dp))
        }
    }
}
