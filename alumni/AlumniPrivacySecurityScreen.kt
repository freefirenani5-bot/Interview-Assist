package com.simats.interviewassist.ui.screens.alumni

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniPrivacySecurityScreen(
    onBack: () -> Unit
) {
    var profileVisibility by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Privacy & Security", 
                        fontSize = 18.sp, 
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
            // Privacy Section
            AlumniPrivacySecuritySectionTitle("Privacy")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    AlumniPrivacyToggleItem(
                        icon = Icons.Outlined.Visibility,
                        title = "Profile Visibility",
                        subtitle = "Allow others to see your profile",
                        checked = profileVisibility,
                        onCheckedChange = { profileVisibility = it }
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF3F4F6))
                    AlumniPrivacyNavigationItem(
                        icon = Icons.Outlined.FileDownload,
                        title = "Download My Data",
                        subtitle = "Get a copy of your data"
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Security Section
            AlumniPrivacySecuritySectionTitle("Security")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    AlumniPrivacyNavigationItem(
                        icon = Icons.Outlined.Shield,
                        title = "Two-Factor Authentication",
                        subtitle = "Add extra security to your account"
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Danger Zone Section
            AlumniPrivacySecuritySectionTitle("Danger Zone")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    AlumniPrivacyNavigationItem(
                        icon = Icons.Outlined.Delete,
                        title = "Delete Account",
                        subtitle = "Permanently delete your account",
                        isDestructive = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun AlumniPrivacySecuritySectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF6B7280),
        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
    )
}

@Composable
fun AlumniPrivacyToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF3F7FF)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(20.dp), tint = PrimaryBlue)
            }
        }
        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF6B7280))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryBlue,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFD1D5DB)
            )
        )
    }
}

@Composable
fun AlumniPrivacyNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isDestructive: Boolean = false,
    onClick: () -> Unit = {}
) {
    val tintColor = if (isDestructive) Color(0xFFEF4444) else PrimaryBlue
    val iconBgColor = if (isDestructive) Color(0xFFFEF2F2) else Color(0xFFF3F7FF)
    val titleColor = if (isDestructive) Color(0xFFEF4444) else TextTitle

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = iconBgColor
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(20.dp), tint = tintColor)
            }
        }
        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = titleColor)
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF6B7280))
        }
        Icon(
            Icons.Default.ChevronRight,
            null,
            tint = if (isDestructive) Color(0xFFEF4444).copy(alpha = 0.5f) else Color(0xFFD1D5DB),
            modifier = Modifier.size(20.dp)
        )
    }
}
