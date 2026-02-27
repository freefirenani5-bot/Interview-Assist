package com.simats.interviewassist.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminChangePasswordScreen(
    onBack: () -> Unit = {}
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Change Password", 
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding)
                .padding(24.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Current Password", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        placeholder = { Text("Enter current password", color = Color(0xFF9CA3AF), fontSize = 15.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFF0F0F0),
                            focusedBorderColor = PrimaryBlue,
                            unfocusedContainerColor = Color(0xFFFBFBFB),
                            focusedContainerColor = Color(0xFFFBFBFB)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text("New Password", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        placeholder = { Text("Enter new password", color = Color(0xFF9CA3AF), fontSize = 15.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFF0F0F0),
                            focusedBorderColor = PrimaryBlue,
                            unfocusedContainerColor = Color(0xFFFBFBFB),
                            focusedContainerColor = Color(0xFFFBFBFB)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text("Confirm New Password", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = { Text("Confirm new password", color = Color(0xFF9CA3AF), fontSize = 15.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFF0F0F0),
                            focusedBorderColor = PrimaryBlue,
                            unfocusedContainerColor = Color(0xFFFBFBFB),
                            focusedContainerColor = Color(0xFFFBFBFB)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFEFF6FF),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Password Requirements",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E40AF)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    val requirements = listOf(
                        "At least 8 characters long",
                        "Contains uppercase and lowercase letters",
                        "Contains at least one number",
                        "Contains at least one special character"
                    )
                    requirements.forEach { req ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                            Box(modifier = Modifier.size(4.dp).background(Color(0xFF2563EB), RoundedCornerShape(2.dp)))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(req, fontSize = 13.sp, color = Color(0xFF2563EB))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Update Password", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
