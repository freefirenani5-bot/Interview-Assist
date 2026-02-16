package com.simats.interviewassist.ui.screens.auth

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onCreateAccount: () -> Unit,
    onForgotPassword: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    var selectedRole by remember { mutableStateOf("Student") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Logo Section
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(12.dp, RoundedCornerShape(20.dp), spotColor = PrimaryBlue)
                .clip(RoundedCornerShape(20.dp))
                .background(PrimaryBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            color = TextTitle
        )

        Text(
            text = "Sign in to continue to Interview Assist",
            style = MaterialTheme.typography.bodyLarge,
            color = TextBody
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Role Selection
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RoleTab(
                label = "Student",
                icon = Icons.Default.School,
                isSelected = selectedRole == "Student",
                onClick = { selectedRole = "Student" },
                modifier = Modifier.weight(1f)
            )
            RoleTab(
                label = "Alumni",
                icon = Icons.Default.Work,
                isSelected = selectedRole == "Alumni",
                onClick = { selectedRole = "Alumni" },
                modifier = Modifier.weight(1f)
            )
            RoleTab(
                label = "Admin",
                icon = Icons.Default.Security,
                isSelected = selectedRole == "Admin",
                onClick = { selectedRole = "Admin" },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Input Fields
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Email Address",
                style = MaterialTheme.typography.labelLarge,
                color = TextTitle,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("you@university.edu", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF0F0F0),
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color(0xFFFBFBFB),
                    focusedContainerColor = Color(0xFFFBFBFB)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Password",
                style = MaterialTheme.typography.labelLarge,
                color = TextTitle,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("........", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF0F0F0),
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color(0xFFFBFBFB),
                    focusedContainerColor = Color(0xFFFBFBFB)
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Text(
                text = "Forgot Password?",
                color = PrimaryBlue,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onForgotPassword() }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Sign In Button
        Button(
            onClick = { onLoginSuccess(selectedRole) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Sign In as $selectedRole",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account? ",
                color = TextBody,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Create Account",
                color = PrimaryBlue,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onCreateAccount() }
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun RoleTab(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) SelectedRoleBg else Color.Transparent)
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) PrimaryBlue else TextBody.copy(alpha = 0.5f),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                color = if (isSelected) PrimaryBlue else TextBody.copy(alpha = 0.5f),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}
