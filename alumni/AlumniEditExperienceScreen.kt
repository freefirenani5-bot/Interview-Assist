package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.simats.interviewassist.ui.theme.PrimaryBlue
import com.simats.interviewassist.ui.theme.TextTitle
import com.simats.interviewassist.ui.screens.student.getMockCompany
import com.simats.interviewassist.ui.screens.student.ExperienceSectionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniEditExperienceScreen(
    companyName: String,
    experienceId: String,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val company = remember { getMockCompany(companyName) }
    val experience = remember { company.experiences.find { it.id == experienceId } ?: company.experiences.first() }
    val scrollState = rememberScrollState()

    // State for all editable fields
    var brief by remember { mutableStateOf(experience.brief) }
    var applicationProcess by remember { mutableStateOf(experience.applicationProcess) }
    var interviewProcess by remember { mutableStateOf(experience.interviewRounds.joinToString("\n") { "${it.title}: ${it.duration}" }) }
    var myExperience by remember { mutableStateOf(experience.myExperience) }
    var technicalQuestions by remember { mutableStateOf(experience.technicalQuestions.joinToString("\n")) }
    var behavioralQuestions by remember { mutableStateOf(experience.behavioralQuestions.joinToString("\n")) }
    var mistakes by remember { mutableStateOf(experience.mistakes.joinToString("\n")) }
    var prepStrategy by remember { mutableStateOf(experience.preparationStrategy.map { "${it.key}: ${it.value.joinToString(", ")}" }.joinToString("\n")) }
    var finalAdvice by remember { mutableStateOf(experience.finalAdvice.joinToString("\n")) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Experience", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel")
                    }
                },
                actions = {
                    TextButton(onClick = onSave) {
                        Text("Save", color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            Text(
                "Keep your experience details up to date to help juniors prepare better.",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Brief Section
            EditSectionCard(
                title = "About the Author/Brief",
                icon = Icons.Default.Person,
                containerColor = Color(0xFFEFF6FF),
                contentColor = PrimaryBlue,
                value = brief,
                onValueChange = { brief = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Application Process
            EditSectionCard(
                title = "How I got the interview",
                icon = Icons.Default.Bolt,
                containerColor = Color(0xFFFAF5FF),
                contentColor = Color(0xFF9333EA),
                value = applicationProcess,
                onValueChange = { applicationProcess = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Interview Process Breakdown
            EditSectionCard(
                title = "Interview Process Breakdown",
                icon = Icons.Default.Timeline,
                containerColor = Color(0xFFEEF2FF),
                contentColor = Color(0xFF4F46E5),
                value = interviewProcess,
                onValueChange = { interviewProcess = it },
                placeholder = "e.g. Round 1: Online Test"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // My Experience
            EditSectionCard(
                title = "My Detailed Experience",
                icon = Icons.Default.Article,
                containerColor = Color(0xFFF9FAFB),
                contentColor = Color(0xFF374151),
                value = myExperience,
                onValueChange = { myExperience = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Technical Questions
            EditSectionCard(
                title = "Questions Asked",
                icon = Icons.Default.HelpOutline,
                containerColor = Color(0xFFFFF7ED),
                contentColor = Color(0xFFEA580C),
                value = technicalQuestions,
                onValueChange = { technicalQuestions = it },
                placeholder = "One question per line..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mistakes
            EditSectionCard(
                title = "Mistakes I Made",
                icon = Icons.Default.Warning,
                containerColor = Color(0xFFFEF2F2),
                contentColor = Color(0xFFDC2626),
                value = mistakes,
                onValueChange = { mistakes = it },
                placeholder = "One mistake per line..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Preparation Strategy
            EditSectionCard(
                title = "Preparation Strategy",
                icon = Icons.Default.School,
                containerColor = Color(0xFFECFDF5),
                contentColor = Color(0xFF059669),
                value = prepStrategy,
                onValueChange = { prepStrategy = it },
                placeholder = "Category: Tool 1, Tool 2..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Final Advice
            EditSectionCard(
                title = "Final Advice & Conclusion",
                icon = Icons.Default.VolunteerActivism,
                containerColor = Color(0xFFF5F3FF),
                contentColor = Color(0xFF7C3AED),
                value = finalAdvice,
                onValueChange = { finalAdvice = it },
                placeholder = "One tip per line..."
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Changes", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun EditSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: Color,
    contentColor: Color,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Write here..."
) {
    ExperienceSectionCard(
        title = title,
        icon = icon,
        containerColor = containerColor,
        contentColor = contentColor
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
            placeholder = { Text(placeholder, color = contentColor.copy(alpha = 0.5f)) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = contentColor,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                focusedContainerColor = Color.White
            )
        )
    }
}
