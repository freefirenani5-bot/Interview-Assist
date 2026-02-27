package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import com.simats.interviewassist.ui.theme.*
import java.util.*
import android.app.DatePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniShareExperienceScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(1) }
    
    // Step 1 State
    var companyName by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var interviewDate by remember { mutableStateOf("") }
    var interviewMode by remember { mutableStateOf("") }
    var experienceLevel by remember { mutableStateOf("") }
    var outcome by remember { mutableStateOf("") }

    // Step 2 State
    var difficulty by remember { mutableStateOf("Medium") }
    var background by remember { mutableStateOf("") }
    var howGotInterview by remember { mutableStateOf("") }
    var processBreakdown by remember { mutableStateOf("") }
    var questionsAsked by remember { mutableStateOf("") }
    var mistakesMade by remember { mutableStateOf("") }
    var prepStrategy by remember { mutableStateOf("") }
    var finalAdvice by remember { mutableStateOf("") }
    var myExperience by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(top = 12.dp, bottom = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    IconButton(onClick = {
                        if (currentStep > 1) currentStep-- else onBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextTitle)
                    }
                    Column(modifier = Modifier.padding(start = 4.dp)) {
                        Text(
                            "Share Experience",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                        Text(
                            "Help juniors prepare better",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Stepper
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StepItem(number = 1, label = "Company", isActive = currentStep == 1, isCompleted = currentStep > 1)
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(if (currentStep > 1) PrimaryBlue else Color(0xFFF3F4F6)))
                    StepItem(number = 2, label = "Details", isActive = currentStep == 2, isCompleted = currentStep > 2)
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(if (currentStep > 2) PrimaryBlue else Color(0xFFF3F4F6)))
                    StepItem(number = 3, label = "Review", isActive = currentStep == 3, isCompleted = currentStep > 3)
                }
            }
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .navigationBarsPadding()
        ) {
            if (currentStep == 1) {
                // Step 1: Company Information
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFEFF6FF),
                                modifier = Modifier.size(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Outlined.Apartment, null, tint = PrimaryBlue)
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Company Information", fontWeight = FontWeight.Bold, color = TextTitle, fontSize = 16.sp)
                                Text("Basic details about the interview", fontSize = 12.sp, color = Color(0xFF6B7280))
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        LabelledTextField(
                            label = "Company Name",
                            value = companyName,
                            onValueChange = { companyName = it },
                            placeholder = "e.g. Google, Microsoft, Amazon"
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        LabelledTextField(
                            label = "Role Interviewed For",
                            value = role,
                            onValueChange = { role = it },
                            placeholder = "e.g. Software Engineer, Product Manager"
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        val context = LocalContext.current
                        val calendar = Calendar.getInstance()
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                interviewDate = "${month + 1}/$dayOfMonth/$year"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )

                        LabelledTextField(
                            label = "Interview Date",
                            value = interviewDate,
                            onValueChange = { interviewDate = it },
                            placeholder = "mm/dd/yyyy",
                            trailingIcon = { 
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Icon(Icons.Outlined.CalendarToday, null, tint = TextTitle, modifier = Modifier.size(20.dp))
                                }
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF9FAFB),
                                modifier = Modifier.size(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Outlined.HelpOutline, null, tint = Color(0xFF6B7280))
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Additional Details", fontWeight = FontWeight.Bold, color = TextTitle, fontSize = 16.sp)
                                Text("Optional but helpful for students", fontSize = 12.sp, color = Color(0xFF6B7280))
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        LabelledDropdown(
                            label = "Interview Mode",
                            value = interviewMode,
                            placeholder = "Select mode...",
                            options = listOf("On-campus", "Off-campus", "Virtual"),
                            onSelected = { interviewMode = it }
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        LabelledDropdown(
                            label = "Experience Level",
                            value = experienceLevel,
                            placeholder = "Select level...",
                            options = listOf("Internship", "Entry Level", "Mid Level", "Senior"),
                            onSelected = { experienceLevel = it }
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        LabelledDropdown(
                            label = "Outcome",
                            value = outcome,
                            placeholder = "Select outcome...",
                            options = listOf("Selected", "Waitlisted", "Not Selected"),
                            onSelected = { outcome = it }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = { currentStep = 2 },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Continue to Details", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            } else if (currentStep == 2) {
                // Step 2: Experience Details
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFEFF6FF),
                                modifier = Modifier.size(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.AutoAwesome, null, tint = Color(0xFF6366F1), modifier = Modifier.size(24.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Write Your Story", fontWeight = FontWeight.Bold, color = TextTitle, fontSize = 16.sp)
                                Text("Share your experience like a blog post. The more detail you provide, the more it helps juniors.", fontSize = 12.sp, color = Color(0xFF6B7280))
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Overall Difficulty Level", fontWeight = FontWeight.SemiBold, color = TextTitle, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            DifficultyChip("Easy", difficulty == "Easy") { difficulty = "Easy" }
                            DifficultyChip("Medium", difficulty == "Medium") { difficulty = "Medium" }
                            DifficultyChip("Hard", difficulty == "Hard") { difficulty = "Hard" }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Expandable Sections
                ExpandableSection(title = "Your Background", value = background, onValueChange = { background = it })
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableSection(title = "How I Got the Interview", value = howGotInterview, onValueChange = { howGotInterview = it })
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableSection(title = "Interview Process Breakdown", value = processBreakdown, onValueChange = { processBreakdown = it }, tag = "Important", tagColor = Color(0xFFFFF7ED), tagTextColor = Color(0xFFC2410C))
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableSection(title = "Questions Asked", value = questionsAsked, onValueChange = { questionsAsked = it })
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableSection(title = "Mistakes I Made", value = mistakesMade, onValueChange = { mistakesMade = it }, tag = "Most Helpful", tagColor = Color(0xFFF0FDF4), tagTextColor = Color(0xFF15803D))
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableSection(title = "Preparation Strategy", value = prepStrategy, onValueChange = { prepStrategy = it })
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableSection(title = "Final Advice to Juniors", value = finalAdvice, onValueChange = { finalAdvice = it })
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableSection(title = "My Experience", value = myExperience, onValueChange = { myExperience = it })
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { currentStep = 1 },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Back", color = TextTitle, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = { currentStep = 3 },
                        modifier = Modifier.weight(1.5f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Review & Submit", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            } else if (currentStep == 3) {
                // Step 3: Review & Submit
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFF0FDF4)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFDCFCE7),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF15803D))
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Almost Done!", fontWeight = FontWeight.Bold, color = Color(0xFF166534), fontSize = 18.sp)
                            Text(
                                "Your experience will be reviewed by admins before going live. This usually takes 24-48 hours.",
                                fontSize = 12.sp,
                                color = Color(0xFF15803D)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Preview Summary", fontWeight = FontWeight.Bold, color = TextTitle, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        SummaryRow("Company", companyName.ifEmpty { "---" })
                        SummaryRow("Role", role.ifEmpty { "---" })
                        SummaryRow("Difficulty", difficulty, isChip = true)
                        SummaryRow("Outcome", outcome.ifEmpty { "---" })
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text("Sections completed:", fontSize = 14.sp, color = Color(0xFF6B7280))
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        val completedSections = mutableListOf<String>()
                        if (background.isNotEmpty()) completedSections.add("Background")
                        if (howGotInterview.isNotEmpty()) completedSections.add("Process")
                        if (questionsAsked.isNotEmpty()) completedSections.add("Questions")
                        if (mistakesMade.isNotEmpty()) completedSections.add("Mistakes")
                        if (prepStrategy.isNotEmpty()) completedSections.add("Prep Strategy")
                        if (finalAdvice.isNotEmpty()) completedSections.add("Advice")
                        
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp
                        ) {
                            completedSections.forEach { section ->
                                Surface(
                                    color = Color(0xFFF3F4F6),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        section,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        fontSize = 12.sp,
                                        color = Color(0xFF4B5563)
                                    )
                                }
                            }
                            if (completedSections.isEmpty()) {
                                Text("No detailed sections completed", fontSize = 12.sp, color = Color.LightGray)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    "By submitting, you confirm that this is a genuine interview experience and follows our community guidelines. Your contribution helps thousands of students prepare better.",
                    fontSize = 12.sp,
                    color = Color(0xFF9CA3AF),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { currentStep = 2 },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Back", color = TextTitle, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = { currentStep = 4 }, // Moving to success step
                        modifier = Modifier.weight(1.5f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Submit Experience", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            } else if (currentStep == 4) {
                // Success Step
                Column(
                    modifier = Modifier.fillMaxSize().padding(top = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFDCFCE7),
                        modifier = Modifier.size(100.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Check, null, tint = Color(0xFF15803D), modifier = Modifier.size(50.dp))
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Text(
                        "Success!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        "Your experience will be made live once the admin review and accept",
                        fontSize = 16.sp,
                        color = Color(0xFF6B7280),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    Button(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Back to Dashboard", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StepItem(number: Int, label: String, isActive: Boolean, isCompleted: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = if (isActive || isCompleted) PrimaryBlue else Color(0xFFF3F4F6),
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number.toString(),
                    color = if (isActive || isCompleted) Color.White else Color(0xFF9CA3AF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            fontSize = 10.sp,
            color = if (isActive) PrimaryBlue else Color(0xFF9CA3AF),
            fontWeight = if (isActive) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextTitle,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color(0xFFE5E7EB), fontSize = 16.sp) },
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = Color(0xFFF3F4F6),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelledDropdown(
    label: String,
    value: String,
    placeholder: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextTitle,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (value.isEmpty()) placeholder else value,
                        color = if (value.isEmpty()) Color(0xFFD1D5DB) else TextTitle,
                        fontSize = 16.sp
                    )
                    Icon(
                        if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        null,
                        tint = Color(0xFF6B7280)
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onSelected(selectionOption)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultyChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = when (text) {
        "Easy" -> if (isSelected) Color(0xFFF0FDF4) else Color.White
        "Medium" -> if (isSelected) Color(0xFFFFFBEB) else Color.White
        "Hard" -> if (isSelected) Color(0xFFFEF2F2) else Color.White
        else -> Color.White
    }
    val borderColor = when (text) {
        "Easy" -> if (isSelected) Color(0xFF4ADE80) else Color(0xFFF3F4F6)
        "Medium" -> if (isSelected) Color(0xFFFBBF24) else Color(0xFFF3F4F6)
        "Hard" -> if (isSelected) Color(0xFFF87171) else Color(0xFFF3F4F6)
        else -> Color(0xFFF3F4F6)
    }
    val textColor = when (text) {
        "Easy" -> if (isSelected) Color(0xFF15803D) else Color(0xFF9CA3AF)
        "Medium" -> if (isSelected) Color(0xFFB45309) else Color(0xFF9CA3AF)
        "Hard" -> if (isSelected) Color(0xFFB91C1C) else Color(0xFF9CA3AF)
        else -> Color(0xFF9CA3AF)
    }

    Surface(
        onClick = onClick,
        modifier = Modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        color = backgroundColor
    ) {
        Box(modifier = Modifier.padding(horizontal = 24.dp), contentAlignment = Alignment.Center) {
            Text(text, color = textColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun ExpandableSection(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    tag: String? = null,
    tagColor: Color = Color.Transparent,
    tagTextColor: Color = Color.Transparent
) {
    var isExpanded by remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF9FAFB),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        val icon = when (title) {
                            "Your Background" -> Icons.Outlined.Book
                            "How I Got the Interview" -> Icons.Outlined.Search
                            "Interview Process Breakdown" -> Icons.Outlined.School
                            "Questions Asked" -> Icons.Outlined.HelpOutline
                            "Mistakes I Made" -> Icons.Outlined.WarningAmber
                            "Preparation Strategy" -> Icons.Outlined.Lightbulb
                            "Final Advice to Juniors" -> Icons.Outlined.FavoriteBorder
                            "My Experience" -> Icons.Outlined.Create
                            else -> Icons.Outlined.HelpOutline
                        }
                        Icon(icon, null, tint = if (isExpanded) PrimaryBlue else Color(0xFF9CA3AF), modifier = Modifier.size(20.dp))
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    color = if (isExpanded) PrimaryBlue else TextTitle,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )
                if (tag != null) {
                    Surface(
                        color = tagColor,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            tag,
                            color = tagTextColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Icon(
                    if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    null,
                    tint = Color(0xFF9CA3AF)
                )
            }
            
            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    placeholder = { Text("Write here...", color = Color(0xFFE5E7EB)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = Color(0xFFF3F4F6),
                        unfocusedContainerColor = Color(0xFFFBFBFB),
                        focusedContainerColor = Color(0xFFFBFBFB)
                    )
                )
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, isChip: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp, color = Color(0xFF6B7280))
        if (isChip) {
            val bgColor = when (value) {
                "Easy" -> Color(0xFFF0FDF4)
                "Medium" -> Color(0xFFFFFBEB)
                "Hard" -> Color(0xFFFEF2F2)
                else -> Color(0xFFF3F4F6)
            }
            val textColor = when (value) {
                "Easy" -> Color(0xFF15803D)
                "Medium" -> Color(0xFFB45309)
                "Hard" -> Color(0xFFB91C1C)
                else -> Color(0xFF4B5563)
            }
            Surface(
                color = bgColor,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    value,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        } else {
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    crossAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    content: @Composable () -> Unit
) {
    androidx.compose.ui.layout.Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
        val spacingPx = mainAxisSpacing.roundToPx()
        val crossSpacingPx = crossAxisSpacing.roundToPx()
        
        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0
        
        placeables.forEach { placeable ->
            if (currentRowWidth + placeable.width + (if (currentRow.isEmpty()) 0 else spacingPx) > constraints.maxWidth) {
                rows.add(currentRow)
                currentRow = mutableListOf(placeable)
                currentRowWidth = placeable.width
            } else {
                currentRowWidth += placeable.width + (if (currentRow.isEmpty()) 0 else spacingPx)
                currentRow.add(placeable)
            }
        }
        rows.add(currentRow)
        
        val totalHeight = rows.sumOf { it.maxOfOrNull { p -> p.height } ?: 0 } + (rows.size - 1) * crossSpacingPx
        
        layout(constraints.maxWidth, if (totalHeight > 0) totalHeight else 0) {
            var yOffset = 0
            rows.forEach { row ->
                var xOffset = 0
                val rowHeight = row.maxOfOrNull { it.height } ?: 0
                row.forEach { placeable ->
                    placeable.placeRelative(xOffset, yOffset)
                    xOffset += placeable.width + spacingPx
                }
                yOffset += rowHeight + crossSpacingPx
            }
        }
    }
}
