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
fun ConnectSucceedScreen(onGetStarted: () -> Unit, onSkip: () -> Unit) {
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

        // Onboarding Image Container
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
                    painter = painterResource(id = R.drawable.onboarding_img_3),
                    contentDescription = "Connect & Succeed Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Indicator Dots (3rd dot active)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFE0E0E0)))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFE0E0E0)))
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(PrimaryBlue)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Text Content
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(800, delayMillis = 300)) + slideInVertically(initialOffsetY = { 40 })
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Connect & \nSucceed",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextTitle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ask questions, get answers, and prepare confidently for your dream job.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextBody,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.labelLarge,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}
