package com.simats.interviewassist.ui.screens.student

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCreateAccountScreen(onBack: () -> Unit, onContinue: (String) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("Student") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Back Button
        IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = TextTitle,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            color = TextTitle,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Join the Interview Assist community",
            style = MaterialTheme.typography.bodyLarge,
            color = TextBody
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Role Cards Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SignupRoleCard(
                label = "Student",
                description = "Looking for jobs",
                icon = Icons.Default.School,
                isSelected = selectedRole == "Student",
                onClick = { selectedRole = "Student" },
                modifier = Modifier.weight(1f)
            )
            SignupRoleCard(
                label = "Alumni",
                description = "Share experiences",
                icon = Icons.Default.WorkOutline,
                isSelected = selectedRole == "Alumni",
                onClick = { selectedRole = "Alumni" },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Names Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Label("First Name")
                SignupTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    placeholder = "John"
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Label("Last Name")
                SignupTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    placeholder = "Doe"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Label("University Email")
        SignupTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "john.doe@university.edu"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Label("Password")
        SignupTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Create a password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Label("Confirm Password")
        SignupTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Confirm password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Continue Button
        Button(
            onClick = { onContinue(selectedRole) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = "Continue as $selectedRole",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "By creating an account, you agree to our Terms of \nService and Privacy Policy.",
            style = MaterialTheme.typography.labelLarge,
            color = TextBody.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun Label(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = TextTitle,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.LightGray) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFF0F0F0),
            focusedBorderColor = PrimaryBlue,
            unfocusedContainerColor = Color(0xFFFBFBFB),
            focusedContainerColor = Color(0xFFFBFBFB)
        ),
        singleLine = true
    )
}

@Composable
fun SignupRoleCard(
    label: String,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(20.dp)),
        color = if (isSelected) SelectedRoleBg else Color(0xFFF8F9FA),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) PrimaryBlue else TextBody.copy(alpha = 0.4f),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) PrimaryBlue else TextTitle,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 11.sp,
                color = if (isSelected) PrimaryBlue.copy(alpha = 0.7f) else TextBody.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}
