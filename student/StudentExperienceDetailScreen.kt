package com.simats.interviewassist.ui.screens.student

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
import androidx.compose.animation.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.PrimaryBlue
import com.simats.interviewassist.ui.theme.TextTitle
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentExperienceDetailScreen(
    companyName: String,
    experienceId: String,
    onBack: () -> Unit
) {
    val company = remember { getMockCompany(companyName) }
    val experience = remember { company.experiences.find { it.id == experienceId } ?: company.experiences.first() }
    val scrollState = rememberScrollState()
    
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
                    val isSaved = SavedExperiencesManager.isSaved(experience.id)
                    IconButton(onClick = { SavedExperiencesManager.toggleSave(experience, companyName) }) {
                        Icon(
                            if (isSaved) Icons.Filled.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save",
                            tint = if (isSaved) PrimaryBlue else LocalContentColor.current
                        )
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
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color(0xFFE5E7EB), CircleShape)
                            .border(2.dp, PrimaryBlue.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = experience.userName.take(1) + experience.userName.split(" ").last().take(1),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4B5563)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = experience.userName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextTitle
                            )
                            if (experience.isUserVerified) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(16.dp), tint = PrimaryBlue)
                            }
                        }
                        Text(
                            text = "${experience.userRole} at $companyName",
                            color = Color(0xFF6B7280),
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            // Badges Row - Clean and minimal
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusBadge(experience.difficulty,
                    when(experience.difficulty) {
                        "Easy" -> Color(0xFFECFDF5)
                        "Medium" -> Color(0xFFFFFBEB)
                        "Hard" -> Color(0xFFFEF2F2)
                        else -> Color(0xFFF3F4F6)
                    },
                    when(experience.difficulty) {
                        "Easy" -> Color(0xFF10B981)
                        "Medium" -> Color(0xFFD97706)
                        "Hard" -> Color(0xFFEF4444)
                        else -> Color(0xFF6B7280)
                    }
                )
                StatusBadge(experience.workMode, Color(0xFFF3F4F6), Color(0xFF6B7280))
                StatusBadge(experience.candidateType, Color(0xFFF3F4F6), Color(0xFF6B7280))
                if (experience.isSelected) {
                    StatusBadge("Selected", Color(0xFFECFDF5), Color(0xFF10B981))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                
                // About the Author - Blue Card
                ExperienceSectionCard(
                    title = "About the Author",
                    icon = Icons.Default.Person,
                    containerColor = Color(0xFFEFF6FF), // Light Blue
                    contentColor = PrimaryBlue
                ) {
                    Text(
                        text = experience.brief,
                        fontSize = 15.sp,
                        color = Color(0xFF1E3A8A), // Darker Blue
                        lineHeight = 22.sp
                    )
                }

                // How I got the interview - Purple Card
                ExperienceSectionCard(
                    title = "How I got the interview",
                    icon = Icons.Default.Bolt,
                    containerColor = Color(0xFFFAF5FF), // Light Purple
                    contentColor = Color(0xFF9333EA)
                ) {
                    Text(
                        text = experience.applicationProcess,
                        fontSize = 15.sp,
                        color = Color(0xFF581C87), // Darker Purple
                        lineHeight = 22.sp
                    )
                }

                // Interview Process Breakdown - Indigo Card
                ExperienceSectionCard(
                    title = "Interview Process Breakdown",
                    icon = Icons.Default.Timeline,
                    containerColor = Color(0xFFEEF2FF), // Light Indigo
                    contentColor = Color(0xFF4F46E5)
                ) {
                    experience.interviewRounds.forEachIndexed { index, round ->
                        Row(modifier = Modifier.padding(bottom = 12.dp), verticalAlignment = Alignment.Top) {
                            Text(
                                "• ",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4F46E5),
                                fontSize = 16.sp
                            )
                            Column {
                                Text(
                                    text = round.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF312E81)
                                )
                                Text(
                                    text = round.duration,
                                    fontSize = 13.sp,
                                    color = Color(0xFF4338CA)
                                )
                            }
                        }
                    }
                }

                // My Experience (Narrative) - Gray Card (distinct from others)
                 ExperienceSectionCard(
                    title = "My Experience (Detailed)",
                    icon = Icons.Default.Article,
                    containerColor = Color(0xFFF9FAFB),
                    contentColor = Color(0xFF374151)
                ) {
                    Text(
                        text = experience.myExperience,
                        fontSize = 15.sp,
                        color = Color(0xFF1F2937),
                        lineHeight = 24.sp
                    )
                }

                // Questions Asked - Orange Card
                ExperienceSectionCard(
                    title = "Questions Asked",
                    icon = Icons.Default.HelpOutline,
                    containerColor = Color(0xFFFFF7ED), // Light Orange
                    contentColor = Color(0xFFEA580C)
                ) {
                    if (experience.technicalQuestions.isNotEmpty()) {
                        Text(
                            "Technical Questions",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9A3412),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        experience.technicalQuestions.forEach {
                            Text("• $it", fontSize = 14.sp, color = Color(0xFF7C2D12), modifier = Modifier.padding(bottom = 4.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    
                    if (experience.behavioralQuestions.isNotEmpty()) {
                        Text(
                            "Behavioral Questions",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9A3412),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        experience.behavioralQuestions.forEach {
                            Text("• $it", fontSize = 14.sp, color = Color(0xFF7C2D12), modifier = Modifier.padding(bottom = 4.dp))
                        }
                    }
                }

                // Mistakes Made - Red Card
                ExperienceSectionCard(
                    title = "Mistakes I Made",
                    icon = Icons.Default.Warning,
                    containerColor = Color(0xFFFEF2F2), // Light Red
                    contentColor = Color(0xFFDC2626)
                ) {
                    experience.mistakes.forEachIndexed { index, mistake ->
                         Row(modifier = Modifier.padding(bottom = 6.dp), verticalAlignment = Alignment.Top) {
                            Text("${index + 1}. ", fontWeight = FontWeight.Bold, color = Color(0xFFB91C1C), fontSize = 14.sp)
                            Text(mistake, fontSize = 14.sp, color = Color(0xFF7F1D1D), lineHeight = 20.sp)
                        }
                    }
                }

                // Preparation Strategy - Green Card
                ExperienceSectionCard(
                    title = "Preparation Strategy",
                    icon = Icons.Default.School,
                    containerColor = Color(0xFFECFDF5), // Light Green
                    contentColor = Color(0xFF059669)
                ) {
                    experience.preparationStrategy.forEach { (category, tools) ->
                        Column(modifier = Modifier.padding(bottom = 12.dp)) {
                            Text(category, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF065F46))
                            tools.forEach { tool ->
                                Text("• $tool", fontSize = 14.sp, color = Color(0xFF064E3B), modifier = Modifier.padding(start = 8.dp, top = 2.dp))
                            }
                        }
                    }
                }

                // Final Advice - Violet Card
                ExperienceSectionCard(
                    title = "Final Advice & Conclusion",
                    icon = Icons.Default.VolunteerActivism, // Heart/Hand icon
                    containerColor = Color(0xFFF5F3FF), // Light Violet
                    contentColor = Color(0xFF7C3AED)
                ) {
                    experience.finalAdvice.forEachIndexed { index, advice ->
                        Row(modifier = Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.Top) {
                            Text("${index + 1}. ", fontWeight = FontWeight.Bold, color = Color(0xFF6D28D9), fontSize = 14.sp)
                            Text(advice, fontSize = 14.sp, color = Color(0xFF4C1D95), lineHeight = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Good Luck! You've got this! \uD83D\uDCAA", fontWeight = FontWeight.Bold, color = Color(0xFF5B21B6), fontSize = 15.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Bottom Action Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Was this helpful?", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF374151))
                    }
                    val isHelpful = HelpfulExperiencesManager.isHelpful(experience.id)
                    Button(
                        onClick = { 
                            HelpfulExperiencesManager.toggleHelpful(experience.id)
                            showNotification(if (HelpfulExperiencesManager.isHelpful(experience.id)) "Marked as helpful" else "Removed from helpful")
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isHelpful) PrimaryBlue else Color(0xFFF3F4F6),
                            contentColor = if (isHelpful) Color.White else Color(0xFF6B7280)
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(
                            if (isHelpful) Icons.Default.ThumbUp else Icons.Default.ThumbUpOffAlt, 
                            null, 
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isHelpful) "Helpful" else "Was this helpful?", fontSize = 13.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
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
            color = Color(0xFF1F2937).copy(alpha = 0.9f),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.padding(top = 48.dp),
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (globalNotificationMessage.contains("helpful")) Icons.Default.ThumbUp else Icons.Default.Info,
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
fun ExperienceSectionCard(
    title: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color.White.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = contentColor, modifier = Modifier.size(18.dp))
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontWeight = FontWeight.Bold, color = contentColor, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun StatusBadge(text: String, bgColor: Color, textColor: Color) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier.padding(end = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
