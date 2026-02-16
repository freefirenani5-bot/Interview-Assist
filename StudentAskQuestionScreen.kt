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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudentAskQuestionScreen(
    onBack: () -> Unit,
    onPostSuccess: () -> Unit = {}
) {
    var selectedCompany by remember { mutableStateOf("Google") }
    var questionText by remember { mutableStateOf("") }
    val maxChars = 500
    var showCompanyDropdown by remember { mutableStateOf(false) }

    val companies = listOf("Google", "Amazon", "Microsoft", "Meta", "Apple", "Netflix")
    val suggestedQuestions = listOf(
        "What's the interview process like?",
        "How should I prepare for the technical round?",
        "What's the work culture like?",
        "Do they offer remote work options?"
    )

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = TextTitle)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ask a Question",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
            }
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
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFF3F7FF)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.HelpOutline, null, tint = PrimaryBlue, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            "Get answers from alumni",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                        Text(
                            "Your question will be visible to verified alumni who have worked at the selected company. They'll share their real experiences to help you.",
                            fontSize = 12.sp,
                            color = PrimaryBlue,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Section
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Company Selection
                    Text(
                        "Select Company",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Box {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showCompanyDropdown = true },
                            color = Color(0xFFF9FAFB),
                            shape = RoundedCornerShape(12.dp),
                            border = null
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(selectedCompany, fontSize = 15.sp, color = TextTitle)
                                Icon(Icons.Default.KeyboardArrowDown, null, tint = Color(0xFF9CA3AF))
                            }
                        }
                        DropdownMenu(
                            expanded = showCompanyDropdown,
                            onDismissRequest = { showCompanyDropdown = false },
                            modifier = Modifier.fillMaxWidth(0.85f).background(Color.White)
                        ) {
                            companies.forEach { company ->
                                DropdownMenuItem(
                                    text = { Text(company) },
                                    onClick = {
                                        selectedCompany = company
                                        showCompanyDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Question Input
                    Text(
                        "Your Question",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = questionText,
                        onValueChange = { if (it.length <= maxChars) questionText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        placeholder = { 
                            Text(
                                "e.g., how many rounds will be there", 
                                color = Color(0xFF9CA3AF),
                                fontSize = 15.sp
                            ) 
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = PrimaryBlue
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                    )
                    
                    Text(
                        text = "${questionText.length}/$maxChars",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        fontSize = 12.sp,
                        color = Color(0xFF9CA3AF)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Suggested Questions
            Text(
                "Suggested Questions",
                fontSize = 14.sp,
                color = Color(0xFF6B7280)
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Chunk the suggestions locally for a simple grid-like layout
                suggestedQuestions.chunked(2).forEach { rowSuggestions ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowSuggestions.forEach { suggestion ->
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { questionText = suggestion },
                                color = Color.White,
                                shape = RoundedCornerShape(20.dp),
                                border = null
                            ) {
                                Text(
                                    text = suggestion,
                                    color = Color(0xFF4B5563),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        // Fill space if the row has only one item
                        if (rowSuggestions.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Post Button
            Button(
                onClick = onPostSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(12.dp),
                enabled = questionText.isNotEmpty()
            ) {
                Text(
                    "Post Question",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Questions are reviewed before being posted. Please keep them professional and relevant.",
                fontSize = 12.sp,
                color = Color(0xFF9CA3AF),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 18.sp
            )
        }
    }
}
