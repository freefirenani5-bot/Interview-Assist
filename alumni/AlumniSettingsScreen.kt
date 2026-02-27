package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.simats.interviewassist.ui.screens.student.SettingsSectionTitle
import com.simats.interviewassist.ui.screens.student.ToggleSettingsItem
import com.simats.interviewassist.ui.screens.student.NavigationSettingsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniSettingsScreen(
    onBack: () -> Unit,
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToHelpSupport: () -> Unit = {},
    onNavigateToPrivacySecurity: () -> Unit = {},
    onNavigateToChangePassword: () -> Unit = {},
    onSignOut: () -> Unit
) {
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
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Preferences Section
            SettingsSectionTitle("Preferences")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    var darkMode by remember { mutableStateOf(false) }
                    var notifications by remember { mutableStateOf(true) }
                    
                    ToggleSettingsItem(
                        icon = Icons.Outlined.ModeNight,
                        title = "Dark Mode",
                        checked = darkMode,
                        onCheckedChange = { darkMode = it }
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF3F4F6))
                    ToggleSettingsItem(
                        icon = Icons.Outlined.Notifications,
                        title = "Notifications",
                        checked = notifications,
                        onCheckedChange = { notifications = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Account Section
            SettingsSectionTitle("Account")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    NavigationSettingsItem(Icons.Outlined.Person, "Edit Profile", onClick = onNavigateToEditProfile)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF3F4F6))
                    NavigationSettingsItem(
                        Icons.Outlined.Lock, 
                        "Change Password",
                        onClick = onNavigateToChangePassword
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF3F4F6))
                    NavigationSettingsItem(
                        Icons.Outlined.Security, 
                        "Privacy & Security", 
                        onClick = onNavigateToPrivacySecurity
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Support Section
            SettingsSectionTitle("Support")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    NavigationSettingsItem(Icons.Outlined.HelpOutline, "Help & Support", onClick = onNavigateToHelpSupport)
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF3F4F6))
                    NavigationSettingsItem(Icons.Outlined.Description, "Terms of Service")
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF3F4F6))
                    NavigationSettingsItem(Icons.Outlined.PrivacyTip, "Privacy Policy")
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

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

            Spacer(modifier = Modifier.height(32.dp))

            // Footer
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
                    "Made with ❤️ by Alumni", 
                    fontSize = 12.sp, 
                    color = Color(0xFF9CA3AF),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
