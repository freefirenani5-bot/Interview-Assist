package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*
import com.simats.interviewassist.ui.screens.student.EditProfileSectionTitle
import com.simats.interviewassist.ui.screens.student.EditProfileField
import com.simats.interviewassist.ui.screens.student.EditProfileDropdown
import com.simats.interviewassist.ui.screens.student.EditProfileBioField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniEditProfileScreen(
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    var fullName by remember { mutableStateOf("Sarah Chen") }
    var email by remember { mutableStateOf("sarah.chen@google.com") }
    var phoneNumber by remember { mutableStateOf("+1 (555) 123-4567") }
    
    var currentCompany by remember { mutableStateOf("Google") }
    var jobTitle by remember { mutableStateOf("Software Engineer") }
    var graduationYear by remember { mutableStateOf("2022") }
    
    var linkedIn by remember { mutableStateOf("linkedin.com/in/sarahchen") }
    var website by remember { mutableStateOf("") }
    
    var bio by remember { mutableStateOf("Software Engineer at Google with 2+ years of experience...") }

    var expandedGrad by remember { mutableStateOf(false) }
    val gradYears = listOf("2020", "2021", "2022", "2023", "2024")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Edit Profile", 
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
            // Profile Photo Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        color = Color(0xFFE5E7EB)
                    ) {
                        // Profile image placeholder
                        Icon(
                            Icons.Default.Person, 
                            null, 
                            modifier = Modifier.padding(20.dp),
                            tint = Color.White
                        )
                    }
                    Surface(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { },
                        color = PrimaryBlue,
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.CameraAlt, 
                                null, 
                                modifier = Modifier.size(16.dp), 
                                tint = Color.White
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Change Profile Photo", 
                    color = PrimaryBlue, 
                    fontSize = 14.sp, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Personal Info
            EditProfileSectionTitle("Personal Info")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    EditProfileField("Full Name", fullName) { fullName = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    EditProfileField("Email", email, icon = Icons.Outlined.Email) { email = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    EditProfileField("Phone Number", phoneNumber) { phoneNumber = it }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Work Info
            EditProfileSectionTitle("Work Info")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    EditProfileField("Current Company", currentCompany) { currentCompany = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    EditProfileField("Job Title", jobTitle) { jobTitle = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Box {
                        EditProfileDropdown("Graduation Year", graduationYear) { expandedGrad = true }
                        DropdownMenu(
                            expanded = expandedGrad,
                            onDismissRequest = { expandedGrad = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            gradYears.forEach { year ->
                                DropdownMenuItem(
                                    text = { Text(year) },
                                    onClick = {
                                        graduationYear = year
                                        expandedGrad = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Social Links
            EditProfileSectionTitle("Social Links")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    EditProfileField("LinkedIn Profile", linkedIn) { linkedIn = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    EditProfileField("Personal Website", website) { website = it }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // About
            EditProfileSectionTitle("About")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    EditProfileBioField("Bio", bio) { if (it.length <= 300) bio = it }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancel", color = Color(0xFF4B5563), fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onSave,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save Changes", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
