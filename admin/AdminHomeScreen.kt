package com.simats.interviewassist.ui.screens.admin

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*
import com.simats.interviewassist.R
import com.simats.interviewassist.ui.screens.student.Company
import com.simats.interviewassist.ui.screens.student.getMockCompany
import com.simats.interviewassist.ui.screens.student.ExamSection
import com.simats.interviewassist.ui.screens.student.ProcessStep
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.BorderStroke

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onAddCompany: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToReviews: () -> Unit = {},
    onNavigateToUsers: () -> Unit = {},
    onNavigateToCompanyDetails: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var showAddCompanySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Form states
    var companyName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var industry by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("Medium") }
    val examPattern = remember { mutableStateListOf<ExamSection>() }
    val hiringProcess = remember { mutableStateListOf<ProcessStep>() }

    // Sub-form states for lists
    var showAddExamSection by remember { mutableStateOf(false) }
    var sectionName by remember { mutableStateOf("") }
    var sectionQuestions by remember { mutableStateOf("") }
    var sectionTime by remember { mutableStateOf("") }
    var sectionLevel by remember { mutableStateOf("Medium") }

    var showAddHiringStep by remember { mutableStateOf(false) }
    var stepTitle by remember { mutableStateOf("") }
    var stepDuration by remember { mutableStateOf("") }
    
    val adminCompanies = remember {
        mutableStateListOf(
            getMockCompany("Cognizant"),
            getMockCompany("TCS"),
            getMockCompany("Blackstraw"),
            getMockCompany("Hexaware"),
            getMockCompany("Zoho"),
            getMockCompany("Accenture"),
            getMockCompany("Wipro"),
            getMockCompany("Infosys"),
            getMockCompany("HCL"),
            getMockCompany("Capgemini")
        )
    }

    val filteredCompanies = remember(searchQuery, adminCompanies.size) {
        adminCompanies.filter { 
            it.name.contains(searchQuery, ignoreCase = true) || 
            it.sector.contains(searchQuery, ignoreCase = true) 
        }
    }

    if (showAddCompanySheet) {
        ModalBottomSheet(
            onDismissRequest = { 
                showAddCompanySheet = false
                // Reset fields
                companyName = ""
                location = ""
                industry = ""
                website = ""
            },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFFE5E7EB)) }
        ) {
            AddCompanySheetContent(
                companyName = companyName,
                onCompanyNameChange = { companyName = it },
                location = location,
                onLocationChange = { location = it },
                industry = industry,
                onIndustryChange = { industry = it },
                website = website,
                onWebsiteChange = { website = it },
                description = description,
                onDescriptionChange = { description = it },
                difficulty = difficulty,
                onDifficultyChange = { difficulty = it },
                examSections = examPattern,
                hiringSteps = hiringProcess,
                onAddSection = { name, qs, time, level -> 
                    examPattern.add(ExamSection(name, qs, time, level)) 
                },
                onAddStep = { title, duration -> 
                    hiringProcess.add(ProcessStep(title, duration)) 
                },
                onCancel = { showAddCompanySheet = false },
                onAdd = {
                    if (companyName.isNotBlank()) {
                        adminCompanies.add(0, getMockCompany(companyName).copy(
                            location = location,
                            sector = industry,
                            difficulty = difficulty,
                            description = description,
                            websiteUrl = website,
                            examPattern = examPattern.toList(),
                            hiringProcess = hiringProcess.toList()
                        ))
                        showAddCompanySheet = false
                        // Reset fields
                        companyName = ""
                        location = ""
                        industry = ""
                        website = ""
                        description = ""
                        difficulty = "Medium"
                        examPattern.clear()
                        hiringProcess.clear()
                    }
                }
            )
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
                    icon = { Icon(Icons.Outlined.People, contentDescription = "Users") },
                    label = { Text("Users", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToUsers,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp)
            ) {
                // Section Title and Add Button
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Companies",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                        Button(
                            onClick = { showAddCompanySheet = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Icon(Icons.Default.Business, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add Company", fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Search Bar
                item {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search companies...", color = TextBody.copy(alpha = 0.5f)) },
                        modifier = Modifier
                            .fillMaxWidth()
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
                    Spacer(modifier = Modifier.height(32.dp))
                }

                // Company List
                items(filteredCompanies) { company ->
                    AdminCompanyItem(
                        company = company,
                        onClick = { onNavigateToCompanyDetails(company.name) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun AdminCompanyItem(company: Company, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8F9FA)
            ) {
                Image(
                    painter = painterResource(id = company.logoResId),
                    contentDescription = company.name,
                    modifier = Modifier.padding(12.dp)
                )
            }
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = company.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(14.dp), tint = TextBody)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = company.location,
                            fontSize = 14.sp,
                            color = TextBody
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.BusinessCenter, null, modifier = Modifier.size(14.dp), tint = TextBody)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = company.sector,
                            fontSize = 14.sp,
                            color = TextBody
                        )
                    }
                }
            }
            
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = TextBody)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCompanySheetContent(
    companyName: String,
    onCompanyNameChange: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    industry: String,
    onIndustryChange: (String) -> Unit,
    website: String,
    onWebsiteChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    difficulty: String,
    onDifficultyChange: (String) -> Unit,
    examSections: List<ExamSection>,
    hiringSteps: List<ProcessStep>,
    onAddSection: (String, Int, String, String) -> Unit,
    onAddStep: (String, String) -> Unit,
    onCancel: () -> Unit,
    onAdd: () -> Unit
) {
    var showSectionForm by remember { mutableStateOf(false) }
    var showStepForm by remember { mutableStateOf(false) }

    // Sub-form internal states
    var newSectionName by remember { mutableStateOf("") }
    var newSectionQs by remember { mutableStateOf("") }
    var newSectionTime by remember { mutableStateOf("") }
    var newSectionLevel by remember { mutableStateOf("Medium") }

    var newStepTitle by remember { mutableStateOf("") }
    var newStepDuration by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 40.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Add Company Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextTitle
            )
            IconButton(onClick = onCancel) {
                Icon(Icons.Default.Close, null, tint = Color(0xFF9CA3AF))
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("Basic Information", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
        Spacer(modifier = Modifier.height(16.dp))
        
        AdminInputField(label = "Company Name", value = companyName, onValueChange = onCompanyNameChange, placeholder = "e.g. Google")
        Spacer(modifier = Modifier.height(20.dp))
        AdminInputField(label = "Location", value = location, onValueChange = onLocationChange, placeholder = "e.g. Mountain View, CA")
        Spacer(modifier = Modifier.height(20.dp))
        AdminInputField(label = "Industry", value = industry, onValueChange = onIndustryChange, placeholder = "e.g. Technology")
        Spacer(modifier = Modifier.height(20.dp))
        AdminInputField(label = "Website", value = website, onValueChange = onWebsiteChange, placeholder = "https://...")
        
        Spacer(modifier = Modifier.height(32.dp))
        Text("Overview Details", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
        Spacer(modifier = Modifier.height(16.dp))

        AdminInputField(
            label = "Company Description", 
            value = description, 
            onValueChange = onDescriptionChange, 
            placeholder = "Briefly describe the company...",
            singleLine = false,
            minLines = 3
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Column {
            Text(
                "Interview Difficulty",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF475569),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Easy", "Medium", "Hard").forEach { level ->
                    val isSelected = difficulty == level
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDifficultyChange(level) },
                        color = if (isSelected) PrimaryBlue else Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            level,
                            modifier = Modifier.padding(vertical = 12.dp),
                            textAlign = TextAlign.Center,
                            color = if (isSelected) Color.White else Color(0xFF64748B),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        
        // Exam Pattern Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Exam Pattern", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            TextButton(onClick = { showSectionForm = !showSectionForm }) {
                Text(if (showSectionForm) "Done" else "+ Add Section")
            }
        }

        if (showSectionForm) {
            Surface(
                color = Color(0xFFF8FAFC),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 16.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    AdminInputField(label = "Section Name", value = newSectionName, onValueChange = { newSectionName = it }, placeholder = "e.g. Aptitude")
                    Row(modifier = Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(modifier = Modifier.weight(1f)) {
                            AdminInputField(label = "Qs", value = newSectionQs, onValueChange = { newSectionQs = it }, placeholder = "20")
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            AdminInputField(label = "Time", value = newSectionTime, onValueChange = { newSectionTime = it }, placeholder = "30m")
                        }
                    }
                    Button(
                        onClick = {
                            if (newSectionName.isNotBlank()) {
                                onAddSection(newSectionName, newSectionQs.toIntOrNull() ?: 0, newSectionTime, newSectionLevel)
                                newSectionName = ""
                                newSectionQs = ""
                                newSectionTime = ""
                                newSectionLevel = "Medium"
                                showSectionForm = false
                            }
                        },
                        modifier = Modifier.align(Alignment.End).padding(top = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        Text("Save Section", fontSize = 12.sp)
                    }
                    Text("Note: Use the 'Add Company' button to save all changes.", fontSize = 10.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 8.dp))
                }
            }
        }

        examSections.forEach { section ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color(0xFFF1F5F9),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(section.name, fontWeight = FontWeight.Medium)
                    Text("${section.questions} Qs • ${section.time}", color = Color(0xFF64748B))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hiring Process Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Hiring Process", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            TextButton(onClick = { showStepForm = !showStepForm }) {
                Text(if (showStepForm) "Done" else "+ Add Step")
            }
        }

        if (showStepForm) {
            Surface(
                color = Color(0xFFF8FAFC),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 16.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    AdminInputField(label = "Step Title", value = newStepTitle, onValueChange = { newStepTitle = it }, placeholder = "e.g. Technical Interview")
                    Spacer(modifier = Modifier.height(12.dp))
                    AdminInputField(label = "Duration", value = newStepDuration, onValueChange = { newStepDuration = it }, placeholder = "e.g. 1 Hour")
                    
                    Button(
                        onClick = {
                            if (newStepTitle.isNotBlank()) {
                                onAddStep(newStepTitle, newStepDuration)
                                newStepTitle = ""
                                newStepDuration = ""
                                showStepForm = false
                            }
                        },
                        modifier = Modifier.align(Alignment.End).padding(top = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        Text("Save Step", fontSize = 12.sp)
                    }
                }
            }
        }

        hiringSteps.forEach { step ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color(0xFFF1F5F9),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(step.title, fontWeight = FontWeight.Medium)
                    Text(step.duration, color = Color(0xFF64748B))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9))
            ) {
                Text("Cancel", color = TextTitle, fontWeight = FontWeight.Bold)
            }
            
            Button(
                onClick = onAdd,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Add Company", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column {
        Text(
            label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF475569),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xFF94A3B8)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = singleLine,
            minLines = minLines
        )
    }
}
