package com.simats.interviewassist.ui.screens.alumni

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniChangePasswordScreen(
    onBack: () -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Change Password", 
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Password Fields Container
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    AlumniPasswordField(
                        label = "Current Password",
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        isVisible = currentPasswordVisible,
                        onToggleVisibility = { currentPasswordVisible = !currentPasswordVisible },
                        placeholder = "Enter current password"
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    AlumniPasswordField(
                        label = "New Password",
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        isVisible = newPasswordVisible,
                        onToggleVisibility = { newPasswordVisible = !newPasswordVisible },
                        placeholder = "Enter new password"
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    AlumniPasswordField(
                        label = "Confirm New Password",
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        isVisible = confirmPasswordVisible,
                        onToggleVisibility = { confirmPasswordVisible = !confirmPasswordVisible },
                        placeholder = "Confirm new password"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Password Requirements
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF3F7FF)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Password Requirements", 
                        fontSize = 14.sp, 
                        fontWeight = FontWeight.Bold, 
                        color = PrimaryBlue
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AlumniRequirementItem("At least 8 characters long")
                    AlumniRequirementItem("Contains uppercase and lowercase letters")
                    AlumniRequirementItem("Contains at least one number")
                    AlumniRequirementItem("Contains at least one special character")
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            // Update Button
            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Password updated successfully!",
                            duration = SnackbarDuration.Short
                        )
                        delay(1000)
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Update Password", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun AlumniPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
    placeholder: String
) {
    Column {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextTitle)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color(0xFF9CA3AF), fontSize = 15.sp) },
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onToggleVisibility) {
                    Icon(
                        imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = Color(0xFF9CA3AF)
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFF0F0F0),
                focusedBorderColor = PrimaryBlue,
                unfocusedContainerColor = Color(0xFFFBFBFB),
                focusedContainerColor = Color(0xFFFBFBFB)
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
        )
    }
}

@Composable
fun AlumniRequirementItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text("•", color = PrimaryBlue, modifier = Modifier.padding(end = 8.dp))
        Text(text, fontSize = 12.sp, color = Color(0xFF3B82F6).copy(alpha = 0.8f))
    }
}
