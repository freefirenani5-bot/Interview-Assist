package com.simats.interviewassist.ui.screens.alumni

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
import com.simats.interviewassist.ui.screens.student.Company
import com.simats.interviewassist.ui.screens.student.getMockCompany
import com.simats.interviewassist.ui.screens.student.StatBox
import com.simats.interviewassist.ui.screens.student.OverviewTab
import com.simats.interviewassist.ui.screens.student.ExperiencesTab
import com.simats.interviewassist.ui.screens.student.Question
import com.simats.interviewassist.ui.screens.student.UserQuestionsManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniCompanyDetailsScreen(
    companyName: String,
    onBack: () -> Unit,
    onExperienceClick: (String) -> Unit,
    onNavigateToAskQuestion: (String) -> Unit = {}
) {
    val company = remember { getMockCompany(companyName) }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Experiences", "Q&A")

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
                        .height(200.dp)
                ) {
                    // Banner Background
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFFE0E7FF), Color.White)
                                )
                            )
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
                            .size(90.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = company.logoResId),
                                contentDescription = company.name,
                                modifier = Modifier.size(60.dp)
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
                            bgColor = Color(0xFFEEF2FF),
                            textColor = Color(0xFF4F46E5),
                            modifier = Modifier.weight(1f)
                        )
                        StatBox(
                            value = "${company.selectedCount}",
                            label = "Selected",
                            bgColor = Color(0xFFECFDF5),
                            textColor = Color(0xFF10B981),
                            modifier = Modifier.weight(1f)
                        )
                        StatBox(
                            value = company.difficulty,
                            label = "Difficulty",
                            bgColor = Color(0xFFFFFBEB),
                            textColor = Color(0xFFD97706),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Actions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TextButton(
                            onClick = { /* Open Website */ },
                            modifier = Modifier.weight(1f).height(48.dp)
                        ) {
                            Text("Visit Website", color = Color(0xFF4B5563), fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { /* Follow */ },
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Follow Company", fontWeight = FontWeight.Bold)
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
                        2 -> AlumniQuestionsTab(company)
                    }
                    
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }

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
fun AlumniQuestionsTab(company: Company) {
    val userQuestionsForCompany = UserQuestionsManager.userQuestions.filter { it.companyName == company.name }
    val allQuestions = userQuestionsForCompany + company.questions

    Column {
        allQuestions.forEach { question ->
            AlumniQuestionCard(question)
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
                    "This section displays questions asked by students and answered by verified alumni. As an alumni, your insights help others succeed!",
                    fontSize = 14.sp,
                    color = PrimaryBlue,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun AlumniQuestionCard(question: Question) {
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

            if (question.answers.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                question.answers.forEach { answer ->
                    Surface(
                        color = Color(0xFFF9FAFB),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
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
            }
        }
    }
}
