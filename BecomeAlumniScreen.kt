package com.simats.interviewassist.ui.screens.student

import androidx.compose.foundation.*
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
fun BecomeAlumniScreen(
    onBack: () -> Unit,
    onComplete: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var currentCompany by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var graduationYear by remember { mutableStateOf("") }
    var linkedInProfile by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    var expandedYear by remember { mutableStateOf(false) }
    val years = (2020..2030).map { it.toString() }

    Scaffold(
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back Button
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextTitle)
                }
            }

            // Header Icon
            Surface(
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(16.dp),
                color = PrimaryBlue
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Work, 
                        contentDescription = null, 
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Complete Your Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextTitle
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "Help students by sharing your journey",
                fontSize = 15.sp,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Profile Photo
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    color = Color(0xFFE5E7EB)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("P", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                    }
                }
                Surface(
                    modifier = Modifier.size(32.dp),
                    shape = CircleShape,
                    color = PrimaryBlue,
                    border = BorderStroke(2.dp, Color.White)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.CameraAlt, null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }
            
            Text(
                "Add Photo",
                modifier = Modifier.padding(top = 12.dp).clickable { },
                color = PrimaryBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Form Fields
            AlumniFormField(label = "Phone Number", value = phoneNumber, onValueChange = { phoneNumber = it }, placeholder = "+1 (555) 000-0000")
            Spacer(modifier = Modifier.height(24.dp))
            
            AlumniFormField(label = "Current Company", value = currentCompany, onValueChange = { currentCompany = it }, placeholder = "e.g. Google")
            Spacer(modifier = Modifier.height(24.dp))
            
            AlumniFormField(label = "Job Title", value = jobTitle, onValueChange = { jobTitle = it }, placeholder = "e.g. Software Engineer")
            Spacer(modifier = Modifier.height(24.dp))
            
            // Graduation Year Dropdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Graduation Year", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedYear,
                    onExpandedChange = { expandedYear = it }
                ) {
                    OutlinedTextField(
                        value = graduationYear.ifEmpty { "Select year" },
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedYear) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 15.sp,
                            color = if (graduationYear.isEmpty()) Color(0xFF9CA3AF) else TextTitle
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedYear,
                        onDismissRequest = { expandedYear = false }
                    ) {
                        years.forEach { year ->
                            DropdownMenuItem(
                                text = { Text(year) },
                                onClick = {
                                    graduationYear = year
                                    expandedYear = false
                                }
                            )
                        }
                    }
                }
                Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            AlumniFormField(label = "LinkedIn Profile", value = linkedInProfile, onValueChange = { linkedInProfile = it }, placeholder = "linkedin.com/in/yourprofile")
            Spacer(modifier = Modifier.height(24.dp))
            
            // Bio Field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Brief Bio", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = bio,
                    onValueChange = { if (it.length <= 300) bio = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    placeholder = { 
                        Text(
                            "Tell students about your journey and what you can help with...", 
                            color = Color(0xFF9CA3AF), 
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        ) 
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp, lineHeight = 22.sp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text("${bio.length}/300", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                }
                Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Footer Actions
            Button(
                onClick = onComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Complete Profile", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            
            TextButton(
                onClick = onBack,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Skip for now", color = Color(0xFF9CA3AF), fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun AlumniFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color(0xFF9CA3AF), fontSize = 15.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
            singleLine = true
        )
        Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
    }
}
