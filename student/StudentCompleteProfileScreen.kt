package com.simats.interviewassist.ui.screens.student

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCompleteProfileScreen(onComplete: () -> Unit, onSkip: () -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }
    var major by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    
    var expGradYearExpanded by remember { mutableStateOf(false) }
    var selectedExpGradYear by remember { mutableStateOf("") }
    val gradYears = listOf("2024", "2025", "2026", "2027", "2028")

    var currentYearExpanded by remember { mutableStateOf(false) }
    var selectedCurrentYear by remember { mutableStateOf("") }
    val currentYears = listOf("1st Year", "2nd Year", "3rd Year", "4th Year", "5th Year")

    // Validation State
    var phoneNumberError by remember { mutableStateOf<String?>(null) }
    var majorError by remember { mutableStateOf<String?>(null) }
    var expGradYearError by remember { mutableStateOf<String?>(null) }
    var currentYearError by remember { mutableStateOf<String?>(null) }

    fun validateAndComplete() {
        val phoneRegex = Regex("^\\+?[0-9\\-\\s()]{7,15}$")
        
        phoneNumberError = when {
            phoneNumber.isBlank() -> "Phone number is required"
            !phoneRegex.matches(phoneNumber.trim()) -> "Enter a valid phone number"
            else -> null
        }
        
        majorError = if (major.isBlank()) "Major is required" else null
        expGradYearError = if (selectedExpGradYear.isBlank()) "Please select expected graduation year" else null
        currentYearError = if (selectedCurrentYear.isBlank()) "Please select your current year" else null

        if (phoneNumberError == null && majorError == null && expGradYearError == null && currentYearError == null) {
            onComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // App Logo
        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = PrimaryBlue)
                .clip(RoundedCornerShape(16.dp))
                .background(PrimaryBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Complete Your Profile",
            style = MaterialTheme.typography.headlineMedium,
            color = TextTitle,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tell us about yourself",
            style = MaterialTheme.typography.bodyLarge,
            color = TextBody,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Profile Picture Placeholder
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.size(120.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFFEBF2FF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "P", // Placeholder initial
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue)
                    .clickable { /* TODO: Image Picker */ }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Add Photo",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Add Photo",
            style = MaterialTheme.typography.labelLarge,
            color = PrimaryBlue,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            ProfileLabel("Phone Number")
            ProfileTextField(
                value = phoneNumber,
                onValueChange = { 
                    phoneNumber = it
                    if (phoneNumberError != null) phoneNumberError = null
                },
                placeholder = "+1 (555) 000-0000",
                isError = phoneNumberError != null,
                errorMessage = phoneNumberError
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileLabel("Major / Department")
            ProfileTextField(
                value = major,
                onValueChange = { 
                    major = it
                    if (majorError != null) majorError = null
                },
                placeholder = "e.g. Computer Science",
                isError = majorError != null,
                errorMessage = majorError
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileLabel("Expected Graduation Year")
            ExposedDropdownMenuBox(
                expanded = expGradYearExpanded,
                onExpandedChange = { expGradYearExpanded = !expGradYearExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedExpGradYear,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select year", color = Color.LightGray) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expGradYearExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = expGradYearError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFF0F0F0),
                        focusedBorderColor = PrimaryBlue,
                        errorBorderColor = Color(0xFFD32F2F),
                        unfocusedContainerColor = Color(0xFFFBFBFB),
                        focusedContainerColor = Color(0xFFFBFBFB),
                        errorContainerColor = Color(0xFFFFF3F3)
                    )
                )
                ExposedDropdownMenu(
                    expanded = expGradYearExpanded,
                    onDismissRequest = { expGradYearExpanded = false }
                ) {
                    gradYears.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedExpGradYear = selectionOption
                                expGradYearExpanded = false
                                if (expGradYearError != null) expGradYearError = null
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
            AnimatedErrorText(expGradYearError)

            Spacer(modifier = Modifier.height(24.dp))

            ProfileLabel("Current Year")
            ExposedDropdownMenuBox(
                expanded = currentYearExpanded,
                onExpandedChange = { currentYearExpanded = !currentYearExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedCurrentYear,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select year", color = Color.LightGray) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = currentYearExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = currentYearError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFF0F0F0),
                        focusedBorderColor = PrimaryBlue,
                        errorBorderColor = Color(0xFFD32F2F),
                        unfocusedContainerColor = Color(0xFFFBFBFB),
                        focusedContainerColor = Color(0xFFFBFBFB),
                        errorContainerColor = Color(0xFFFFF3F3)
                    )
                )
                ExposedDropdownMenu(
                    expanded = currentYearExpanded,
                    onDismissRequest = { currentYearExpanded = false }
                ) {
                    currentYears.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedCurrentYear = selectionOption
                                currentYearExpanded = false
                                if (currentYearError != null) currentYearError = null
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
            AnimatedErrorText(currentYearError)

            Spacer(modifier = Modifier.height(24.dp))

            ProfileLabel("About Me")
            OutlinedTextField(
                value = bio,
                onValueChange = { if (it.length <= 200) bio = it },
                placeholder = { Text("Tell us about your interests and career goals...", color = Color.LightGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF0F0F0),
                    focusedBorderColor = PrimaryBlue,
                    unfocusedContainerColor = Color(0xFFFBFBFB),
                    focusedContainerColor = Color(0xFFFBFBFB)
                )
            )
            Text(
                text = "${bio.length}/200",
                style = MaterialTheme.typography.labelSmall,
                color = TextBody.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Complete Profile Button
        Button(
            onClick = { validateAndComplete() },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = "Complete Profile",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSkip) {
            Text(
                text = "Skip for now",
                style = MaterialTheme.typography.labelLarge,
                color = TextBody,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ProfileLabel(text: String) {
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
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isError,
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
        AnimatedErrorText(errorMessage)
    }
}

@Composable
fun AnimatedErrorText(errorMessage: String?) {
    AnimatedVisibility(
        visible = errorMessage != null,
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
                text = errorMessage ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFD32F2F)
            )
        }
    }
}
