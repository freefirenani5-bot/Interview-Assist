package com.simats.interviewassist.ui.screens.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onCreateAccount: (String) -> Unit,
    onForgotPassword: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    var selectedRole by remember { mutableStateOf("Student") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Validation state
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    fun validateAndLogin() {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        emailError = when {
            email.isBlank() -> "Email is required"
            !emailRegex.matches(email.trim()) -> "Please enter a valid email address"
            else -> null
        }
        passwordError = when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
        if (emailError == null && passwordError == null) {
            onLoginSuccess(selectedRole)
        }
    }


    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "SignInButtonScale"
    )

    // Shimmer animation
    val shimmerTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by shimmerTransition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Logo & Title Section
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = JakartaFontFamily,
                    fontWeight = FontWeight.Bold
                ),
                color = TextTitle
            )

            Text(
                text = "Sign in to continue to Interview Assist",
                style = MaterialTheme.typography.bodyLarge.copy(fontFamily = JakartaFontFamily),
                color = TextBody
            )
        }

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
                style = MaterialTheme.typography.labelLarge.copy(fontFamily = JakartaFontFamily),
                color = TextTitle,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (emailError != null) emailError = null
                },
                placeholder = { Text("you@university.edu", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = emailError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF0F0F0),
                    focusedBorderColor = PrimaryBlue,
                    errorBorderColor = Color(0xFFD32F2F),
                    unfocusedContainerColor = Color(0xFFFBFBFB),
                    focusedContainerColor = Color(0xFFFBFBFB),
                    errorContainerColor = Color(0xFFFFF3F3)
                ),
                singleLine = true
            )
            AnimatedVisibility(
                visible = emailError != null,
                enter = fadeIn() + expandVertically()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = emailError ?: "",
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = JakartaFontFamily),
                        color = Color(0xFFD32F2F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Password",
                style = MaterialTheme.typography.labelLarge.copy(fontFamily = JakartaFontFamily),
                color = TextTitle,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (passwordError != null) passwordError = null
                },
                placeholder = { Text("........", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF0F0F0),
                    focusedBorderColor = PrimaryBlue,
                    errorBorderColor = Color(0xFFD32F2F),
                    unfocusedContainerColor = Color(0xFFFBFBFB),
                    focusedContainerColor = Color(0xFFFBFBFB),
                    errorContainerColor = Color(0xFFFFF3F3)
                ),
                singleLine = true
            )
            AnimatedVisibility(
                visible = passwordError != null,
                enter = fadeIn() + expandVertically()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = passwordError ?: "",
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = JakartaFontFamily),
                        color = Color(0xFFD32F2F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = "Forgot Password?",
                    color = PrimaryBlue,
                    style = MaterialTheme.typography.labelLarge.copy(fontFamily = JakartaFontFamily),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onForgotPassword() }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Sign In Button with shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = PrimaryBlue)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(PrimaryBlue, Color(0xFF1565C0), PrimaryBlue)
                    )
                )
                .clickable(interactionSource = interactionSource, indication = null) {
                    validateAndLogin()
                },
            contentAlignment = Alignment.Center
        ) {
            // Shimmer sweep overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.18f),
                                Color.Transparent
                            ),
                            start = Offset(shimmerOffset - 300f, 0f),
                            end = Offset(shimmerOffset + 300f, 0f)
                        )
                    )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign In as $selectedRole",
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = JakartaFontFamily),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Create Account
        if (selectedRole != "Admin") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = TextBody,
                    style = MaterialTheme.typography.bodyLarge.copy(fontFamily = JakartaFontFamily)
                )
                Text(
                    text = "Create Account",
                    color = PrimaryBlue,
                    style = MaterialTheme.typography.bodyLarge.copy(fontFamily = JakartaFontFamily),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onCreateAccount(selectedRole) }
                )
            }
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