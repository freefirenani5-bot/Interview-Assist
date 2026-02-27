package com.simats.interviewassist.ui.screens.admin

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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.simats.interviewassist.ui.theme.PrimaryBlue
import com.simats.interviewassist.ui.theme.TextBody
import com.simats.interviewassist.ui.theme.TextTitle
import com.simats.interviewassist.ui.screens.student.Company
import com.simats.interviewassist.ui.screens.student.getMockCompany
import com.simats.interviewassist.ui.screens.student.ExamSection
import com.simats.interviewassist.ui.screens.student.ProcessStep
import com.simats.interviewassist.ui.screens.student.StatBox
import com.simats.interviewassist.ui.screens.student.InterviewExperience
import com.simats.interviewassist.ui.screens.student.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCompanyDetailsScreen(
    companyName: String,
    onBack: () -> Unit,
    onNavigateToExperienceDetail: (String) -> Unit
) {
    val company = remember { getMockCompany(companyName) }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Experiences", "Q&A")

    Scaffold(
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section (matches reference image)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                // Banner Background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color(0xFFF1F5F9))
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
                        .size(100.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 8.dp
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
                Spacer(modifier = Modifier.height(24.dp))

                // Company Name & Info
                Text(
                    text = company.name,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(18.dp), tint = Color(0xFF64748B))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(company.location, fontSize = 15.sp, color = Color(0xFF64748B))
                    Spacer(modifier = Modifier.width(28.dp))
                    Icon(Icons.Default.Public, null, modifier = Modifier.size(18.dp), tint = Color(0xFF64748B))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(company.sector, fontSize = 15.sp, color = Color(0xFF64748B))
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Description
                Text(
                    text = company.description,
                    fontSize = 16.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Stats Cards (matches reference image colors)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatBox(
                        value = "${company.experiencesCount}",
                        label = "Experiences",
                        bgColor = Color(0xFFEFF6FF),
                        textColor = PrimaryBlue,
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        value = "${company.selectedCount}",
                        label = "Selected",
                        bgColor = Color(0xFFF0FDF4),
                        textColor = Color(0xFF166534),
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        value = company.difficulty,
                        label = "Difficulty",
                        bgColor = Color(0xFFFFFBEB),
                        textColor = Color(0xFFB45309),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextButton(
                        onClick = { /* Admin Logic */ },
                        modifier = Modifier.weight(1f).height(54.dp)
                    ) {
                        Text("Visit Website", color = Color(0xFF475569), fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { /* Admin Logic */ },
                        modifier = Modifier.weight(1f).height(54.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        Text("Follow Company", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Experiences Count (Only for Experiences Tab)
                if (selectedTab == 1) {
                    Text(
                        text = "${company.experiencesCount} experiences shared",
                        fontSize = 14.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = PrimaryBlue,
                    edgePadding = 0.dp,
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = PrimaryBlue,
                            height = 3.dp
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        when(index) {
                                            0 -> Icons.Outlined.Book
                                            1 -> Icons.Outlined.People
                                            else -> Icons.Outlined.QuestionAnswer
                                        },
                                        null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium)
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Content Based on Tab
                when (selectedTab) {
                    0 -> OverviewTab(company)
                    1 -> ExperiencesTab(company, onNavigateToExperienceDetail)
                    2 -> AdminQuestionsTab(company)
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun OverviewTab(company: Company) {
    Column {
        Text(
            "Exam Pattern",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextTitle
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        ExamPatternTable(company.examPattern)
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            "Hiring Process",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextTitle
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        AdminHiringTimeline(company.hiringProcess)
    }
}

@Composable
fun ExamPatternTable(sections: List<ExamSection>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF8FAFC))
    ) {
        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F5F9))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Section", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF64748B))
            Text("Q", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF64748B), textAlign = TextAlign.Center)
            Text("Time", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF64748B), textAlign = TextAlign.Center)
            Text("Level", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF64748B), textAlign = TextAlign.Center)
        }
        
        sections.forEach { section ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(section.name, modifier = Modifier.weight(2f), fontWeight = FontWeight.Medium, color = Color(0xFF475569))
                Text("${section.questions}", modifier = Modifier.weight(1f), color = Color(0xFF475569), textAlign = TextAlign.Center)
                Text(section.time, modifier = Modifier.weight(1f), color = Color(0xFF475569), textAlign = TextAlign.Center)
                Text(section.level, modifier = Modifier.weight(1f), color = Color(0xFF475569), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun AdminHiringTimeline(steps: List<ProcessStep>) {
    Column {
        steps.forEachIndexed { index, step ->
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(PrimaryBlue, CircleShape)
                    )
                    if (index < steps.size - 1) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .width(2.dp)
                                .background(Color(0xFFE2E8F0))
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.padding(bottom = 32.dp)) {
                    Text(step.title, fontWeight = FontWeight.Bold, color = TextTitle, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Usually takes ${step.duration}", color = Color(0xFF64748B), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun ExperiencesTab(company: Company, onExperienceClick: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        company.experiences.forEach { experience ->
            AdminExperienceCard(experience, company.name, onClick = { onExperienceClick(experience.id) })
        }
    }
}

@Composable
fun AdminExperienceCard(experience: InterviewExperience, companyName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // User Info Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar with Initials
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(0xFFF1F5F9), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    val initials = experience.userName.split(" ").let { parts ->
                        if (parts.size >= 2) "${parts[0][0]}${parts[1][0]}"
                        else parts[0].take(2).uppercase()
                    }
                    Text(
                        text = initials,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64748B)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = experience.userName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                        if (experience.isUserVerified) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                modifier = Modifier.size(16.dp),
                                tint = PrimaryBlue
                            )
                        }
                    }
                    Text(
                        text = experience.userRole,
                        fontSize = 13.sp,
                        color = Color(0xFF64748B)
                    )
                }

                // Difficulty Tag
                Surface(
                    color = when(experience.difficulty) {
                        "Easy" -> Color(0xFFF0FDF4)
                        "Medium" -> Color(0xFFFFFBEB)
                        "Hard" -> Color(0xFFFEF2F2)
                        else -> Color(0xFFF8FAFC)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = experience.difficulty,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = when(experience.difficulty) {
                            "Easy" -> Color(0xFF166534)
                            "Medium" -> Color(0xFFB45309)
                            "Hard" -> Color(0xFF991B1B)
                            else -> Color(0xFF64748B)
                        },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.MoreVert, null, tint = Color(0xFF94A3B8))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Interview Context
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Interview at ", fontSize = 15.sp, color = Color(0xFF64748B))
                Text(companyName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextTitle)
                Spacer(modifier = Modifier.width(12.dp))
                
                Surface(
                    color = if (experience.isSelected) Color(0xFFEFF6FF) else Color(0xFFFEF2F2),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (experience.isSelected) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            null,
                            modifier = Modifier.size(12.dp),
                            tint = if (experience.isSelected) PrimaryBlue else Color(0xFFEF4444)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            if (experience.isSelected) "Selected" else "Rejected",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (experience.isSelected) PrimaryBlue else Color(0xFFEF4444)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Badges
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AdminBadge(icon = Icons.Default.Computer, text = experience.workMode)
                AdminBadge(text = experience.candidateType)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Experience Snippet
            Text(
                text = experience.content.take(150) + if (experience.content.length > 150) "..." else "",
                fontSize = 15.sp,
                color = Color(0xFF475569),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, null, modifier = Modifier.size(16.dp), tint = Color(0xFF94A3B8))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(experience.date, fontSize = 13.sp, color = Color(0xFF94A3B8))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ThumbUpOffAlt, null, modifier = Modifier.size(18.dp), tint = Color(0xFF94A3B8))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${experience.helpfulCount} Helpful", fontSize = 13.sp, color = Color(0xFF94A3B8))
                }
            }
        }
    }
}

@Composable
fun AdminQuestionsTab(company: Company) {
    Column {
        company.questions.forEach { question ->
            AdminQuestionCard(question)
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Verified Alumni Info Card
        Surface(
            color = Color(0xFFF0F9FF),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBAE6FD)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.Lightbulb, null, tint = Color(0xFF0284C7), modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Questions are answered by verified alumni who have worked at this company. Use the Ask Question button to post your own question!",
                    fontSize = 14.sp,
                    color = Color(0xFF0369A1),
                    lineHeight = 22.sp
                )
            }
        }
    }
}

@Composable
fun AdminQuestionCard(question: Question) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = question.questionText,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = TextTitle,
                lineHeight = 26.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Asked by ${question.askedBy} • ${question.date}",
                fontSize = 13.sp,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (question.answers.isNotEmpty()) {
                question.answers.forEach { answer ->
                    AdminAnswerCard(answer)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            } else {
                Surface(
                    color = Color(0xFFFFFBEB),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "No answers yet. Alumni will respond soon!",
                        fontSize = 14.sp,
                        color = Color(0xFFB45309),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun AdminAnswerCard(answer: com.simats.interviewassist.ui.screens.student.Answer) {
    Surface(
        color = Color(0xFFF8FAFC),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F5F9)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Answerer Icon/Initials
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFEFF6FF), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = answer.answererName.first().toString().uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = answer.answererName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextTitle
                        )
                        if (answer.isVerifiedAlumni) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = Color(0xFFDCFCE7),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    "Verified Alumni",
                                    fontSize = 10.sp,
                                    color = Color(0xFF166534),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                    Text(text = answer.answererRole, fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = answer.answerText,
                fontSize = 15.sp,
                color = Color(0xFF475569),
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = answer.date,
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
            )
        }
    }
}

@Composable
fun AdminBadge(icon: androidx.compose.ui.graphics.vector.ImageVector? = null, text: String) {
    Surface(
        color = Color(0xFFF1F5F9),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(icon, null, modifier = Modifier.size(14.dp), tint = Color(0xFF64748B))
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(text, fontSize = 12.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
        }
    }
}
