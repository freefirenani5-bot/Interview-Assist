package com.simats.interviewassist.ui.screens.student

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.PrimaryBlue
import com.simats.interviewassist.ui.theme.TextBody
import com.simats.interviewassist.ui.theme.TextTitle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCompanyDetailsScreen(
    companyName: String,
    onBack: () -> Unit,
    onExperienceClick: (String) -> Unit,
    onNavigateToAskQuestion: (String) -> Unit = {}
) {
    val company = remember { getMockCompany(companyName) }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Experiences", "Q&A")
    
    // State for local notifications
    var showAskQuestionSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Global Notification State
    var showGlobalNotification by remember { mutableStateOf(false) }
    var globalNotificationMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val showNotification: (String) -> Unit = { message ->
        globalNotificationMessage = message
        showGlobalNotification = true
        scope.launch {
            kotlinx.coroutines.delay(2000)
            showGlobalNotification = false
        }
    }

    if (showAskQuestionSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAskQuestionSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            AskQuestionSheet(
                companyName = companyName,
                onDismiss = { showAskQuestionSheet = false },
            onPostQuestion = { question ->
                    UserQuestionsManager.addQuestion(companyName, question)
                    showAskQuestionSheet = false
                    showNotification("Question posted successfully")
                }
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                // No standard TopAppBar, we'll use a custom one with overlay
            },
            containerColor = Color.White
        ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                // Banner Background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF9FAFB))
                )

                // Back Button
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .background(Color.White, CircleShape)
                        .size(40.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = TextTitle)
                }

                // Logo Container
                Surface(
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .align(Alignment.BottomStart)
                        .size(80.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = company.logoResId),
                            contentDescription = company.name,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                // Company Name & Info
                Text(
                    text = company.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(16.dp), tint = Color(0xFF9CA3AF))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(company.location, fontSize = 14.sp, color = Color(0xFF6B7280))
                    Spacer(modifier = Modifier.width(24.dp))
                    Icon(Icons.Default.Public, null, modifier = Modifier.size(16.dp), tint = Color(0xFF9CA3AF))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(company.sector, fontSize = 14.sp, color = Color(0xFF6B7280))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                Text(
                    text = company.description,
                    fontSize = 15.sp,
                    color = Color(0xFF6B7280),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Stats Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatBox(
                        value = "${company.experiencesCount}",
                        label = "Experiences",
                        bgColor = Color(0xFFE3F2FD), // Light Blue
                        textColor = Color(0xFF1976D2),
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        value = "${company.selectedCount}",
                        label = "Selected",
                        bgColor = Color(0xFFE8F5E9), // Light Green
                        textColor = Color(0xFF388E3C),
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        value = company.difficulty,
                        label = "Difficulty",
                        bgColor = Color(0xFFFFF3E0), // Light Orange
                        textColor = Color(0xFFF57C00),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* Open Website */ },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF4B5563))
                    ) {
                        Text("Visit Website", fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = { /* Follow */ },
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Follow Company", fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Tabs
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        tabs.forEachIndexed { index, title ->
                            val isSelected = selectedTab == index
                            Column(
                                modifier = Modifier
                                    .clickable { selectedTab = index }
                                    .padding(vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        when(index) {
                                            0 -> Icons.Default.MenuBook
                                            1 -> Icons.Default.Groups
                                            else -> Icons.Default.ChatBubbleOutline
                                        },
                                        null,
                                        modifier = Modifier.size(18.dp),
                                        tint = if (isSelected) PrimaryBlue else Color(0xFF6B7280)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = title,
                                        fontSize = 16.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) PrimaryBlue else Color(0xFF6B7280)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(2.dp)
                                            .background(PrimaryBlue)
                                    )
                                } else {
                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                            }
                        }
                    }
                    HorizontalDivider(color = Color(0xFFF3F4F6), thickness = 1.dp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tab Content
                when (selectedTab) {
                    0 -> OverviewTab(company)
                    1 -> ExperiencesTab(company, onExperienceClick, showNotification)
                    2 -> QuestionsTab(company, onAskQuestion = { showAskQuestionSheet = true })
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        } // Close the main Column inside Scaffold padding

        // Floating Notification Popup (Premium Style)
        androidx.compose.animation.AnimatedVisibility(
            visible = showGlobalNotification,
            enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.slideInVertically(initialOffsetY = { -it }),
            exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.slideOutVertically(targetOffsetY = { -it }),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Surface(
                color = Color(0xFF1F2937).copy(alpha = 0.9f), // Premium Dark
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(top = 48.dp),
                shadowElevation = 6.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (globalNotificationMessage.contains("posted")) Icons.Default.CheckCircle 
                                     else if (globalNotificationMessage.contains("saved")) Icons.Default.Bookmark
                                     else if (globalNotificationMessage.contains("reported")) Icons.Default.Flag
                                     else Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = globalNotificationMessage,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ExperiencesTab(company: Company, onExperienceClick: (String) -> Unit, onShowNotification: (String) -> Unit) {
    Column {
        Text(
            text = "${company.experiencesCount} experiences shared",
            fontSize = 14.sp,
            color = Color(0xFF6B7280),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        company.experiences.forEach { experience ->
            ExperienceCard(experience, company.name, onClick = { onExperienceClick(experience.id) }, onShowNotification = onShowNotification)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ExperienceCard(experience: InterviewExperience, companyName: String, onClick: () -> Unit, onShowNotification: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF9FAFB),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // User Info Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFE5E7EB), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = experience.userName.take(1) + experience.userName.split(" ").last().take(1),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4B5563)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = experience.userName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                        if (experience.isUserVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                modifier = Modifier.size(14.dp),
                                tint = PrimaryBlue
                            )
                        }
                    }
                    Text(
                        text = experience.userRole,
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                }

                // Difficulty Tag
                Surface(
                    color = when(experience.difficulty) {
                        "Easy" -> Color(0xFFECFDF5)
                        "Medium" -> Color(0xFFFFFBEB)
                        "Hard" -> Color(0xFFFEF2F2)
                        else -> Color(0xFFF3F4F6)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = experience.difficulty,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when(experience.difficulty) {
                            "Easy" -> Color(0xFF10B981)
                            "Medium" -> Color(0xFFD97706)
                            "Hard" -> Color(0xFFEF4444)
                            else -> Color(0xFF6B7280)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                Box {
                    var expanded by remember { mutableStateOf(false) }
                    var isSaved by remember { mutableStateOf(false) }
                    var showReportDialog by remember { mutableStateOf(false) }

                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Default.MoreVert, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(20.dp))
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text(if (isSaved) "Unsave" else "Save", color = TextTitle, fontSize = 14.sp, fontWeight = FontWeight.Medium) },
                            onClick = { 
                                isSaved = !isSaved
                                expanded = false 
                                onShowNotification(if (isSaved) "Experience saved" else "Removed from saved")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Report", color = Color(0xFFEF4444), fontSize = 14.sp, fontWeight = FontWeight.Medium) },
                            onClick = { 
                                showReportDialog = true
                                expanded = false 
                            }
                        )
                    }

                    if (showReportDialog) {
                        AlertDialog(
                            onDismissRequest = { showReportDialog = false },
                            title = { Text("Report Experience", fontWeight = FontWeight.Bold) },
                            text = { Text("Are you sure you want to report this experience? Admin will review it.", color = Color(0xFF4B5563)) },
                            confirmButton = {
                                TextButton(onClick = {
                                    showReportDialog = false
                                    onShowNotification("Experience reported to admin")
                                }) {
                                    Text("Report", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showReportDialog = false }) {
                                    Text("Cancel", color = Color(0xFF6B7280))
                                }
                            },
                            containerColor = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Interview Details
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Interview at ",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280)
                )
                Text(
                    text = companyName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                if (experience.isSelected) {
                    Surface(
                        color = Color(0xFFECFDF5),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(12.dp), tint = Color(0xFF10B981))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Selected", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                        }
                    }
                } else {
                    Surface(
                        color = Color(0xFFFEF2F2),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(Icons.Default.Cancel, null, modifier = Modifier.size(12.dp), tint = Color(0xFFEF4444))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Rejected", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Badges
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    color = Color(0xFFF3F4F6),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Computer, null, modifier = Modifier.size(12.dp), tint = Color(0xFF6B7280))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(experience.workMode, fontSize = 11.sp, color = Color(0xFF6B7280))
                    }
                }
                Surface(
                    color = Color(0xFFF3F4F6),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        experience.candidateType,
                        fontSize = 11.sp,
                        color = Color(0xFF6B7280),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content
            Text(
                text = experience.content,
                fontSize = 14.sp,
                color = Color(0xFF4B5563),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, null, modifier = Modifier.size(14.dp), tint = Color(0xFF9CA3AF))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(experience.date, fontSize = 12.sp, color = Color(0xFF9CA3AF))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val isHelpful = HelpfulExperiencesManager.isHelpful(experience.id)
                    IconButton(
                        onClick = { 
                            HelpfulExperiencesManager.toggleHelpful(experience.id)
                            onShowNotification(if (HelpfulExperiencesManager.isHelpful(experience.id)) "Marked as helpful" else "Removed from helpful")
                        },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            if (isHelpful) Icons.Default.ThumbUp else Icons.Default.ThumbUpOffAlt,
                            null,
                            modifier = Modifier.size(16.dp),
                            tint = if (isHelpful) PrimaryBlue else Color(0xFF9CA3AF)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${experience.helpfulCount + (if (isHelpful) 1 else 0)} Helpful",
                        fontSize = 12.sp,
                        color = if (isHelpful) PrimaryBlue else Color(0xFF9CA3AF),
                        fontWeight = if (isHelpful) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
fun StatBox(value: String, label: String, bgColor: Color, textColor: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(84.dp),
        shape = RoundedCornerShape(12.dp),
        color = bgColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(text = label, fontSize = 12.sp, color = textColor.copy(alpha = 0.8f), fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun OverviewTab(company: Company) {
    Column {
        Text("Exam Pattern", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextTitle)
        Spacer(modifier = Modifier.height(16.dp))
        
        // Exam Pattern Table
        ExamPatternTable(company.examPattern)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("Hiring Process", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextTitle)
        Spacer(modifier = Modifier.height(24.dp))
        
        // Hiring Process Timeline
        HiringTimeline(company.hiringProcess)
    }
}

@Composable
fun ExamPatternTable(patterns: List<ExamSection>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6)),
        color = Color.White
    ) {
        Column {
            // Header Row
            Surface(
                color = Color(0xFFF9FAFB),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Section", modifier = Modifier.weight(1.5f), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                    Text("Q", modifier = Modifier.weight(1f), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151), textAlign = TextAlign.Center)
                    Text("Time", modifier = Modifier.weight(1.5f), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151), textAlign = TextAlign.Center)
                    Text("Level", modifier = Modifier.weight(1.2f), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151), textAlign = TextAlign.End)
                }
            }
            
            patterns.forEachIndexed { index, section ->
                if (index > 0) Divider(color = Color(0xFFF3F4F6))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(section.name, modifier = Modifier.weight(1.5f), fontSize = 14.sp, color = Color(0xFF4B5563), fontWeight = FontWeight.Medium)
                    Text("${section.questions}", modifier = Modifier.weight(1f), fontSize = 14.sp, color = Color(0xFF4B5563), textAlign = TextAlign.Center)
                    Text(section.time, modifier = Modifier.weight(1.5f), fontSize = 14.sp, color = Color(0xFF4B5563), textAlign = TextAlign.Center)
                    Text(
                        section.level,
                        modifier = Modifier.weight(1.2f),
                        fontSize = 14.sp,
                        color = when(section.level) {
                            "Easy" -> Color(0xFF10B981)
                            "Medium" -> Color(0xFFF59E0B)
                            "Hard" -> Color(0xFFEF4444)
                            else -> Color(0xFF4B5563)
                        },
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun HiringTimeline(steps: List<ProcessStep>) {
    Column {
        steps.forEachIndexed { index, step ->
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(24.dp)
                ) {
                    // Dot
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(PrimaryBlue, CircleShape)
                    )
                    // Line
                    if (index < steps.size - 1) {
                         Box(
                            modifier = Modifier
                                .weight(1f) // Fill remaining space in this Row's height
                                .width(2.dp)
                                .background(Color(0xFFE5E7EB))
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.padding(bottom = 32.dp)) {
                    Text(text = step.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = step.duration, fontSize = 13.sp, color = Color(0xFF9CA3AF))
                }
            }
        }
    }
}

@Composable
fun QuestionsTab(company: Company, onAskQuestion: () -> Unit = {}) {
    val userQuestionsForCompany = UserQuestionsManager.userQuestions.filter { it.companyName == company.name }
    val allQuestions = userQuestionsForCompany + company.questions

    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(
                onClick = onAskQuestion,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ask Question", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        allQuestions.forEach { question ->
            QuestionCard(question)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Verified Alumni Info Card
        Surface(
            color = Color(0xFFEFF6FF),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.Lightbulb, null, tint = PrimaryBlue, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Questions are answered by verified alumni who have worked at this company. Use the Ask Question button to post your own question!",
                    fontSize = 14.sp,
                    color = PrimaryBlue,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun QuestionCard(question: Question) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                question.questionText,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1F2937),
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Asked by ${question.askedBy} • ${question.date}",
                fontSize = 13.sp,
                color = Color(0xFF9CA3AF)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (question.answers.isNotEmpty()) {
                question.answers.forEach { answer ->
                    Surface(
                        color = Color(0xFFF9FAFB),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(PrimaryBlue.copy(alpha = 0.1f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        answer.answererName.first().toString(),
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue,
                                        fontSize = 14.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(answer.answererName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF374151))
                                        if (answer.isVerifiedAlumni) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Surface(
                                                color = Color(0xFFDCFCE7),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    "Verified Alumni",
                                                    fontSize = 10.sp,
                                                    color = Color(0xFF166534),
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                    Text(answer.answererRole, fontSize = 12.sp, color = Color(0xFF6B7280))
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                answer.answerText,
                                fontSize = 15.sp,
                                color = Color(0xFF4B5563),
                                lineHeight = 22.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                answer.date,
                                fontSize = 12.sp,
                                color = Color(0xFF9CA3AF)
                            )
                        }
                    }
                }
            } else {
                Surface(
                    color = Color(0xFFFFFBEB),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "No answers yet. Alumni will respond soon!",
                        fontSize = 14.sp,
                        color = Color(0xFFB45309),
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
