package com.simats.interviewassist.ui.screens.admin

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

data class ReportedContent(
    val id: String,
    val type: String, // "experience" or "question"
    val time: String,
    val title: String,
    val snippet: String,
    val reportedBy: String,
    val reason: String,
    val status: String,
    val contentCreator: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReportsScreen(
    onBack: () -> Unit = {}
) {
    val reports = remember {
        mutableStateListOf(
            ReportedContent(
                "1", "experience", "2 hours ago", "Interview at Google",
                "This experience contains spam links and irrelevant content...",
                "John Doe", "Spam content", "Pending", "Unknown User"
            ),
            ReportedContent(
                "2", "question", "5 hours ago", "Question about salary",
                "How much does Google pay for L3?",
                "Sarah Chen", "Inappropriate question", "Pending", "Mike Ross"
            ),
            ReportedContent(
                "3", "experience", "1 day ago", "Interview at Amazon",
                "Contains false information about the interview process...",
                "Alice Smith", "False information", "Pending", "Bob Jones"
            )
        )
    }

    var selectedReport by remember { mutableStateOf<ReportedContent?>(null) }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    if (showSheet && selectedReport != null) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            ReportDetailSheet(
                report = selectedReport!!,
                onKeep = {
                    reports.remove(selectedReport!!)
                    showSheet = false
                },
                onRemove = {
                    reports.remove(selectedReport!!)
                    showSheet = false
                }
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Reports",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextTitle
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Surface(
                            color = Color(0xFFFEF2F2),
                            shape = RoundedCornerShape(100.dp)
                        ) {
                            Text(
                                "3 Pending",
                                color = Color(0xFFEF4444),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextTitle)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(reports) { report ->
                ReportCard(
                    report = report,
                    onReview = {
                        selectedReport = report
                        showSheet = true
                    },
                    onKeep = { reports.remove(report) },
                    onRemove = { reports.remove(report) }
                )
            }
        }
    }
}

@Composable
fun ReportCard(
    report: ReportedContent,
    onReview: () -> Unit,
    onKeep: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Header: Icon, Type, Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFFEF2F2)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Flag, null, modifier = Modifier.size(24.dp), tint = Color(0xFFEF4444))
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Surface(
                        color = if (report.type == "experience") Color(0xFFE0E7FF) else Color(0xFFFEF3C7),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = report.type,
                            fontSize = 12.sp,
                            color = if (report.type == "experience") PrimaryBlue else Color(0xFFD97706),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(report.time, fontSize = 12.sp, color = Color(0xFF9CA3AF))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Content Preview Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8F9FA)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {
                        Text("Reported content by", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(report.contentCreator, fontSize = 12.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Medium)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(report.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextTitle)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(report.snippet, fontSize = 14.sp, color = TextBody, lineHeight = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Reporter and Reason
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text("Reported by", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(report.reportedBy, fontSize = 12.sp, color = Color(0xFF1F2937), fontWeight = FontWeight.Bold)
                }
                Text(report.reason, fontSize = 12.sp, color = Color(0xFFEF4444), fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onReview,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Visibility, null, modifier = Modifier.size(18.dp), tint = TextTitle)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Review", color = TextTitle, fontWeight = FontWeight.SemiBold)
                    }
                }

                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircleOutline, null, modifier = Modifier.size(18.dp), tint = Color(0xFF1F2937))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Keep", color = Color(0xFF1F2937), fontWeight = FontWeight.SemiBold)
                    }
                }

                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DeleteOutline, null, modifier = Modifier.size(18.dp), tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Remove", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportDetailSheet(
    report: ReportedContent,
    onKeep: () -> Unit,
    onRemove: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 40.dp)
    ) {
        Text(
            "Review Report",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextTitle
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("Reported Content", fontSize = 14.sp, color = Color(0xFF9CA3AF))
        Spacer(modifier = Modifier.height(12.dp))
        
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF8F9FA)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(report.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextTitle)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    report.snippet,
                    fontSize = 15.sp,
                    color = TextBody,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "By ${report.contentCreator}",
                    fontSize = 14.sp,
                    color = Color(0xFF9CA3AF)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Report Reason", fontSize = 14.sp, color = Color(0xFF9CA3AF))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            report.reason,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFEF4444)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onKeep,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F7FF))
            ) {
                Text("Keep Content", color = TextTitle, fontWeight = FontWeight.Bold)
            }
            
            Button(
                onClick = onRemove,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
            ) {
                Text("Remove Content", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
