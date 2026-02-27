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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
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
                    "Your interview experience for Cognizant has been approved and is now live.",
                    "2 hours ago", false
                ),
                Notification(
                    "2", NotificationType.INFO, "New Answer",
                    "Alumni Sarah Chen answered your question about TCS.",
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

