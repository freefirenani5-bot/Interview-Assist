package com.simats.interviewassist.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHelpSupportScreen(
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Help & Support", 
                        fontSize = 20.sp, 
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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding),
            contentPadding = PaddingValues(24.dp)
        ) {
            // Contact Options
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ContactCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.ChatBubbleOutline,
                        title = "Live Chat",
                        subtitle = "Available 24/7",
                        iconBg = Color(0xFFDBEAFE),
                        iconTint = PrimaryBlue
                    )
                    ContactCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.Email,
                        title = "Email Us",
                        subtitle = "support@interviewassist.com",
                        iconBg = Color(0xFFD1FAE5),
                        iconTint = Color(0xFF10B981)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // FAQs
            item {
                Text(
                    "Frequently Asked Questions",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        FaqItem(
                            question = "How do I save an interview experience?",
                            answer = "Tap the bookmark icon on any experience card."
                        )
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = Color(0xFFF3F4F6))
                        FaqItem(
                            question = "How can I ask a question to alumni?",
                            answer = "Go to any company page and use the Q&A section."
                        )
                        Divider(modifier = Modifier.padding(horizontal = 20.dp), color = Color(0xFFF3F4F6))
                        FaqItem(
                            question = "How do I report inappropriate content?",
                            answer = "Hold down on any review and select 'Report'."
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Resources
            item {
                Text(
                    "Resources",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF3F4F6)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Outlined.Description, null, modifier = Modifier.size(20.dp), tint = TextTitle)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "User Guide",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextTitle,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            Icons.Outlined.Launch,
                            null,
                            tint = Color(0xFFD1D5DB),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContactCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconBg: Color,
    iconTint: Color
) {
    Card(
        modifier = modifier.height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = iconBg
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, modifier = Modifier.size(24.dp), tint = iconTint)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextTitle
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color(0xFF6B7280),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun FaqItem(
    question: String,
    answer: String
) {
    var expanded by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = question,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextTitle,
                modifier = Modifier.weight(1f)
            )
            Icon(
                if (expanded) Icons.Default.ArrowBack else Icons.Default.ChevronRight,
                null,
                tint = Color(0xFFD1D5DB),
                modifier = Modifier.size(20.dp).let { 
                    if (expanded) it.then(Modifier.background(Color.Transparent)) // lazy way to rotate icon
                    else it 
                }
            )
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = answer,
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp
            )
        }
    }
}
