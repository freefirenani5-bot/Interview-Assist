package com.simats.interviewassist.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.TextBody
import com.simats.interviewassist.ui.theme.TextTitle
import com.simats.interviewassist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentAllCompaniesScreen(
    onBack: () -> Unit,
    onNavigateToCompanyDetails: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    
    // Reuse the same company data. Ideally, this would come from a Repository.
    val allCompanies = remember {
        listOf(
            getMockCompany("Cognizant"),
            getMockCompany("TCS"),
            getMockCompany("Blackstraw"),
            getMockCompany("Hexaware"),
            getMockCompany("Zoho"),
            getMockCompany("Accenture"),
            getMockCompany("Wipro"),
            getMockCompany("Infosys"),
            getMockCompany("HCL"),
            getMockCompany("Capgemini")
        )
    }

    val filteredCompanies = remember(searchQuery) {
        allCompanies.filter { company ->
            company.name.contains(searchQuery, ignoreCase = true) ||
            company.sector.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = TextTitle)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "All Companies",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextTitle
                )
            }
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            PaddingValues(24.dp).let {
                Box(modifier = Modifier.padding(horizontal = 24.dp).padding(top = 8.dp)) {
                     TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search companies...", color = TextBody.copy(alpha = 0.5f)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = Color(0xFF9CA3AF)) },
                        singleLine = true
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredCompanies) { company ->
                    CompanyItem(
                        company = company,
                        onClick = { onNavigateToCompanyDetails(company.name) }
                    )
                }
                
                if (filteredCompanies.isEmpty()) {
                    item {
                         Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                            Text("No companies found", color = Color(0xFF9CA3AF))
                        }
                    }
                }
                
                // Bottom spacing
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}
