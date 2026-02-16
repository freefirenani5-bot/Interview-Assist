package com.simats.interviewassist.ui.screens.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.R
import com.simats.interviewassist.ui.theme.PrimaryBlue
import com.simats.interviewassist.ui.theme.TextBody
import com.simats.interviewassist.ui.theme.TextTitle

@Composable
fun WelcomeScreen(onNext: () -> Unit, onSkip: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Image Container with simple animation
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1000)) + expandVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color(0xFFF5F5F5)) 
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_img),
                    contentDescription = "Welcome Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Indicator Dots
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(PrimaryBlue)
            )
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFE0E0E0)))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFE0E0E0)))
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Text Content with fade-in
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(800, delayMillis = 300)) + slideInVertically(initialOffsetY = { 40 })
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Welcome to \nInterview Assist",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextTitle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your ultimate companion for cracking interviews with real insights from alumni.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextBody,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Next Button with slightly improved interaction
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSkip) {
            Text(
                text = "Skip",
                style = MaterialTheme.typography.labelLarge,
                color = TextBody,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
