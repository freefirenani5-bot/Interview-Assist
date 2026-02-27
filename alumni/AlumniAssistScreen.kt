package com.simats.interviewassist.ui.screens.alumni

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniAssistScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToPosts: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToShareExperience: () -> Unit
) {
    var answeringQuestion by remember { mutableStateOf<AssistQuestion?>(null) }
    var reportingQuestion by remember { mutableStateOf<AssistQuestion?>(null) }
    var showAnswerSuccess by remember { mutableStateOf(false) }
    
    val answerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val reportSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToHome,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.AddCircleOutline, contentDescription = "Add") },
                    label = { Text("Add", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToShareExperience,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Handshake, contentDescription = "Assist") },
                    label = { Text("Assist", fontSize = 10.sp) },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SelectedRoleBg
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Description, contentDescription = "Posts") },
                    label = { Text("Posts", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToPosts,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.PersonOutline, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToProfile,
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextBody.copy(alpha = 0.5f),
                        unselectedTextColor = TextBody.copy(alpha = 0.5f)
                    )
                )
            }
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                            text = "Alumni",
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
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        IconButton(onClick = onNavigateToHome, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.ArrowBack, null, tint = TextTitle)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            "Answer Questions",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                    }
                    Text(
                        "Help students by answering their questions about interviews and companies.",
                        fontSize = 15.sp,
                        color = Color(0xFF6B7280),
                        modifier = Modifier.padding(start = 40.dp, bottom = 24.dp),
                        lineHeight = 22.sp
                    )
                }

                items(dummyAssistQuestions) { question ->
                    AssistQuestionCard(
                        question = question,
                        onAnswerClick = { answeringQuestion = it },
                        onReportClick = { reportingQuestion = question }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Write Answer Bottom Sheet
        if (answeringQuestion != null) {
            ModalBottomSheet(
                onDismissRequest = { answeringQuestion = null },
                sheetState = answerSheetState,
                containerColor = Color.White,
                dragHandle = null
            ) {
                WriteAnswerSheet(
                    question = answeringQuestion!!,
                    onClose = {
                        scope.launch { answerSheetState.hide() }.invokeOnCompletion {
                            answeringQuestion = null
                        }
                    },
                    onPost = {
                        // Handle post logic
                        scope.launch { answerSheetState.hide() }.invokeOnCompletion {
                            answeringQuestion = null
                            showAnswerSuccess = true
                        }
                    }
                )
            }
        }

        // Report Bottom Sheet
        if (reportingQuestion != null) {
            ModalBottomSheet(
                onDismissRequest = { reportingQuestion = null },
                sheetState = reportSheetState,
                containerColor = Color.White,
                dragHandle = null
            ) {
                ReportSheet(
                    onClose = {
                        scope.launch { reportSheetState.hide() }.invokeOnCompletion {
                            reportingQuestion = null
                        }
                    },
                    onSubmit = {
                        scope.launch { reportSheetState.hide() }.invokeOnCompletion {
                            reportingQuestion = null
                        }
                    }
                )
            }
        }

        // Success Dialog
        if (showAnswerSuccess) {
            AlertDialog(
                onDismissRequest = { showAnswerSuccess = false },
                confirmButton = {
                    TextButton(onClick = { showAnswerSuccess = false }) {
                        Text("OK", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }
                },
                title = { Text("Success!", fontWeight = FontWeight.Bold) },
                text = { Text("Your answer has been submitted successfully.") },
                shape = RoundedCornerShape(24.dp),
                containerColor = Color.White
            )
        }
    }
}

@Composable
fun AssistQuestionCard(
    question: AssistQuestion,
    onAnswerClick: (AssistQuestion) -> Unit,
    onReportClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp // Added elevation
    ) {
        Column(modifier = Modifier.padding(24.dp)) { // Increased padding
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        color = Color(0xFFE0E7FF) // Consistent light blue/indigo
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(question.initials, color = PrimaryBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(question.userName, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("asked", fontSize = 13.sp, color = Color(0xFF6B7280))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.AccessTime, null, modifier = Modifier.size(14.dp), tint = Color(0xFF9CA3AF))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(question.date, fontSize = 12.sp, color = Color(0xFF9CA3AF))
                        }
                    }
                }
                IconButton(onClick = onReportClick) {
                    Icon(Icons.Outlined.OutlinedFlag, null, tint = Color(0xFF9CA3AF))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                question.content,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold, // Increased weight
                color = TextTitle,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = Color(0xFFEFF6FF),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.ChatBubbleOutline, null, modifier = Modifier.size(16.dp), tint = PrimaryBlue)
                        Spacer(modifier = Modifier.width(8.dp))
                        val answerText = if (question.answerCount == 1) "1 Answer" else "${question.answerCount} Answers"
                        Text(answerText, color = PrimaryBlue, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }

                TextButton(onClick = { onAnswerClick(question) }) {
                    Text("Answer this", color = Color(0xFF6B7280), fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun WriteAnswerSheet(
    question: AssistQuestion,
    onClose: () -> Unit,
    onPost: (String) -> Unit
) {
    var answerText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding() // ensuring it respects system bars
            .imePadding() // ensuring it moves up with keyboard
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Write Answer", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextTitle)
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, null, tint = Color(0xFF9CA3AF))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Question Context Box
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFF9FAFB),
            shape = RoundedCornerShape(8.dp) // Slightly less rounded in mockup
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Question from ",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280)
                    )
                    Text(
                        question.userName,
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280), // Name is also gray in mockup context
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    question.content,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = answerText,
            onValueChange = { answerText = it },
            placeholder = { 
                Text(
                    "Share your knowledge and experience...", 
                    color = Color(0xFF9CA3AF),
                    fontSize = 16.sp
                ) 
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Allow text field to take available space
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = PrimaryBlue
            ),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp, color = TextTitle)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onPost(answerText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Post Answer", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}

@Composable
fun ReportSheet(
    onClose: () -> Unit,
    onSubmit: () -> Unit
) {
    val reasons = listOf(
        "Inappropriate content",
        "Spam",
        "Not a genuine question",
        "Harassment",
        "Other"
    )
    var selectedReason by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding() // Handheld space
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Report Question", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextTitle)
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, null, tint = Color(0xFF9CA3AF))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Why are you reporting this question?",
            fontSize = 16.sp,
            color = Color(0xFF6B7280),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        reasons.forEach { reason ->
            val isSelected = selectedReason == reason
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedReason = reason }
                    .padding(vertical = 16.dp), // Increased vertical padding
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reason,
                    fontSize = 16.sp,
                    color = if (isSelected) TextTitle else TextTitle.copy(alpha = 0.8f),
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                )
                
                // Design Mockup doesn't show Radio buttons. Using Check mark for selected state for better UX while staying clean.
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEF4444),
                disabledContainerColor = Color(0xFFEF4444).copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = selectedReason.isNotEmpty()
        ) {
            Text("Submit Report", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

data class AssistQuestion(
    val userName: String,
    val initials: String,
    val date: String,
    val content: String,
    val answerCount: Int
)

val dummyAssistQuestions = listOf(
    AssistQuestion(
        "Alex Wong",
        "AW",
        "3 days ago",
        "Does Cognizant allow remote work for new grad roles?",
        4
    ),
    AssistQuestion(
        "Maria Garcia",
        "MG",
        "1 day ago",
        "How much emphasis is placed on system design for L3 roles?",
        0
    ),
    AssistQuestion(
        "John Smith",
        "JS",
        "2 days ago",
        "What is the typical timeline for TCS internship interviews?",
        2
    )
)
