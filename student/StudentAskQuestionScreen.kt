package com.simats.interviewassist.ui.screens.student

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StudentAskQuestionScreen(
    companyName: String = "Cognizant", // Default value
    onBack: () -> Unit,
    onPostSuccess: () -> Unit = {}
) {
    var questionText by remember { mutableStateOf("") }
    val maxChars = 500
    var selectedCompany by remember { mutableStateOf(companyName) }
    var expanded by remember { mutableStateOf(false) }

    val companies = listOf(
        "Cognizant", "TCS", "Blackstraw", "Hexaware", "Zoho",
        "Accenture", "Wipro", "Infosys", "HCL", "Capgemini"
    )

    val suggestedQuestions = listOf(
        "What's the interview process like?",
        "How should I prepare for the technical round?",
        "What's the work culture like?",
        "Do they offer remote work options?"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ask a Question",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1E293B))
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
            // Info Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFE0F2FE), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.HelpOutline,
                            contentDescription = null,
                            tint = PrimaryBlue
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            "Get answers from alumni",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Your question will be visible to verified alumni who have worked at the selected company. They'll share their real experiences to help you.",
                            color = PrimaryBlue,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Content Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Company Selector
                    Text(
                        "Select Company",
                        fontSize = 16.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expanded = true }
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedCompany,
                                fontSize = 18.sp,
                                color = Color(0xFF1E293B),
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select",
                                tint = Color(0xFF94A3B8)
                            )
                        }
                        
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .background(Color.White)
                        ) {
                            companies.forEach { company ->
                                DropdownMenuItem(
                                    text = { Text(company) },
                                    onClick = {
                                        selectedCompany = company
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Question Input
                    Text(
                        "Your Question",
                        fontSize = 16.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        value = questionText,
                        onValueChange = { if (it.length <= maxChars) questionText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        placeholder = {
                            Text(
                                "how many rounds will be there",
                                color = Color(0xFF1E293B),
                                fontSize = 18.sp
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = PrimaryBlue
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color(0xFF1E293B))
                    )

                    // Char Count
                    Text(
                        text = "${questionText.length}/$maxChars",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        fontSize = 13.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Suggested Questions
            Text(
                "Suggested Questions",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                suggestedQuestions.forEach { suggestion ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White,
                        modifier = Modifier.clickable { questionText = suggestion }
                    ) {
                        Text(
                            text = suggestion,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            fontSize = 14.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Post Button
            Button(
                onClick = {
                    UserQuestionsManager.addQuestion(selectedCompany, questionText)
                    onPostSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(12.dp),
                enabled = questionText.isNotEmpty()
            ) {
                Text(
                    "Post Question",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "Questions are reviewed before being posted. Please keep them professional and relevant.",
                fontSize = 13.sp,
                color = Color(0xFF94A3B8),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 18.sp
            )
        }
    }
}
