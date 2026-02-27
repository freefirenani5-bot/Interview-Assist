package com.simats.interviewassist.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            color = Color.White
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
