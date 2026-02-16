package com.simats.interviewassist.ui.screens.student

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentMyQuestionsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "My Questions", 
                        fontSize = 18.sp, 
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextTitle)
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            QuestionItem(
                company = "Google",
                question = "Does Google allow remote work for new grad roles?",
                askedTime = "3 days ago",
                status = "Answered",
                alumniName = "Sarah Chen",
                alumniRole = "Software Engineer at Google",
                answer = "Yes, Google offers hybrid work options for most roles. New grads typically work 3 days in office and 2 days remote. Some teams are fully remote depending on the project.",
                answerTime = "2 days ago"
            )

            Spacer(modifier = Modifier.height(16.dp))

            QuestionItem(
                company = "Microsoft",
                question = "What is the typical timeline for Microsoft internship interviews?",
                askedTime = "1 week ago",
                status = "Pending"
            )

            Spacer(modifier = Modifier.height(16.dp))

            QuestionItem(
                company = "Amazon",
                question = "How important are leadership principles in the interview?",
                askedTime = "2 weeks ago",
                status = "Answered",
                alumniName = "Mike Ross",
                alumniRole = "SDE-2 at Amazon",
                answer = "Leadership principles are VERY important at Amazon. Every interview round will have behavioral questions based on LPs. I recommend preparing 2-3 stories for each principle using the STAR method.",
                answerTime = "1 week ago"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun QuestionItem(
    company: String,
    question: String,
    askedTime: String,
    status: String,
    alumniName: String? = null,
    alumniRole: String? = null,
    answer: String? = null,
    answerTime: String? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header: Company and Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color(0xFFEFF6FF),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = company,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = PrimaryBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                StatusBadge(status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Question Text
            Text(
                text = question,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextTitle,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Asked  $askedTime",
                fontSize = 12.sp,
                color = Color(0xFF9CA3AF)
            )

            // Answer Block (if applicable)
            if (status == "Answered" && answer != null) {
                Spacer(modifier = Modifier.height(20.dp))
                AnswerBlock(
                    name = alumniName ?: "",
                    role = alumniRole ?: "",
                    answer = answer,
                    time = answerTime ?: ""
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val bgColor = if (status == "Answered") Color(0xFFECFDF5) else Color(0xFFFFF7ED)
    val textColor = if (status == "Answered") Color(0xFF10B981) else Color(0xFFF59E0B)
    val icon = if (status == "Answered") Icons.Default.CheckCircle else Icons.Default.Schedule

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, null, tint = textColor, modifier = Modifier.size(14.dp))
            Text(status, color = textColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun AnswerBlock(
    name: String,
    role: String,
    answer: String,
    time: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF8F9FA)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Blue indicator line like in screenshot
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight()
                    .background(PrimaryBlue, RoundedCornerShape(2.dp))
                    .align(Alignment.Top)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                // Alumni Profile Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = CircleShape,
                        color = Color(0xFFE5E7EB)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                name.split(" ").map { it.take(1) }.joinToString(""),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextTitle)
                        Text(role, fontSize = 11.sp, color = Color(0xFF6B7280))
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Answer Text
                Text(
                    text = answer,
                    fontSize = 13.sp,
                    color = Color(0xFF4B5563),
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Answered  $time",
                    fontSize = 11.sp,
                    color = Color(0xFF9CA3AF)
                )
            }
        }
    }
}
