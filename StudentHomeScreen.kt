package com.simats.interviewassist.ui.screens.student

import kotlinx.coroutines.launch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.activity.compose.BackHandler
import coil.compose.AsyncImage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun StudentHomeScreen(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToMyQuestions: () -> Unit = {},
    onNavigateToSaved: () -> Unit = {},
    onNavigateToAskQuestion: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Technology", "Finance", "Consulting", "Automotive", "Entertainment")

    var showFilterSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val focusManager = LocalFocusManager.current
    
    var selectedIndustries by remember { mutableStateOf(setOf<String>()) }
    var selectedDifficulty by remember { mutableStateOf<String?>(null) }
    var selectedDateRange by remember { mutableStateOf("All Time") }



    val allCompanies = remember {
        listOf(
            Company("Google", "Mountain View, CA", "Technology", "https://logo.clearbit.com/google.com", "Hard"),
            Company("Goldman Sachs", "New York, NY", "Finance", "https://logo.clearbit.com/goldmansachs.com", "Hard"),
            Company("McKinsey", "Chicago, IL", "Consulting", "https://logo.clearbit.com/mckinsey.com", "Hard"),
            Company("Microsoft", "Redmond, WA", "Technology", "https://logo.clearbit.com/microsoft.com", "Hard"),
            Company("Amazon", "Seattle, WA", "Technology", "https://logo.clearbit.com/amazon.com", "Medium"),
            Company("Tesla", "Austin, TX", "Automotive", "https://logo.clearbit.com/tesla.com", "Hard"),
            Company("Netflix", "Los Gatos, CA", "Entertainment", "https://logo.clearbit.com/netflix.com", "Medium")
        )
    }

    val filteredCompanies = remember(searchQuery, selectedCategory, selectedIndustries, selectedDifficulty) {
        allCompanies.filter { company ->
            val matchesSearch = company.name.contains(searchQuery, ignoreCase = true) ||
                    company.sector.contains(searchQuery, ignoreCase = true)
            
            val matchesCategory = selectedCategory == "All" || company.sector.equals(selectedCategory, ignoreCase = true)
            
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
                    label = { Text("Home") },
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
                    icon = { Icon(Icons.Default.PersonOutline, contentDescription = "Profile") },
                    label = { Text("Profile") },
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
            FloatingActionButton(
                onClick = onNavigateToAskQuestion,
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Outlined.HelpOutline, contentDescription = "Ask Question")
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
                            text = "Student",
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

                // Questions Card
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToMyQuestions() },
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFEDF2FF)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(Color.White, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.ChatBubbleOutline, null, tint = PrimaryBlue)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("My Questions", fontWeight = FontWeight.Bold, color = TextTitle)
                                Text("View questions you've asked", fontSize = 12.sp, color = textPrimary)
                            }
                            Surface(
                                color = Color(0xFFD9E2FF),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "3 questions",
                                    fontSize = 12.sp,
                                    color = PrimaryBlue,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }

                // Top Companies
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Top Companies", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextTitle)
                        TextButton(onClick = { }) {
                            Text("See All", color = PrimaryBlue)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(filteredCompanies) { company ->
                    CompanyItem(company)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApply: () -> Unit,
    onReset: () -> Unit,
    selectedIndustries: Set<String>,
    onIndustryToggle: (String) -> Unit,
    selectedDifficulty: String?,
    onDifficultySelect: (String) -> Unit,
    selectedDateRange: String,
    onDateRangeSelect: (String) -> Unit
) {
    val industries = listOf("Technology", "Finance", "Consulting", "Healthcare", "Retail", "Automotive", "Entertainment")
    val difficulties = listOf("Easy", "Medium", "Hard")
    val dateRanges = listOf("All Time", "Past Week", "Past Month", "Past Year")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFFE5E7EB)) },
        scrimColor = Color.Black.copy(alpha = 0.32f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter Experiences",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = TextTitle.copy(alpha = 0.5f))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Industry
            Text("Industry", fontWeight = FontWeight.Bold, color = TextTitle, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                industries.chunked(3).forEach { rowIndustries ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        rowIndustries.forEach { sector ->
                            val isSelected = selectedIndustries.contains(sector)
                            FilterChip(
                                selected = isSelected,
                                onClick = { onIndustryToggle(sector) },
                                label = { Text(sector, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PrimaryBlue.copy(alpha = 0.12f),
                                    selectedLabelColor = PrimaryBlue,
                                    containerColor = Color(0xFFF9FAFB),
                                    labelColor = Color(0xFF4B5563)
                                ),
                                border = null,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Difficulty Level
            Text("Difficulty Level", fontWeight = FontWeight.Bold, color = TextTitle, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                difficulties.forEach { difficulty ->
                    val isSelected = selectedDifficulty == difficulty
                    val diffColor = when(difficulty) {
                        "Easy" -> Color(0xFF10B981) // Emerald Green
                        "Medium" -> Color(0xFFF59E0B) // Amber Orange
                        "Hard" -> Color(0xFFEF4444) // Rose Red
                        else -> PrimaryBlue
                    }
                    FilterChip(
                        selected = isSelected,
                        onClick = { onDifficultySelect(difficulty) },
                        label = { Text(difficulty, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = diffColor.copy(alpha = 0.15f),
                            selectedLabelColor = diffColor,
                            containerColor = Color(0xFFF9FAFB),
                            labelColor = Color(0xFF4B5563)
                        ),
                        border = null,
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Date Posted
            Text("Date Posted", fontWeight = FontWeight.Bold, color = TextTitle, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    dateRanges.take(2).forEach { range ->
                        DateFilterButton(
                            text = range,
                            isSelected = selectedDateRange == range,
                            onClick = { onDateRangeSelect(range) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    dateRanges.drop(2).forEach { range ->
                        DateFilterButton(
                            text = range,
                            isSelected = selectedDateRange == range,
                            onClick = { onDateRangeSelect(range) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onReset,
                    modifier = Modifier.weight(1f).height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Reset All", color = Color(0xFF4B5563), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Button(
                    onClick = onApply,
                    modifier = Modifier.weight(1f).height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text("Apply Filters", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}



@Composable
fun DateFilterButton(text: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        color = if (isSelected) PrimaryBlue else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (!isSelected) BorderStroke(1.dp, Color(0xFFF0F0F0)) else null
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = if (isSelected) Color.White else TextBody,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

data class Company(val name: String, val location: String, val sector: String, val logoUrl: String, val difficulty: String)

@Composable
fun CompanyItem(company: Company) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 0.dp,
        border = BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = company.logoUrl,
                    contentDescription = company.name,
                    modifier = Modifier.size(36.dp),
                    error = null // Or use a placeholder if you prefer
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = company.name,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle,
                        fontSize = 16.sp
                    )
                    Surface(
                        color = when(company.difficulty) {
                            "Easy" -> Color(0xFF10B981).copy(alpha = 0.1f)
                            "Medium" -> Color(0xFFF59E0B).copy(alpha = 0.1f)
                            "Hard" -> Color(0xFFEF4444).copy(alpha = 0.1f)
                            else -> Color(0xFFE5E7EB)
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = company.difficulty,
                            fontSize = 10.sp,
                            color = when(company.difficulty) {
                                "Easy" -> Color(0xFF10B981)
                                "Medium" -> Color(0xFFF59E0B)
                                "Hard" -> Color(0xFFEF4444)
                                else -> Color(0xFF4B5563)
                            },
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(12.dp), tint = Color(0xFF9CA3AF))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(company.location, fontSize = 12.sp, color = Color(0xFF6B7280))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.BusinessCenter, null, modifier = Modifier.size(12.dp), tint = Color(0xFF9CA3AF))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(company.sector, fontSize = 12.sp, color = Color(0xFF6B7280))
                    }
                }
            }
            
            Icon(
                Icons.Default.ChevronRight,
                null,
                tint = Color(0xFFD1D5DB)
            )
        }
    }
}

val textPrimary = Color(0xFF5E6D7E)
