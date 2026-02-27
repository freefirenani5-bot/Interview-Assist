package com.simats.interviewassist.ui.screens.admin

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

data class AlumniRequest(
    val id: String,
    val name: String,
    val email: String,
    val info: String, // e.g., "Netflix • Class of 2023"
    val initials: String,
    val isUpgrade: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniRequestsScreen(
    onBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToReviews: () -> Unit = {},
    onNavigateToUsers: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("New Alumni", "Student Upgrades")
    
    val newAlumniRequests = remember {
        mutableStateListOf(
            AlumniRequest("1", "Alice Smith", "alice@example.com", "Netflix • Class of 2023", "AS"),
            AlumniRequest("2", "Bob Jones", "bob@example.com", "Tesla • Class of 2022", "BJ")
        )
    }
    
    val studentUpgradeRequests = remember {
        mutableStateListOf(
            AlumniRequest("3", "Charlie Brown", "charlie@uni.edu", "Google • Class of 2024", "CB", isUpgrade = true),
            AlumniRequest("4", "Diana Prince", "diana@uni.edu", "Microsoft • Class of 2024", "DP", isUpgrade = true)
        )
    }

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
                    icon = { Icon(Icons.Outlined.Description, contentDescription = "Reviews") },
                    label = { Text("Reviews", fontSize = 10.sp) },
                    selected = false,
                    onClick = { /* TODO */ },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.People, contentDescription = "Users") },
                    label = { Text("Users", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToUsers,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SelectedRoleBg
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

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Alumni Requests",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = PrimaryBlue,
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = PrimaryBlue
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 16.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selectedTab == index) PrimaryBlue else Color(0xFF6B7280)
                                )
                            }
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val currentList = if (selectedTab == 0) newAlumniRequests else studentUpgradeRequests
                
                items(currentList) { request ->
                    AlumniRequestCard(
                        request = request,
                        onApprove = { currentList.remove(request) },
                        onReject = { currentList.remove(request) }
                    )
                }
            }
        }
    }
}

@Composable
fun AlumniRequestCard(
    request: AlumniRequest,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    color = Color(0xFFDBEAFE)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = request.initials,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryBlue
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = request.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                        if (request.isUpgrade) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Surface(
                                color = Color(0xFFE0E7FF),
                                shape = RoundedCornerShape(100.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.NorthEast,
                                        null,
                                        modifier = Modifier.size(14.dp),
                                        tint = PrimaryBlue
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "Upgrade",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = request.info,
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280)
                    )
                    Text(
                        text = request.email,
                        fontSize = 14.sp,
                        color = Color(0xFF9CA3AF)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Reject Button
                TextButton(
                    onClick = onReject,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.textButtonColors(contentColor = TextTitle)
                ) {
                    Icon(Icons.Default.Close, null, modifier = Modifier.size(20.dp), tint = TextTitle)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Reject", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                
                // Approve Button
                Button(
                    onClick = onApprove,
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00966D))
                ) {
                    Icon(Icons.Default.Check, null, modifier = Modifier.size(20.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Approve", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
