package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*
import com.simats.interviewassist.ui.screens.student.CompanyItem
import com.simats.interviewassist.ui.screens.student.getMockCompany
import com.simats.interviewassist.ui.screens.student.FilterBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniHomeScreen(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToPosts: () -> Unit = {},
    onNavigateToAssist: () -> Unit = {},
    onNavigateToShareExperience: () -> Unit = {},
    onNavigateToAllCompanies: () -> Unit = {},
    onNavigateToCompanyDetails: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "IT Services", "AI Solutions", "Software", "Consulting")

    var showFilterSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val focusManager = LocalFocusManager.current
    
    var selectedIndustries by remember { mutableStateOf(setOf<String>()) }
    var selectedDifficulty by remember { mutableStateOf<String?>(null) }
    var selectedDateRange by remember { mutableStateOf("All Time") }

    val allCompanies = remember {
        listOf(
            getMockCompany("Cognizant"),
            getMockCompany("TCS"),
            getMockCompany("Blackstraw"),
            getMockCompany("Hexaware"),
            getMockCompany("Zoho"),
            getMockCompany("Accenture")
        )
    }

    val filteredCompanies = remember(searchQuery, selectedCategory, selectedIndustries, selectedDifficulty) {
        allCompanies.filter { company ->
            val matchesSearch = company.name.contains(searchQuery, ignoreCase = true) ||
                    company.sector.contains(searchQuery, ignoreCase = true)
            
            val matchesCategory = selectedCategory == "All" || 
                    company.sector.contains(selectedCategory, ignoreCase = true)
            
            val matchesIndustry = selectedIndustries.isEmpty() || selectedIndustries.contains(company.sector)
            val matchesDifficulty = selectedDifficulty == null || company.difficulty == selectedDifficulty
            
            matchesSearch && matchesCategory && matchesIndustry && matchesDifficulty
        }
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
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SelectedRoleBg
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AddCircleOutline, contentDescription = "Add") },
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
                    icon = { Icon(Icons.Default.PersonOutline, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToProfile,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
            }
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = onNavigateToAssist,
                    containerColor = PrimaryBlue,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Messages")
                }
                Spacer(modifier = Modifier.height(16.dp))
                FloatingActionButton(
                    onClick = onNavigateToShareExperience,
                    containerColor = PrimaryBlue,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Content", modifier = Modifier.size(32.dp))
                }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
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
                            .clickable { onNavigateToProfile() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("UP", fontSize = 12.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp)
            ) {
                // Search Bar
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search companies...", color = TextBody.copy(alpha = 0.5f)) },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            modifier = Modifier.size(56.dp)
                        ) {
                            IconButton(onClick = { 
                                focusManager.clearFocus()
                                showFilterSheet = true
                            }) {
                                Icon(Icons.Default.FilterAlt, "Filter", tint = TextTitle)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Categories
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { category ->
                            val isSelected = category == selectedCategory
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = category },
                                label = { Text(category) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PrimaryBlue,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color.White,
                                    labelColor = TextBody
                                ),
                                border = null,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.height(40.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Top Companies
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Top Companies", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextTitle)
                        TextButton(onClick = onNavigateToAllCompanies) {
                            Text("See All", color = PrimaryBlue)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(filteredCompanies) { company ->
                    CompanyItem(
                        company = company,
                        onClick = { onNavigateToCompanyDetails(company.name) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }

    if (showFilterSheet) {
        FilterBottomSheet(
            sheetState = sheetState,
            onDismiss = { showFilterSheet = false },
            onApply = { showFilterSheet = false },
            onReset = {
                selectedIndustries = emptySet()
                selectedDifficulty = null
                selectedDateRange = "All Time"
            },
            selectedIndustries = selectedIndustries,
            onIndustryToggle = { industry ->
                selectedIndustries = if (selectedIndustries.contains(industry)) {
                    selectedIndustries - industry
                } else {
                    selectedIndustries + industry
                }
            },
            selectedDifficulty = selectedDifficulty,
            onDifficultySelect = { selectedDifficulty = it },
            selectedDateRange = selectedDateRange,
            onDateRangeSelect = { selectedDateRange = it }
        )
    }
}
