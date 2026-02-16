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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentEditProfileScreen(
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    var fullName by remember { mutableStateOf("Alex Johnson") }
    var email by remember { mutableStateOf("alex.j@university.edu") }
    var phoneNumber by remember { mutableStateOf("+1 (555) 000-0000") }
    var department by remember { mutableStateOf("Computer Science") }
    var currentYear by remember { mutableStateOf("Final Year") }
    var graduationYear by remember { mutableStateOf("2024") }
    var bio by remember { mutableStateOf("Final year CS student interested in Web Development and AI. Looking for SDE roles.") }

    var expandedYear by remember { mutableStateOf(false) }
    var expandedGrad by remember { mutableStateOf(false) }
    val years = listOf("1st Year", "2nd Year", "3rd Year", "Final Year")
    val gradYears = listOf("2024", "2025", "2026", "2027")

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
                        shape = CircleShape
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

            // Academic Info
            EditProfileSectionTitle("Academic Info")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    EditProfileField("Major/Department", department) { department = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Box {
                        EditProfileDropdown("Current Year", currentYear) { expandedYear = true }
                        DropdownMenu(
                            expanded = expandedYear,
                            onDismissRequest = { expandedYear = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            years.forEach { year ->
                                DropdownMenuItem(
                                    text = { Text(year) },
                                    onClick = {
                                        currentYear = year
                                        expandedYear = false
                                    }
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Box {
                        EditProfileDropdown("Expected Graduation", graduationYear) { expandedGrad = true }
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
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    EditProfileBioField("Bio", bio) { if (it.length <= 200) bio = it }
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
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun EditProfileSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = TextTitle,
        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
    )
}

@Composable
fun EditProfileField(
    label: String, 
    value: String, 
    icon: ImageVector? = null,
    onValueChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    Column(modifier = Modifier.clickable { focusRequester.requestFocus() }) {
        Text(label, fontSize = 13.sp, color = Color(0xFF6B7280))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            leadingIcon = if (icon != null) {
                { Icon(icon, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(20.dp)) }
            } else null,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = PrimaryBlue,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = TextTitle,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        )
        // Bottom line divider
        Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
    }
}

@Composable
fun EditProfileDropdown(label: String, value: String, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Text(label, fontSize = 13.sp, color = Color(0xFF6B7280))
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextTitle)
            Icon(Icons.Default.KeyboardArrowDown, null, tint = Color(0xFF9CA3AF))
        }
        Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
    }
}

@Composable
fun EditProfileBioField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, fontSize = 13.sp, color = Color(0xFF6B7280))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(100.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = PrimaryBlue,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = TextTitle,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        )
        Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "${value.length}/200", 
            modifier = Modifier.align(Alignment.End),
            fontSize = 11.sp,
            color = Color(0xFFD1D5DB)
        )
    }
}
