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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSettingsScreen(
    onBack: () -> Unit = {},
    onNavigateToChangePassword: () -> Unit = {},
    onNavigateToHelpSupport: () -> Unit = {},
    onNavigateToPrivacySecurity: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    var darkModeEnabled by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Settings", 
                        fontSize = 20.sp, 
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextTitle)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding),
            contentPadding = PaddingValues(24.dp)
        ) {
            // Preferences Section
            item {
                Text(
                    "Preferences",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        SettingsSwitchItem(
                            icon = Icons.Outlined.Nightlight,
                            label = "Dark Mode",
                            checked = darkModeEnabled,
                            onCheckedChange = { darkModeEnabled = it }
                        )
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = Color(0xFFF3F4F6))
                        SettingsSwitchItem(
                            icon = Icons.Outlined.Notifications,
                            label = "Notifications",
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Account Section
            item {
                Text(
                    "Account",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        SettingsNavItem(icon = Icons.Outlined.Person, label = "Edit Profile")
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = Color(0xFFF3F4F6))
                        SettingsNavItem(
                            icon = Icons.Outlined.Lock, 
                            label = "Change Password",
                            onClick = onNavigateToChangePassword
                        )
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = Color(0xFFF3F4F6))
                        SettingsNavItem(
                            icon = Icons.Outlined.Shield, 
                            label = "Privacy & Security",
                            onClick = onNavigateToPrivacySecurity
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Support Section
            item {
                Text(
                    "Support",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        SettingsNavItem(
                            icon = Icons.Outlined.HelpOutline, 
                            label = "Help & Support",
                            onClick = onNavigateToHelpSupport
                        )
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = Color(0xFFF3F4F6))
                        SettingsNavItem(icon = Icons.Outlined.Description, label = "Terms of Service")
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = Color(0xFFF3F4F6))
                        SettingsNavItem(icon = Icons.Outlined.Assignment, label = "Privacy Policy")
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Sign Out
            item {
                Button(
                    onClick = onSignOut,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                ) {
                    Icon(Icons.Default.Logout, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Sign Out", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Interview Assist v1.0.0",
                        fontSize = 12.sp,
                        color = Color(0xFF9CA3AF)
                    )
                    Text(
                        text = "Made with ❤️ by Alumni",
                        fontSize = 12.sp,
                        color = Color(0xFF9CA3AF),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF3F4F6)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(20.dp), tint = TextTitle)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = TextTitle,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryBlue,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFE5E7EB),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
fun SettingsNavItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF3F4F6)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(20.dp), tint = TextTitle)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = TextTitle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Default.ChevronRight,
            null,
            tint = Color(0xFFD1D5DB),
            modifier = Modifier.size(20.dp)
        )
    }
}
