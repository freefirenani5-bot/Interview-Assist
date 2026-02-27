package com.simats.interviewassist.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.PrimaryBlue
import com.simats.interviewassist.ui.theme.TextTitle
import com.simats.interviewassist.ui.screens.student.InterviewExperience
import com.simats.interviewassist.ui.screens.student.getMockCompany
import com.simats.interviewassist.ui.screens.student.StatusBadge
import com.simats.interviewassist.ui.screens.student.ExperienceSectionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminExperienceDetailScreen(
    companyName: String,
    experienceId: String,
    onBack: () -> Unit
) {
    val company = remember { getMockCompany(companyName) }
    val experience = remember { company.experiences.find { it.id == experienceId } ?: company.experiences.first() }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .background(Color.White.copy(alpha = 0.9f), CircleShape)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Admin Action */ }) {
                        Icon(Icons.Default.BookmarkBorder, contentDescription = "Save")
                    }
                    IconButton(onClick = { /* Share action */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
        ) {
            // Profile Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color(0xFFF1F5F9), CircleShape)
                            .border(2.dp, PrimaryBlue.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        val initials = experience.userName.split(" ").let { parts ->
                            if (parts.size >= 2) "${parts[0][0]}${parts[1][0]}"
                            else parts[0].take(2).uppercase()
                        }
                        Text(
                            text = initials,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64748B)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = experience.userName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextTitle
                            )
                            if (experience.isUserVerified) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(18.dp), tint = PrimaryBlue)
                            }
                        }
                        Text(
                            text = "${experience.userRole} at $companyName",
                            color = Color(0xFF64748B),
                            fontSize = 15.sp
                        )
                    }
                }
            }
            
            // Badges Row
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatusBadge(experience.difficulty,
                    when(experience.difficulty) {
                        "Easy" -> Color(0xFFF0FDF4)
                        "Medium" -> Color(0xFFFFFBEB)
                        "Hard" -> Color(0xFFFEF2F2)
                        else -> Color(0xFFF8FAFC)
                    },
                    when(experience.difficulty) {
                        "Easy" -> Color(0xFF166534)
                        "Medium" -> Color(0xFFB45309)
                        "Hard" -> Color(0xFF991B1B)
                        else -> Color(0xFF64748B)
                    }
                )
                StatusBadge(experience.workMode, Color(0xFFF1F5F9), Color(0xFF64748B))
                StatusBadge(experience.candidateType, Color(0xFFF1F5F9), Color(0xFF64748B))
                if (experience.isSelected) {
                    StatusBadge("Selected", Color(0xFFEFF6FF), PrimaryBlue)
                }
            }

            // Time & Helpful Stats
            Row(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CalendarToday, null, modifier = Modifier.size(14.dp), tint = Color(0xFF94A3B8))
                Spacer(modifier = Modifier.width(6.dp))
                Text(experience.date, fontSize = 13.sp, color = Color(0xFF94A3B8))
                Spacer(modifier = Modifier.width(20.dp))
                Icon(Icons.Default.ThumbUpOffAlt, null, modifier = Modifier.size(16.dp), tint = Color(0xFF94A3B8))
                Spacer(modifier = Modifier.width(6.dp))
                Text("${experience.helpfulCount} found helpful", fontSize = 13.sp, color = Color(0xFF94A3B8))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                
                // About the Author
                ExperienceSectionCard(
                    title = "About the Author",
                    icon = Icons.Default.Person,
                    containerColor = Color(0xFFEFF6FF),
                    contentColor = PrimaryBlue
                ) {
                    Text(
                        text = experience.brief,
                        fontSize = 16.sp,
                        color = Color(0xFF1E40AF),
                        lineHeight = 24.sp
                    )
                }

                // How I got the interview
                ExperienceSectionCard(
                    title = "How I Got the Interview",
                    icon = Icons.Default.Bolt,
                    containerColor = Color(0xFFFAF5FF),
                    contentColor = Color(0xFF9333EA)
                ) {
                    Text(
                        text = experience.applicationProcess,
                        fontSize = 16.sp,
                        color = Color(0xFF581C87),
                        lineHeight = 24.sp
                    )
                }

                // Interview Process Breakdown
                ExperienceSectionCard(
                    title = "Interview Process Breakdown",
                    icon = Icons.Default.Timeline,
                    containerColor = Color(0xFFEEF2FF),
                    contentColor = Color(0xFF4F46E5)
                ) {
                    experience.interviewRounds.forEachIndexed { index, round ->
                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    "• ",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4F46E5),
                                    fontSize = 18.sp
                                )
                                Column {
                                    Text(
                                        text = round.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF312E81)
                                    )
                                    Text(
                                        text = round.duration,
                                        fontSize = 14.sp,
                                        color = Color(0xFF4338CA)
                                    )
                                }
                            }
                        }
                    }
                }
                
                // My Experience (Detailed)
                ExperienceSectionCard(
                    title = "My Interview Experience",
                    icon = Icons.Default.Article,
                    containerColor = Color(0xFFF8FAFC),
                    contentColor = Color(0xFF334155)
                ) {
                    Text(
                        text = experience.myExperience,
                        fontSize = 16.sp,
                        color = Color(0xFF1E293B),
                        lineHeight = 26.sp
                    )
                }

                // Questions Asked
                ExperienceSectionCard(
                    title = "Questions Asked",
                    icon = Icons.Default.HelpOutline,
                    containerColor = Color(0xFFFFF7ED),
                    contentColor = Color(0xFFEA580C)
                ) {
                    if (experience.technicalQuestions.isNotEmpty()) {
                        Text(
                            "Technical Questions",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9A3412),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        experience.technicalQuestions.forEach {
                            Text("• $it", fontSize = 15.sp, color = Color(0xFF7C2D12), modifier = Modifier.padding(bottom = 6.dp))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    if (experience.behavioralQuestions.isNotEmpty()) {
                        Text(
                            "Behavioral Questions",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9A3412),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        experience.behavioralQuestions.forEach {
                            Text("• $it", fontSize = 15.sp, color = Color(0xFF7C2D12), modifier = Modifier.padding(bottom = 6.dp))
                        }
                    }
                }

                // Mistakes Made
                ExperienceSectionCard(
                    title = "Mistakes I Made",
                    icon = Icons.Default.Warning,
                    containerColor = Color(0xFFFEF2F2),
                    contentColor = Color(0xFFDC2626)
                ) {
                    experience.mistakes.forEachIndexed { index, mistake ->
                         Row(modifier = Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.Top) {
                            Text("${index + 1}. ", fontWeight = FontWeight.Bold, color = Color(0xFFB91C1C), fontSize = 15.sp)
                            Text(mistake, fontSize = 15.sp, color = Color(0xFF7F1D1D), lineHeight = 22.sp)
                        }
                    }
                }

                // Preparation Strategy
                ExperienceSectionCard(
                    title = "Preparation Strategy",
                    icon = Icons.Default.School,
                    containerColor = Color(0xFFECFDF5),
                    contentColor = Color(0xFF059669)
                ) {
                    experience.preparationStrategy.forEach { (category, tools) ->
                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                            Text(category, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF065F46))
                            tools.forEach { tool ->
                                Text("• $tool", fontSize = 15.sp, color = Color(0xFF064E3B), modifier = Modifier.padding(start = 12.dp, top = 4.dp))
                            }
                        }
                    }
                }

                // Final Advice
                ExperienceSectionCard(
                    title = "Final Advice & Conclusion",
                    icon = Icons.Default.VolunteerActivism,
                    containerColor = Color(0xFFF5F3FF),
                    contentColor = Color(0xFF7C3AED)
                ) {
                    experience.finalAdvice.forEachIndexed { index, advice ->
                        Row(modifier = Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.Top) {
                            Text("${index + 1}. ", fontWeight = FontWeight.Bold, color = Color(0xFF6D28D9), fontSize = 15.sp)
                            Text(advice, fontSize = 15.sp, color = Color(0xFF4C1D95), lineHeight = 22.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Good Luck! You've got this! \uD83D\uDCAA", fontWeight = FontWeight.Bold, color = Color(0xFF5B21B6), fontSize = 16.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
