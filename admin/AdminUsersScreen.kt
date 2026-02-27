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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

data class UserItem(
    val name: String,
    val email: String,
    val role: String,
    val status: String = "active",
    val joinedDate: String = "Jan 2024",
    val isSuspended: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsersScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToReviews: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showDetailsSheet by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<UserItem?>(null) }
    var showUnsuspendDialog by remember { mutableStateOf(false) }
    var userToUnsuspend by remember { mutableStateOf<UserItem?>(null) }
    var showSuspendDialog by remember { mutableStateOf(false) }
    var userToSuspend by remember { mutableStateOf<UserItem?>(null) }
    
    val usersList = remember {
        mutableStateListOf(
            UserItem("Alex Johnson", "alex@uni.edu", "student"),
            UserItem("Sarah Chen", "sarah@google.com", "alumni"),
            UserItem("Mike Ross", "mike@uni.edu", "student", isSuspended = true, status = "suspended"),
            UserItem("Emily Davis", "emily@uni.edu", "student"),
            UserItem("John Smith", "john@microsoft.com", "alumni")
        )
    }

    val filteredUsers = remember(searchQuery, usersList.toList()) {
        usersList.filter { 
            it.name.contains(searchQuery, ignoreCase = true) || 
            it.email.contains(searchQuery, ignoreCase = true) 
        }
    }

    if (showUnsuspendDialog && userToUnsuspend != null) {
        UnsuspendUserDialog(
            user = userToUnsuspend!!,
            onDismiss = { showUnsuspendDialog = false },
            onConfirm = { 
                val index = usersList.indexOfFirst { it.email == userToUnsuspend!!.email }
                if (index != -1) {
                    usersList[index] = usersList[index].copy(isSuspended = false, status = "active")
                    // Update selectedUser if it's the same person
                    if (selectedUser?.email == userToUnsuspend!!.email) {
                        selectedUser = usersList[index]
                    }
                }
                showUnsuspendDialog = false 
            }
        )
    }

    if (showSuspendDialog && userToSuspend != null) {
        SuspendUserDialog(
            user = userToSuspend!!,
            onDismiss = { showSuspendDialog = false },
            onConfirm = { 
                val index = usersList.indexOfFirst { it.email == userToSuspend!!.email }
                if (index != -1) {
                    usersList[index] = usersList[index].copy(isSuspended = true, status = "suspended")
                    // Update selectedUser if it's the same person
                    if (selectedUser?.email == userToSuspend!!.email) {
                        selectedUser = usersList[index]
                    }
                }
                showSuspendDialog = false 
            }
        )
    }

    if (showDetailsSheet && selectedUser != null) {
        ModalBottomSheet(
            onDismissRequest = { showDetailsSheet = false },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFFE5E7EB)) }
        ) {
            UserDetailsSheetContent(
                user = selectedUser!!,
                onClose = { showDetailsSheet = false },
                onToggleStatus = {
                    if (selectedUser!!.isSuspended) {
                        userToUnsuspend = selectedUser
                        showUnsuspendDialog = true
                    } else {
                        userToSuspend = selectedUser
                        showSuspendDialog = true
                    }
                    showDetailsSheet = false
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Text(
                                "All Users", 
                                fontSize = 20.sp, 
                                fontWeight = FontWeight.Bold,
                                color = TextTitle
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = Color(0xFFDBEAFE),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "${usersList.size} Total",
                                    fontSize = 12.sp,
                                    color = PrimaryBlue,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        
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
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextTitle)
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToNotifications) {
                        Icon(Icons.Default.NotificationsNone, "Notifications", tint = TextTitle)
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, "Settings", tint = TextTitle)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
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
                    onClick = onNavigateToReviews,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.People, contentDescription = "Users") },
                    label = { Text("Users", fontSize = 10.sp) },
                    selected = true,
                    onClick = { },
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
            // Search Bar
            Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search users...", color = TextBody.copy(alpha = 0.5f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF3F4F6),
                        unfocusedContainerColor = Color(0xFFF3F4F6),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredUsers) { user ->
                    AdminUserCard(
                        user = user,
                        onViewDetails = {
                            selectedUser = user
                            showDetailsSheet = true
                        },
                        onToggleStatus = {
                            if (user.isSuspended) {
                                userToUnsuspend = user
                                showUnsuspendDialog = true
                            } else {
                                userToSuspend = user
                                showSuspendDialog = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AdminUserCard(
    user: UserItem,
    onViewDetails: () -> Unit,
    onToggleStatus: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(24.dp, 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = Color(0xFFDBEAFE)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    val initials = if (user.name.contains(" ")) {
                        user.name.split(" ").map { it.first() }.joinToString("")
                    } else {
                        user.name.take(1)
                    }
                    Text(
                        initials,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(user.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
                Text(user.email, fontSize = 14.sp, color = Color(0xFF6B7280))
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        color = Color(0xFFF3F4F6),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            user.role,
                            fontSize = 12.sp,
                            color = TextTitle,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    if (user.isSuspended) {
                        Surface(
                            color = Color(0xFFFEE2E2),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "Suspended",
                                fontSize = 12.sp,
                                color = Color(0xFFEF4444),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
            
            IconButton(onClick = onViewDetails) {
                Icon(Icons.Outlined.Visibility, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(24.dp))
            }
            IconButton(onClick = onToggleStatus) {
                Icon(
                    if (user.isSuspended) Icons.Outlined.PersonAddAlt1 else Icons.Outlined.Block, 
                    null, 
                    tint = if (user.isSuspended) Color(0xFF10B981) else Color(0xFFEF4444),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun UserDetailsSheetContent(
    user: UserItem,
    onClose: () -> Unit,
    onToggleStatus: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 32.dp)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "User Details",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextTitle
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = TextTitle)
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color(0xFFF3F4F6), thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                color = Color(0xFFDBEAFE)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    val initials = if (user.name.contains(" ")) {
                        user.name.split(" ").map { it.first() }.joinToString("")
                    } else {
                        user.name.take(1)
                    }
                    Text(
                        initials,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    user.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextTitle
                )
                Text(
                    user.email,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Role", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                Spacer(modifier = Modifier.height(4.dp))
                Text(user.role, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Status", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                Spacer(modifier = Modifier.height(4.dp))
                val statusColor = if (user.isSuspended) Color(0xFFEF4444) else Color(0xFF10B981)
                Text(
                    user.status, 
                    fontSize = 16.sp, 
                    fontWeight = FontWeight.SemiBold, 
                    color = statusColor
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Column {
            Text("Joined", fontSize = 12.sp, color = Color(0xFF9CA3AF))
            Spacer(modifier = Modifier.height(4.dp))
            Text(user.joinedDate, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onToggleStatus,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (user.isSuspended) Color(0xFF10B981) else Color(0xFFEF4444)
            )
        ) {
            Text(
                if (user.isSuspended) "Reactivate User" else "Suspend User",
                fontSize = 16.sp, 
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnsuspendUserDialog(
    user: UserItem,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Unsuspend User?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, null, tint = Color(0xFF9CA3AF))
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    "Are you sure you want to unsuspend ${user.name}? They will regain access to the platform.",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF3F4F6)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel", color = TextTitle, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Unsuspend", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuspendUserDialog(
    user: UserItem,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Suspend User?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, null, tint = Color(0xFF9CA3AF))
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    "Are you sure you want to suspend ${user.name}? They will lose access to the platform until reactivated.",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF3F4F6)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel", color = TextTitle, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF4444)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Suspend", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
