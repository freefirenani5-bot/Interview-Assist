package com.simats.interviewassist.ui.screens.student

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
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

data class Notification(
    val id: String,
    val type: NotificationType,
    val title: String,
    val description: String,
    val timestamp: String,
    var isRead: Boolean = false
)

enum class NotificationType {
    SUCCESS, INFO, WARNING
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentNotificationsScreen(
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Unread")
    
    // Initial mock data based on screenshots
    var notifications by remember {
        mutableStateOf(
            listOf(
                Notification(
                    "1", NotificationType.SUCCESS, "Experience Approved",
                    "Your interview experience for Google has been approved and is now live.",
                    "2 hours ago", false
                ),
                Notification(
                    "2", NotificationType.INFO, "New Answer",
                    "Alumni Sarah Chen answered your question about Microsoft.",
                    "5 hours ago", false
                ),
                Notification(
                    "3", NotificationType.WARNING, "Profile Incomplete",
                    "Please update your graduation year in your profile settings.",
                    "1 day ago", true
                ),
                Notification(
                    "4", NotificationType.INFO, "New Feature",
                    "You can now bookmark interview experiences to read later.",
                    "2 days ago", true
                )
            )
        )
    }

    val displayNotifications = if (selectedTab == 0) {
        notifications
    } else {
        notifications.filter { !it.isRead }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Notifications",
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
                actions = {
                    IconButton(onClick = {
                        notifications = notifications.map { it.copy(isRead = true) }
                    }) {
                        Icon(Icons.Default.DoneAll, contentDescription = "Mark all as read", tint = PrimaryBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = PrimaryBlue,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = PrimaryBlue,
                        height = 2.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                fontSize = 14.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedTab == index) PrimaryBlue else Color(0xFF6B7280)
                            )
                        }
                    )
                }
            }

            if (displayNotifications.isEmpty()) {
                EmptyNotificationsState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(displayNotifications) { notification ->
                        NotificationItem(
                            notification = notification,
                            onMarkAsRead = {
                                notifications = notifications.map {
                                    if (it.id == notification.id) it.copy(isRead = true) else it
                                }
                            },
                            onDismiss = {
                                notifications = notifications.filter { it.id != notification.id }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onMarkAsRead: () -> Unit,
    onDismiss: () -> Unit
) {
    val statusColor = when (notification.type) {
        NotificationType.SUCCESS -> Color(0xFF10B981)
        NotificationType.INFO -> PrimaryBlue
        NotificationType.WARNING -> Color(0xFFF59E0B)
    }

    val icon = when (notification.type) {
        NotificationType.SUCCESS -> Icons.Default.CheckCircleOutline
        NotificationType.INFO -> Icons.Default.Info
        NotificationType.WARNING -> Icons.Default.Warning
    }

    val backgroundColor = if (!notification.isRead) Color(0xFFF8FAFF) else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onMarkAsRead() }
            .padding(20.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Icon
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = Color.White,
            border = if (!notification.isRead) null else null
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(24.dp), tint = statusColor)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notification.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextTitle
                )
                Text(
                    text = notification.timestamp,
                    fontSize = 11.sp,
                    color = Color(0xFF9CA3AF)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = notification.description,
                fontSize = 13.sp,
                color = Color(0xFF4B5563),
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Actions
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(PrimaryBlue, CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(Icons.Default.Close, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun EmptyNotificationsState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = Color(0xFFF9FAFB)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Outlined.Notifications,
                    null,
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFFD1D5DB)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "No notifications",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextTitle
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "You're all caught up!",
            fontSize = 14.sp,
            color = Color(0xFF6B7280)
        )
    }
}
