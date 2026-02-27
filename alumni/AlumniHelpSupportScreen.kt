package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniHelpSupportScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Help & Support", 
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
            // Support Channels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AlumniSupportCard(
                    icon = Icons.Outlined.ChatBubbleOutline,
                    iconColor = Color(0xFF3B82F6),
                    iconBg = Color(0xFFEFF6FF),
                    title = "Live Chat",
                    subtitle = "Available 24/7",
                    modifier = Modifier.weight(1f)
                )
                AlumniSupportCard(
                    icon = Icons.Outlined.Email,
                    iconColor = Color(0xFF10B981),
                    iconBg = Color(0xFFECFDF5),
                    title = "Email Us",
                    subtitle = "support@interviewassist.com",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // FAQ Section
            Text(
                "Frequently Asked Questions",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6B7280),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    AlumniFaqItem(
                        question = "How do I share my interview experience?",
                        answer = "Tap the '+' icon on the home screen or go to 'Add' in the bottom navigation to create a new experience post."
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF3F4F6))
                    AlumniFaqItem(
                        question = "How can I answer student questions?",
                        answer = "Go to the 'Answers' tab in the bottom navigation to see all pending questions from students in your domain."
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF3F4F6))
                    AlumniFaqItem(
                        question = "How do I report inappropriate content?",
                        answer = "Click the three dots menu on any post or comment and select 'Report'. Our team will review it within 24 hours."
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Resources Section
            Text(
                "Resources",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6B7280),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFF3F7FF)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.Description, null, tint = PrimaryBlue, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "User Guide", 
                        fontSize = 15.sp, 
                        fontWeight = FontWeight.Medium, 
                        color = TextTitle,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Outlined.OpenInNew, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun AlumniSupportCard(
    icon: ImageVector,
    iconColor: Color,
    iconBg: Color,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(160.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = iconBg
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = iconColor, modifier = Modifier.size(24.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextTitle)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                subtitle, 
                fontSize = 11.sp, 
                color = Color(0xFF6B7280),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun AlumniFaqItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                question, 
                fontSize = 15.sp, 
                fontWeight = FontWeight.Medium, 
                color = TextTitle,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.ChevronRight, 
                null, 
                tint = Color(0xFFD1D5DB),
                modifier = Modifier.rotate(rotation)
            )
        }
        AnimatedVisibility(visible = expanded) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    answer, 
                    fontSize = 14.sp, 
                    color = Color(0xFF6B7280),
                    lineHeight = 20.sp
                )
            }
        }
    }
}
