package com.simats.interviewassist.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.interviewassist.ui.theme.*
import com.simats.interviewassist.R
import androidx.compose.runtime.mutableStateListOf

object UserQuestionsManager {
    val userQuestions = mutableStateListOf<Question>()

    fun addQuestion(companyName: String, questionText: String, askedBy: String = "You") {
        userQuestions.add(0, Question(
            questionText = questionText,
            askedBy = askedBy,
            date = "Just now",
            answers = emptyList(),
            companyName = companyName // Added field for tracking
        ))
    }
}

data class SavedExperienceItem(
    val experience: InterviewExperience,
    val companyName: String,
    val savedAt: Long = System.currentTimeMillis()
)

object SavedExperiencesManager {
    val savedExperiences = mutableStateListOf<SavedExperienceItem>()

    fun toggleSave(experience: InterviewExperience, companyName: String) {
        val existing = savedExperiences.find { it.experience.id == experience.id }
        if (existing != null) {
            savedExperiences.remove(existing)
        } else {
            savedExperiences.add(0, SavedExperienceItem(experience, companyName))
        }
    }

    fun isSaved(id: String): Boolean {
        return savedExperiences.any { it.experience.id == id }
    }
}

object HelpfulExperiencesManager {
    private val helpfulIds = mutableStateListOf<String>()

    fun toggleHelpful(experienceId: String) {
        if (helpfulIds.contains(experienceId)) {
            helpfulIds.remove(experienceId)
        } else {
            helpfulIds.add(experienceId)
        }
    }

    fun isHelpful(experienceId: String): Boolean {
        return helpfulIds.contains(experienceId)
    }
}

data class ExamSection(val name: String, val questions: Int, val time: String, val level: String)
data class ProcessStep(val title: String, val duration: String)

data class InterviewExperience(
    val id: String = java.util.UUID.randomUUID().toString(),
    val userName: String,
    val userRole: String,
    val isUserVerified: Boolean = false,
    val difficulty: String,
    val date: String,
    val isSelected: Boolean,
    val workMode: String,
    val candidateType: String,
    val myExperience: String, // New field
    val content: String,
    val helpfulCount: Int,
    val brief: String = "",
    val applicationProcess: String = "",
    val interviewRounds: List<ProcessStep> = emptyList(),
    val technicalQuestions: List<String> = emptyList(),
    val behavioralQuestions: List<String> = emptyList(),
    val mistakes: List<String> = emptyList(),
    val preparationStrategy: Map<String, List<String>> = emptyMap(),
    val finalAdvice: List<String> = emptyList()
)

data class Answer(
    val id: String = java.util.UUID.randomUUID().toString(),
    val answererName: String,
    val answererRole: String,
    val answererProfileImage: Int? = null, // Optional resource ID
    val isVerifiedAlumni: Boolean = false,
    val answerText: String,
    val date: String
)

data class Question(
    val id: String = java.util.UUID.randomUUID().toString(),
    val questionText: String,
    val askedBy: String,
    val date: String,
    val answers: List<Answer> = emptyList(),
    val companyName: String? = null // For tracking user questions
)

data class Company(
    val name: String,
    val location: String,
    val sector: String,
    val logoResId: Int,
    val difficulty: String,
    val description: String = "",
    val websiteUrl: String = "",
    val experiencesCount: Int = 0,
    val selectedCount: Int = 0,
    val examPattern: List<ExamSection> = emptyList(),
    val hiringProcess: List<ProcessStep> = emptyList(),
    val experiences: List<InterviewExperience> = emptyList(),
    val questions: List<Question> = emptyList(),
    val userQuestions: List<Question> = emptyList() // Added for dynamic merge
)

@Composable
fun CompanyItem(
    company: Company,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8F9FA)
            ) {
                Image(
                    painter = painterResource(id = company.logoResId),
                    contentDescription = company.name,
                    modifier = Modifier.padding(12.dp)
                )
            }
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = company.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextTitle
                    )
                    Surface(
                        color = when(company.difficulty) {
                            "Easy" -> Color(0xFF10B981).copy(alpha = 0.1f)
                            "Medium" -> Color(0xFFF59E0B).copy(alpha = 0.1f)
                            "Hard" -> Color(0xFFEF4444).copy(alpha = 0.1f)
                            else -> Color(0xFFE5E7EB)
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = company.difficulty,
                            fontSize = 10.sp,
                            color = when(company.difficulty) {
                                "Easy" -> Color(0xFF10B981)
                                "Medium" -> Color(0xFFF59E0B)
                                "Hard" -> Color(0xFFEF4444)
                                "All" -> Color(0xFF6B7280)
                                else -> Color(0xFF4B5563)
                            },
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(14.dp), tint = TextBody)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = company.location,
                            fontSize = 14.sp,
                            color = TextBody
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.BusinessCenter, null, modifier = Modifier.size(14.dp), tint = TextBody)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = company.sector,
                            fontSize = 14.sp,
                            color = TextBody
                        )
                    }
                }
            }
            
            Icon(
                Icons.Default.ChevronRight,
                null,
                tint = Color(0xFFD1D5DB)
            )
        }
    }
}

fun getMockCompany(name: String): Company {
    return when(name) {
        "Cognizant" -> Company(
            name = "Cognizant",
            location = "Teaneck, NJ",
            sector = "IT Services",
            logoResId = R.drawable.cognizant_logo,
            difficulty = "Medium",
            description = "American multinational information technology services and consulting company.",
            websiteUrl = "https://cognizant.com",
            experiencesCount = 4,
            selectedCount = 3,
            examPattern = listOf(
                ExamSection("Aptitude", 25, "35 mins", "Easy"),
                ExamSection("Verbal", 20, "20 mins", "Easy"),
                ExamSection("Logical", 35, "45 mins", "Medium"),
                ExamSection("Automata Fix", 7, "20 mins", "Medium")
            ),
            hiringProcess = listOf(
                ProcessStep("GenC Assessment", "1 week"),
                ProcessStep("Technical Interview", "1 week"),
                ProcessStep("HR Discussion", "3 days")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Rahul Sharma",
                    userRole = "Programmer Analyst",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "1 week ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "My journey with Cognizant started with the on-campus drive for the GenC profile. The first round was an aptitude test on the Amcat platform, covering quantitative ability, logical reasoning, and verbal ability. The 'Automata Fix' section was uniqueâ€”we had to debug code snippets in C/C++, which tested our understanding of syntax and logic.\n\nAfter clearing the aptitude round, I was called for the Technical Interview. The interviewer was friendly and started with questions about my final year project. Since I used Java, he drilled down into OOPS concepts like Polymorphism and Inheritance. He asked me to write a code snippet for method overriding on paper. Then came questions on SQL joins and normalization.\n\nThe HR round was straightforward. They asked about my family background, willingness to relocate to any of their locations across India, and shift flexibility. Overall, the process was smooth, but you need to be strong in your basics.",
                    content = "My journey with Cognizant started with the on-campus drive for the GenC profile. The first round was an aptitude test on the Amcat platform, covering quantitative ability, logical reasoning, and verbal ability. The 'Automata Fix' section was uniqueâ€”we had to debug code snippets in C/C++, which tested our understanding of syntax and logic.\n\nAfter clearing the aptitude round, I was called for the Technical Interview. The interviewer was friendly and started with questions about my final year project. Since I used Java, he drilled down into OOPS concepts like Polymorphism and Inheritance. He asked me to write a code snippet for method overriding on paper. Then came questions on SQL joins and normalization.\n\nThe HR round was straightforward. They asked about my family background, willingness to relocate to any of their locations across India, and shift flexibility. Overall, the process was smooth, but you need to be strong in your basics.",
                    helpfulCount = 10,
                    brief = "CSE Grad, 2024 Batch.",
                    applicationProcess = "On-campus drive.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Aptitude", "Quantitative & Logical"),
                        ProcessStep("Round 2: Technical", "Java Basics & Project")
                    ),
                    technicalQuestions = listOf(
                        "Explain Polymorphism with real-world example.",
                        "Difference between ArrayList and LinkedList."
                    ),
                    behavioralQuestions = listOf(
                        "Are you willing to relocate?"
                    ),
                    mistakes = listOf(
                        "Got stuck in one logical reasoning question."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("IndiaBix", "FacePrep"),
                        "Focus" to listOf("Aptitude speed")
                    ),
                    finalAdvice = listOf(
                        "Focus on speed for aptitude."
                    )
                ),
                InterviewExperience(
                    userName = "Priya M",
                    userRole = "Programmer Analyst Trainee",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "2 weeks ago",
                    isSelected = false,
                    workMode = "Hybrid",
                    candidateType = "fresher",
                    myExperience = "I applied through an off-campus drive organized by eLitmus. I was really excited but also quite nervous. The aptitude round was the first hurdle, and honestly, it was harder than I anticipated. The Quants section had some tricky time-speed-distance problems that consumed a lot of my time.\n\nWhile I managed to clear the cutoff for the verbal section easily, I struggled with the logical reasoning puzzles. I missed the overall cutoff by a small margin. It was a tough lesson in time management. If you are preparing, don't just focus on solving the problem, focus on solving it quickly.",
                    content = "I applied through an off-campus drive organized by eLitmus. I was really excited but also quite nervous. The aptitude round was the first hurdle, and honestly, it was harder than I anticipated. The Quants section had some tricky time-speed-distance problems that consumed a lot of my time.\n\nWhile I managed to clear the cutoff for the verbal section easily, I struggled with the logical reasoning puzzles. I missed the overall cutoff by a small margin. It was a tough lesson in time management. If you are preparing, don't just focus on solving the problem, focus on solving it quickly.",
                    helpfulCount = 5,
                    brief = "B.Tech IT, 2024 Batch.",
                    applicationProcess = "Off-campus drive via eLitmus.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Aptitude", "Quants, Logical, Verbal")
                    ),
                    technicalQuestions = emptyList(),
                    behavioralQuestions = emptyList(),
                    mistakes = listOf(
                        "Spent too much time on a single puzzle.",
                        "Didn't manage time well in Quants."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("R.S. Aggarwal", "Indiabix"),
                        "Focus" to listOf("Time Management")
                    ),
                    finalAdvice = listOf(
                        "Skip tough questions and come back later."
                    )
                ),
                InterviewExperience(
                    userName = "Arjun K",
                    userRole = "Associate",
                    isUserVerified = false,
                    difficulty = "Easy",
                    date = "3 weeks ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "experienced",
                    myExperience = "Coming in as an experienced hire with 3 years of Java development under my belt, the process was quite different from the fresher drives. I was referred by a friend.\n\nThe first round was a technical discussion that felt more like a peer-to-peer conversation than an interview. We discussed my current project architecture, why we chose Microservices over Monolithic, and how we handled inter-service communication.\n\nThe interviewer asked me to explain the internal working of HashMaps and concurrent collections in Java. Since I worked on Spring Boot, there were questions on dependency injection and actuator endpoints. The managerial round focused on why I wanted to leave my current company. Be honest and keep it professional.",
                    content = "Coming in as an experienced hire with 3 years of Java development under my belt, the process was quite different from the fresher drives. I was referred by a friend.\n\nThe first round was a technical discussion that felt more like a peer-to-peer conversation than an interview. We discussed my current project architecture, why we chose Microservices over Monolithic, and how we handled inter-service communication.\n\nThe interviewer asked me to explain the internal working of HashMaps and concurrent collections in Java. Since I worked on Spring Boot, there were questions on dependency injection and actuator endpoints. The managerial round focused on why I wanted to leave my current company. Be honest and keep it professional.",
                    helpfulCount = 8,
                    brief = "Java Developer with 3 years experience.",
                    applicationProcess = "Referral from a friend.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Technical", "Java & Spring Boot"),
                        ProcessStep("Round 2: Managerial", "Project discussion"),
                        ProcessStep("Round 3: HR", "Salary negotiation")
                    ),
                    technicalQuestions = listOf(
                        "Explain Spring Boot architecture.",
                        "How to handle exceptions in Spring?",
                        "Microservices communication patterns."
                    ),
                    behavioralQuestions = listOf(
                        "Why do you want to switch companies?",
                        "Describe a conflict with your manager."
                    ),
                    mistakes = listOf(
                        "None."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("Spring documentation", "Baeldung"),
                        "Focus" to listOf("System Design basics")
                    ),
                    finalAdvice = listOf(
                        "Be honest about what you know and don't know."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "What is the difference between GenC and GenC Next?",
                    askedBy = "Suresh K",
                    date = "2 weeks ago",
                    answers = listOf(
                        Answer(
                            answererName = "Rahul Sharma",
                            answererRole = "PAT at Cognizant",
                            isVerifiedAlumni = true,
                            answerText = "GenC is the standard profile (4 LPA), GenC Next is the higher package (6.75 LPA) which requires advanced coding skills.",
                            date = "1 week ago"
                        )
                    )
                ),
                Question(
                    questionText = "Is C# used in Cognizant?",
                    askedBy = "David G",
                    date = "3 days ago",
                    answers = listOf(
                        Answer(
                            answererName = "Arjun K",
                            answererRole = "Associate",
                            isVerifiedAlumni = true,
                            answerText = "Yes, Cognizant has many projects on the Microsoft stack (.NET).",
                            date = "1 day ago"
                        )
                    )
                )
            )
        )
        "TCS" -> Company(
            name = "TCS",
            location = "Mumbai, India",
            sector = "IT Services",
            logoResId = R.drawable.tcs_logo,
            difficulty = "Easy",
            description = "Part of the Tata group, India's largest multinational business group.",
            websiteUrl = "https://tcs.com",
            experiencesCount = 10,
            selectedCount = 8,
            examPattern = listOf(
                ExamSection("Numerical Ability", 26, "40 mins", "Medium"),
                ExamSection("Verbal Ability", 24, "30 mins", "Easy"),
                ExamSection("Reasoning Ability", 30, "50 mins", "Medium"),
                ExamSection("Coding", 2, "45 mins", "Medium")
            ),
            hiringProcess = listOf(
                ProcessStep("NQT Exam", "2 weeks"),
                ProcessStep("Technical Round", "1 week"),
                ProcessStep("MR Round", "3 days"),
                ProcessStep("HR Round", "3 days")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Anjali Gupta",
                    userRole = "System Engineer",
                    isUserVerified = true,
                    difficulty = "Easy",
                    date = "3 days ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "TCS conducts its National Qualifier Test (NQT) which is the gateway for multiple roles like Ninja and Digital. I prepared for about a month focusing on aptitude and basic coding.\n\nThe NQT exam was divided into two sections: Foundation and Advanced. The Foundation section had basic aptitude and verbal questions, while the Advanced section had tougher coding problems. Clearing the Advanced section is key for the Digital profile interview call.\n\nMy interview was a combined Technical and HR round. The panel asked me to introduce myself and then moved to questions on DBMS (SQL queries for joins) and C pointers. Since I mentioned Python in my resume, they asked about list comprehensions. The Managerial part was about situational questions like 'How would you handle a deadline if a team member is sick?'.\n\nIt was a positive experience. The interviewers were encouraging and made me feel comfortable.",
                    content = "TCS conducts its National Qualifier Test (NQT) which is the gateway for multiple roles like Ninja and Digital. I prepared for about a month focusing on aptitude and basic coding.\n\nThe NQT exam was divided into two sections: Foundation and Advanced. The Foundation section had basic aptitude and verbal questions, while the Advanced section had tougher coding problems. Clearing the Advanced section is key for the Digital profile interview call.\n\nMy interview was a combined Technical and HR round. The panel asked me to introduce myself and then moved to questions on DBMS (SQL queries for joins) and C pointers. Since I mentioned Python in my resume, they asked about list comprehensions. The Managerial part was about situational questions like 'How would you handle a deadline if a team member is sick?'.\n\nIt was a positive experience. The interviewers were encouraging and made me feel comfortable.",
                    helpfulCount = 45,
                    brief = "IT Graduate.",
                    applicationProcess = "Applied via TCS NextStep.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: NQT", "National Qualifier Test"),
                        ProcessStep("Round 2: TR+MR+HR", "Combined interview")
                    ),
                    technicalQuestions = listOf(
                        "Write a query to find the second highest salary.",
                        "What are pointers in C?"
                    ),
                    behavioralQuestions = listOf(
                        "Why TCS?"
                    ),
                    mistakes = listOf(
                        "None."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("TCS NQT past papers"),
                        "Focus" to listOf("NQT Score")
                    ),
                    finalAdvice = listOf(
                        "Score high in NQT for Digital profile."
                    )
                ),
                InterviewExperience(
                    userName = "Rohan Das",
                    userRole = "System Engineer",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "1 week ago",
                    isSelected = true,
                    workMode = "Remote",
                    candidateType = "fresher",
                    myExperience = "I had explicitly targeted the specific 'Digital' profile which offers a higher package. To get this, I knew I had to ace the coding section in CodeVita. I solved 2 out of 3 questions completely.\n\nThe coding interview was intense. They asked me to optimize my CodeVita solution further. Then they gave me a Dynamic Programming problem related to the 'Coin Change' problem. It took me a while, but I derived the recurrence relation.\n\nApart from coding, they grilled me on Operating Systems concepts like Deadlocks, Semaphores, and Paging. It was a proper Computer Science interview. If you are aiming for Digital, treat it like a product-company interview.",
                    content = "I had explicitly targeted the specific 'Digital' profile which offers a higher package. To get this, I knew I had to ace the coding section in CodeVita. I solved 2 out of 3 questions completely.\n\nThe coding interview was intense. They asked me to optimize my CodeVita solution further. Then they gave me a Dynamic Programming problem related to the 'Coin Change' problem. It took me a while, but I derived the recurrence relation.\n\nApart from coding, they grilled me on Operating Systems concepts like Deadlocks, Semaphores, and Paging. It was a proper Computer Science interview. If you are aiming for Digital, treat it like a product-company interview.",
                    helpfulCount = 20,
                    brief = "CSE, 2024 Batch.",
                    applicationProcess = "TCS CodeVita.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: CodeVita", "Coding Contest"),
                        ProcessStep("Round 2: Technical Interview", "Advanced Coding & CS Fundamentals")
                    ),
                    technicalQuestions = listOf(
                        "Explain deadlock conditions.",
                        "Difference between TCP and UDP.",
                        "Implement QuickSort."
                    ),
                    behavioralQuestions = listOf(
                        "Where do you see yourself in 5 years?"
                    ),
                    mistakes = listOf(
                        "Stumbled on the networking questions."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("GateSmashers", "LeetCode"),
                        "Focus" to listOf("OS, CN, DBMS")
                    ),
                    finalAdvice = listOf(
                        "Participate in CodeVita for direct interview opportunity."
                    )
                ),
                InterviewExperience(
                    userName = "Sneha P",
                    userRole = "Ninja",
                    isUserVerified = false,
                    difficulty = "Easy",
                    date = "2 weeks ago",
                    isSelected = false,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "My experience with the NQT was good, but I faltered in the interview. I had focused entirely on coding practice (arrays, strings) and completely neglected my core subjects.\n\nThe interviewer asked me basic questions on DBMSâ€”specifically about Normalization and SQL joins. I went blank. I couldn't even write a simple 'Select' query with a 'Where' clause correctly because of nervousness. It was embarrassing.\n\nThey respect honesty, but you need to know the basics of your degree. Don't make the mistake I did. Balance your coding practice with theory revision.",
                    content = "My experience with the NQT was good, but I faltered in the interview. I had focused entirely on coding practice (arrays, strings) and completely neglected my core subjects.\n\nThe interviewer asked me basic questions on DBMSâ€”specifically about Normalization and SQL joins. I went blank. I couldn't even write a simple 'Select' query with a 'Where' clause correctly because of nervousness. It was embarrassing.\n\nThey respect honesty, but you need to know the basics of your degree. Don't make the mistake I did. Balance your coding practice with theory revision.",
                    helpfulCount = 5,
                    brief = "BCA Graduate.",
                    applicationProcess = "NQT.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: NQT", "Aptitude"),
                        ProcessStep("Round 2: Technical", "Basics")
                    ),
                    technicalQuestions = listOf(
                        "What is normalization?",
                        "Types of Joins in SQL."
                    ),
                    behavioralQuestions = listOf(
                        "Why IT?"
                    ),
                    mistakes = listOf(
                        "Couldn't write a simple SQL query."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("W3Schools"),
                        "Focus" to listOf("SQL Basics")
                    ),
                    finalAdvice = listOf(
                        "Don't ignore DBMS and SQL."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "Does NQT score expire?",
                    askedBy = "Vihaan",
                    date = "1 month ago",
                    answers = listOf(
                        Answer(
                            answererName = "Anjali Gupta",
                            answererRole = "System Engineer",
                            isVerifiedAlumni = true,
                            answerText = "Yes, it is valid for 2 years.",
                            date = "3 weeks ago"
                        )
                    )
                ),
                Question(
                    questionText = "Is formal uniform mandatory?",
                    askedBy = "Sam W",
                    date = "1 month ago",
                    answers = listOf(
                        Answer(
                            answererName = "Anjali Gupta",
                            answererRole = "System Engineer",
                            isVerifiedAlumni = true,
                            answerText = "No, mostly business casuals. Formals are required only for client meetings or special events.",
                            date = "3 weeks ago"
                        )
                    )
                )
            )
        )
        "Blackstraw" -> Company(
            name = "Blackstraw",
            location = "Chennai, India",
            sector = "AI Solutions",
            logoResId = R.drawable.blackstraw_logo,
            difficulty = "Hard",
            description = "AI and Analytics solutions provider. Focuses on Computer Vision and NLP.",
            websiteUrl = "https://blackstraw.ai",
            experiencesCount = 2,
            selectedCount = 1,
            examPattern = listOf(
                ExamSection("Aptitude", 20, "30 mins", "Medium"),
                ExamSection("Python/C++", 20, "30 mins", "Hard"),
                ExamSection("Coding", 3, "90 mins", "Hard")
            ),
            hiringProcess = listOf(
                ProcessStep("Coding Round", "1 week"),
                ProcessStep("Technical Zoom", "1 week"),
                ProcessStep("Project Task", "1 week")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Karthik R",
                    userRole = "AI Engineer",
                    isUserVerified = true,
                    difficulty = "Hard",
                    date = "5 days ago",
                    isSelected = true,
                    workMode = "Hybrid",
                    candidateType = "experienced",
                    myExperience = "Blackstraw is very particular about mathematical foundations. The first round wasn't your typical LeetCode grind. They asked me to implement the backpropagation algorithm from scratch in Python without using PyTorch or TensorFlow. They wanted to see if I understood the calculus behind the gradients.\n\nThe second round was a practical task. I was given a dataset and asked to build a small model, wrap it in a Flask API, and containerize it with Docker. They checked for code modularity, error handling, and latency optimization. It was one of the most intellectually stimulating interviews I've had.",
                    content = "Blackstraw is very particular about mathematical foundations. The first round wasn't your typical LeetCode grind. They asked me to implement the backpropagation algorithm from scratch in Python without using PyTorch or TensorFlow. They wanted to see if I understood the calculus behind the gradients.\n\nThe second round was a practical task. I was given a dataset and asked to build a small model, wrap it in a Flask API, and containerize it with Docker. They checked for code modularity, error handling, and latency optimization. It was one of the most intellectually stimulating interviews I've had.",
                    helpfulCount = 12,
                    brief = "Data Scientist with 2 years xp.",
                    applicationProcess = "LinkedIn Easy Apply.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Coding", "Data Structures & AI Math"),
                        ProcessStep("Round 2: API Task", "Build a serving layer")
                    ),
                    technicalQuestions = listOf(
                        "Explain Backpropagation calculus.",
                        "Optimize a Python script using Multiprocessing."
                    ),
                    behavioralQuestions = listOf(
                        "Describe a time you optimized a model."
                    ),
                    mistakes = listOf(
                        "Forgot to handle exceptions in the API."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("Andrew Ng Coursera", "FastAI"),
                        "Focus" to listOf("Deep Learning fundamentals")
                    ),
                    finalAdvice = listOf(
                        "Don't rely just on libraries, know the math."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "Is there a bond at Blackstraw?",
                    askedBy = "Siddharth M",
                    date = "1 week ago",
                    answers = listOf(
                        Answer(
                            answererName = "Karthik R",
                            answererRole = "AI Engineer at Blackstraw",
                            isVerifiedAlumni = true,
                            answerText = "No, there is absolutely no bond. They believe in retaining talent through good work culture.",
                            date = "3 days ago"
                        )
                    )
                ),
                Question(
                    questionText = "Can I use Python for the coding round?",
                    askedBy = "Neha K",
                    date = "2 weeks ago",
                    answers = listOf(
                        Answer(
                            answererName = "HR Team",
                            answererRole = "Recruiter",
                            isVerifiedAlumni = false,
                            answerText = "Yes, Python is highly preferred, especially for AI/ML roles. Libraries like NumPy are allowed.",
                            date = "1 week ago"
                        )
                    )
                )
            )
        )
        "Hexaware" -> Company(
            name = "Hexaware",
            location = "Navi Mumbai, India",
            sector = "IT Services",
            logoResId = R.drawable.hexaware_logo,
            difficulty = "Medium",
            description = "Fast-growing automation-led next-generation service provider.",
            websiteUrl = "https://hexaware.com",
            experiencesCount = 3,
            selectedCount = 2,
            examPattern = listOf(
                ExamSection("Aptitude", 20, "20 mins", "Medium"),
                ExamSection("Domain Test", 20, "20 mins", "Medium"),
                ExamSection("Coding", 2, "45 mins", "Medium")
            ),
            hiringProcess = listOf(
                ProcessStep("Online Test", "1 week"),
                ProcessStep("Technical Interview", "1 week"),
                ProcessStep("Communication Test", "3 days"),
                ProcessStep("HR", "3 days")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Sneha Reddy",
                    userRole = "GET",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "2 weeks ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "Hexaware's process is quite streamlined. The elimination generally happens in the first round which is a combination of Aptitude, Domain-based technical questions, and Pseudocode.\n\nThe unique aspect of Hexaware is the 'Versant' test, which tests your communication skills. You need to speak clearly into a microphone, repeat sentences, and answer quick questions. A lot of students get rejected here because of background noise or unclear pronunciation. My technical interview was standardâ€”Basic C programming loops and some SQL queries.",
                    content = "Hexaware's process is quite streamlined. The elimination generally happens in the first round which is a combination of Aptitude, Domain-based technical questions, and Pseudocode.\n\nThe unique aspect of Hexaware is the 'Versant' test, which tests your communication skills. You need to speak clearly into a microphone, repeat sentences, and answer quick questions. A lot of students get rejected here because of background noise or unclear pronunciation. My technical interview was standardâ€”Basic C programming loops and some SQL queries.",
                    helpfulCount = 8,
                    brief = "ECE Grad.",
                    applicationProcess = "Campus.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Online", "Aptitude + Coding"),
                        ProcessStep("Round 2: Technical", "Basics of C/C++"),
                        ProcessStep("Round 3: Versant", "Speaking skills")
                    ),
                    technicalQuestions = listOf(
                        "Palindrome program.",
                        "What is a Class?"
                    ),
                    behavioralQuestions = listOf(
                        "Tell me about yourself."
                    ),
                    mistakes = listOf(
                        "Stuttered during Versant due to mic issue."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("PrepInsta"),
                        "Focus" to listOf("Communication")
                    ),
                    finalAdvice = listOf(
                        "Check your microphone before the test."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "Is the Versant test difficult?",
                    askedBy = "Amit S",
                    date = "3 days ago",
                    answers = listOf(
                        Answer(
                            answererName = "Sneha Reddy",
                            answererRole = "GET at Hexaware",
                            isVerifiedAlumni = true,
                            answerText = "It is not difficult but strict. Ensure you are in a completely silent room. Background noise can fail you.",
                            date = "1 day ago"
                        )
                    )
                ),
                Question(
                    questionText = "What is the training location?",
                    askedBy = "Pooja D",
                    date = "1 week ago",
                    answers = listOf(
                        Answer(
                            answererName = "Rahul V",
                            answererRole = "PGET",
                            isVerifiedAlumni = true,
                            answerText = "Training usually happens in Chennai, Pune, or Mumbai depending on your business unit.",
                            date = "4 days ago"
                        )
                    )
                )
            )
        )
        "Zoho" -> Company(
            name = "Zoho",
            location = "Chennai, India",
            sector = "Software",
            logoResId = R.drawable.zoho_logo,
            difficulty = "Hard",
            description = "Indian multinational technology company that makes web-based business tools.",
            websiteUrl = "https://zoho.com",
            experiencesCount = 6,
            selectedCount = 2,
            examPattern = listOf(
                ExamSection("C Programming", 15, "30 mins", "Hard"),
                ExamSection("Aptitude", 10, "20 mins", "Medium"),
                ExamSection("Coding", 5, "90 mins", "Hard")
            ),
            hiringProcess = listOf(
                ProcessStep("Written Test", "1 day"),
                ProcessStep("Machine Coding", "1 day"),
                ProcessStep("Advanced Coding", "1 day"),
                ProcessStep("Tech HR", "1 day")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Arun Kumar",
                    userRole = "Software Developer",
                    isUserVerified = true,
                    difficulty = "Hard",
                    date = "1 month ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "Zoho is famous for its unconventional interview style. They don't care about your degree or marks, only your logic. Round 1 was C programming output guessingâ€”tricky pointer arithmetic questions.\n\nRound 2 was Machine Coding. I was asked to implement string manipulation functions like `substring`, `split`, and `reverse` in C *without* using any built-in library functions (no `<string.h>`). It really tests if you understand what goes on under the hood.\n\nThe final round was System Design. I had to design a 'Railway Reservation System' console application. I had to handle booking, cancellation, and waiting lists using Object-Oriented principles. It went on for 3 hours!",
                    content = "Zoho is famous for its unconventional interview style. They don't care about your degree or marks, only your logic. Round 1 was C programming output guessingâ€”tricky pointer arithmetic questions.\n\nRound 2 was Machine Coding. I was asked to implement string manipulation functions like `substring`, `split`, and `reverse` in C *without* using any built-in library functions (no `<string.h>`). It really tests if you understand what goes on under the hood.\n\nThe final round was System Design. I had to design a 'Railway Reservation System' console application. I had to handle booking, cancellation, and waiting lists using Object-Oriented principles. It went on for 3 hours!",
                    helpfulCount = 67,
                    brief = "Mechanical Engineering student who loves coding.",
                    applicationProcess = "Walk-in drive.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: C output guessing", "Predict output and pointers"),
                        ProcessStep("Round 2: Basic Programming", "No library functions allowed"),
                        ProcessStep("Round 3: System Design", "Console application design")
                    ),
                    technicalQuestions = listOf(
                        "Sorting an array with minimum swaps.",
                        "Print a specific pattern.",
                        "Design Call Taxi Booking application."
                    ),
                    behavioralQuestions = listOf(
                        "Why coding after Mechanical engineering?"
                    ),
                    mistakes = listOf(
                        "Used a library function by mistake and was warned."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("GeeksForGeeks Zoho Archives"),
                        "Focus" to listOf("Logic building", "System Design (LLD)")
                    ),
                    finalAdvice = listOf(
                        "Practice standard LLD problems like Parking Lot, Railway Reservation."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "Does Zoho look at CGPA?",
                    askedBy = "Deepak",
                    date = "2 weeks ago",
                    answers = listOf(
                        Answer(
                            answererName = "Arun Kumar",
                            answererRole = "Developer at Zoho",
                            isVerifiedAlumni = true,
                            answerText = "Not really. They value skills much more. Even if you have backlogs, if you crack the code, you are in.",
                            date = "1 week ago"
                        )
                    )
                ),
                Question(
                    questionText = "Does Zoho provide food and transport?",
                    askedBy = "Kiran R",
                    date = "1 month ago",
                    answers = listOf(
                        Answer(
                            answererName = "Arun Kumar",
                            answererRole = "Developer at Zoho",
                            isVerifiedAlumni = true,
                            answerText = "Yes! The food is amazing and free for all employees. Cab facilities are also available for specific routes.",
                            date = "3 weeks ago"
                        )
                    )
                )
            )
        )
        "Accenture" -> Company(
            name = "Accenture",
            location = "Baangalore, India",
            sector = "Consulting",
            logoResId = R.drawable.accenture_logo,
            difficulty = "Medium",
            description = "Fortune Global 500 company specializing in IT services and consulting.",
            websiteUrl = "https://accenture.com",
            experiencesCount = 5,
            selectedCount = 3,
            examPattern = listOf(
                ExamSection("Cognitive", 50, "50 mins", "Medium"),
                ExamSection("Technical", 40, "40 mins", "Medium"),
                ExamSection("Coding", 2, "45 mins", "Medium"),
                ExamSection("Communication", 20, "20 mins", "Easy")
            ),
            hiringProcess = listOf(
                ProcessStep("Assessment 1", "1 week"),
                ProcessStep("Assessment 2", "1 week"),
                ProcessStep("Interview", "1 week")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Priya S",
                    userRole = "ASE",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "3 days ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "The recruitment process was quite comprehensive. It started with a Cognitive and Technical assessment triggered on the same platform. Speed is key hereâ€”you need to clear the sectional cutoffs to unlock the coding round in the same sitting.\n\nThe coding questions were surprisingly easy. I got one on finding the GCD of two numbers and another on reversing a string with special characters. The challenge wasn't the code, but the platform constraints.\n\nThe most unique part is the Communication Test. It's an AI-driven test where you listen to sentences and repeat them, or listen to a story and retell it. Make sure you are in a silent room; even a fan noise can affect your score. Finally, the interview was a mix of HR and technical questions.",
                    content = "The recruitment process was quite comprehensive. It started with a Cognitive and Technical assessment triggered on the same platform. Speed is key hereâ€”you need to clear the sectional cutoffs to unlock the coding round in the same sitting.\n\nThe coding questions were surprisingly easy. I got one on finding the GCD of two numbers and another on reversing a string with special characters. The challenge wasn't the code, but the platform constraints.\n\nThe most unique part is the Communication Test. It's an AI-driven test where you listen to sentences and repeat them, or listen to a story and retell it. Make sure you are in a silent room; even a fan noise can affect your score. Finally, the interview was a mix of HR and technical questions.",
                    helpfulCount = 15,
                    brief = "B.Tech CSE.",
                    applicationProcess = "On-campus.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Cognitive/Tech", "MCQs"),
                        ProcessStep("Round 2: Coding", "2 Easy problems"),
                        ProcessStep("Round 3: Communication", "AI based audio test")
                    ),
                    technicalQuestions = listOf(
                        "Difference between C and C++.",
                        "What is cloud computing?"
                    ),
                    behavioralQuestions = listOf(
                        "Willingness to work in shifts."
                    ),
                    mistakes = listOf(
                        "None."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("CareerRide"),
                        "Focus" to listOf("Basics")
                    ),
                    finalAdvice = listOf(
                        "Ensure a quiet environment for the comms test."
                    )
                ),
                InterviewExperience(
                    userName = "Amit K",
                    userRole = "FSE",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "1 week ago",
                    isSelected = true,
                    workMode = "Hybrid",
                    candidateType = "experienced",
                    myExperience = "I was headhunted via LinkedIn for a Full Stack Engineer (FSE) role. The process differs significantly from entry-level roles. There was no aptitude test.\n\nThe first technical round was a deep dive into JavaScript. Closures, Event Loop, Promises, and the 'this' keyword were discussed in depth. They gave me a React scenario to debug live on screen.\n\nThe second round focused on the backend. I had to design a schema for an E-commerce order system and write optimized SQL queries. We also discussed different caching strategies (Redis) for high-traffic APIs. The System Design round was about architecting a scalable Notification Service. If you have experience, focus on your architectural decisions.",
                    content = "I was headhunted via LinkedIn for a Full Stack Engineer (FSE) role. The process differs significantly from entry-level roles. There was no aptitude test.\n\nThe first technical round was a deep dive into JavaScript. Closures, Event Loop, Promises, and the 'this' keyword were discussed in depth. They gave me a React scenario to debug live on screen.\n\nThe second round focused on the backend. I had to design a schema for an E-commerce order system and write optimized SQL queries. We also discussed different caching strategies (Redis) for high-traffic APIs. The System Design round was about architecting a scalable Notification Service. If you have experience, focus on your architectural decisions.",
                    helpfulCount = 10,
                    brief = "Full Stack Developer, 4 years exp.",
                    applicationProcess = "LinkedIn Recruiter reachout.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Technical 1", "JS & React"),
                        ProcessStep("Round 2: Technical 2", "Node & SQL"),
                        ProcessStep("Round 3: System Design", "LLD & HLD")
                    ),
                    technicalQuestions = listOf(
                        "Explain useEffect hook dependencies.",
                        "How Node handles concurrency?",
                        "Index vs Primary Key."
                    ),
                    behavioralQuestions = listOf(
                        "Describe a challenging bug you fixed."
                    ),
                    mistakes = listOf(
                        "None."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("React Docs", "System Design Interview by Alex Xu"),
                        "Focus" to listOf("Frontend Architecture")
                    ),
                    finalAdvice = listOf(
                        "Know your tools inside out."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "Can I choose my preferred location?",
                    askedBy = "Riya S",
                    date = "1 week ago",
                    answers = listOf(
                        Answer(
                            answererName = "Priya S",
                            answererRole = "ASE at Accenture",
                            isVerifiedAlumni = true,
                            answerText = "You can give 3 preferences, but allocation depends on business requirements. Usually, they try to accommodate.",
                            date = "2 days ago"
                        )
                    )
                ),
                Question(
                    questionText = "Is the coding round elimination?",
                    askedBy = "Vikram J",
                    date = "2 weeks ago",
                    answers = listOf(
                        Answer(
                            answererName = "Amit K",
                            answererRole = "FSE at Accenture",
                            isVerifiedAlumni = true,
                            answerText = "Yes, strictly elimination. You must clear the coding round to proceed to the interview.",
                            date = "1 week ago"
                        )
                    )
                )
            )
        )
        "Wipro" -> Company(
            name = "Wipro",
            location = "Bangalore, India",
            sector = "IT Services",
            logoResId = R.drawable.wipro_logo,
            difficulty = "Easy",
            description = "Leading global information technology, consulting and business process services company.",
            websiteUrl = "https://wipro.com",
            experiencesCount = 2,
            selectedCount = 1,
            examPattern = listOf(
                ExamSection("Aptitude", 20, "20 mins", "Medium"),
                ExamSection("Logical", 20, "20 mins", "Medium"),
                ExamSection("Verbal", 20, "20 mins", "Easy"),
                ExamSection("Coding", 2, "60 mins", "Medium")
            ),
            hiringProcess = listOf(
                ProcessStep("NLTH Exam", "2 weeks"),
                ProcessStep("Business Discussion", "1 week")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Sameer K",
                    userRole = "Project Engineer",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "4 weeks ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "Wipro's National Level Talent Hunt (NLTH) is a massive drive. The assessment platform was CoCubes. The aptitude section was manageable, but the 'Essay Writing' part is often underestimated. You need to write grammatically correct sentences on a given topic (mine was 'Digital India').\n\nThe coding round had two questionsâ€”one easy (pattern printing) and one medium (sorting based on conditions). I solved both, but my second code didn't pass all test cases.\n\nThe Technical Interview was surprisingly short. They focused heavily on my resume. Since I mentioned 'Cloud Computing' as an interest, they asked about AWS services (EC2, S3) and the difference between IaaS and PaaS. If you put something on your resume, be ready to defend it.",
                    content = "Wipro's National Level Talent Hunt (NLTH) is a massive drive. The assessment platform was CoCubes. The aptitude section was manageable, but the 'Essay Writing' part is often underestimated. You need to write grammatically correct sentences on a given topic (mine was 'Digital India').\n\nThe coding round had two questionsâ€”one easy (pattern printing) and one medium (sorting based on conditions). I solved both, but my second code didn't pass all test cases.\n\nThe Technical Interview was surprisingly short. They focused heavily on my resume. Since I mentioned 'Cloud Computing' as an interest, they asked about AWS services (EC2, S3) and the difference between IaaS and PaaS. If you put something on your resume, be ready to defend it.",
                    helpfulCount = 5,
                    brief = "MCA Graduate, 2024.",
                    applicationProcess = "National Level Talent Hunt (NLTH).",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Online Assessment", "Aptitude + Coding + Essay Writing"),
                        ProcessStep("Round 2: Technical Interview", "Project & Java Basics"),
                        ProcessStep("Round 3: HR Interview", "General")
                    ),
                    technicalQuestions = listOf(
                        "Explain the concept of OOPs.",
                        "What is a primary key?",
                        "Difference between C and Java."
                    ),
                    behavioralQuestions = listOf(
                        "Why do you want to join Wipro?",
                        "Are you willing to relocate?"
                    ),
                    mistakes = listOf(
                        "Was nervous during the essay writing."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("PrepInsta", "FacePrep"),
                        "Focus" to listOf("Essay Writing", "Coding patterns")
                    ),
                    finalAdvice = listOf(
                        "Don't ignore the essay writing section, it's an elimination round."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "Is there a service agreement/bond?",
                    askedBy = "Tarun G",
                    date = "3 days ago",
                    answers = listOf(
                        Answer(
                            answererName = "Sameer K",
                            answererRole = "Project Engineer",
                            isVerifiedAlumni = true,
                            answerText = "Yes, for the Project Engineer role, there is usually a 12-month service agreement.",
                            date = "1 day ago"
                        )
                    )
                ),
                Question(
                    questionText = "Does Wipro provide training?",
                    askedBy = "Lata M",
                    date = "5 days ago",
                    answers = listOf(
                        Answer(
                            answererName = "Alumni",
                            answererRole = "Senior Engineer",
                            isVerifiedAlumni = true,
                            answerText = "Yes, the PRP (Project Readiness Program) is very structured and lasts for about 3 months.",
                            date = "2 days ago"
                        )
                    )
                )
            )
        )
        "Infosys" -> Company(
            name = "Infosys",
            location = "Bangalore, India",
            sector = "IT Services",
            logoResId = R.drawable.infosys_logo,
            difficulty = "Medium",
            description = "Global leader in next-generation digital services and consulting.",
            websiteUrl = "https://infosys.com",
            experiencesCount = 5,
            selectedCount = 3,
            examPattern = listOf(
                ExamSection("Reasoning Ability", 15, "25 mins", "Hard"),
                ExamSection("Mathematical Ability", 10, "35 mins", "Medium"),
                ExamSection("Verbal Ability", 20, "20 mins", "Medium"),
                ExamSection("Pseudocode", 5, "10 mins", "Medium"),
                ExamSection("Puzzle Solving", 4, "10 mins", "Hard")
            ),
            hiringProcess = listOf(
                ProcessStep("InfyTQ / HackWithInfy", "Varies"),
                ProcessStep("Technical Interview", "1 week"),
                ProcessStep("HR Interview", "3 days")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Kavya M",
                    userRole = "Systems Engineer Specialist",
                    isUserVerified = true,
                    difficulty = "Hard",
                    date = "2 months ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "I secured the 'Power Programmer' role via HackWithInfy. It's a completely different ball game compared to the regular recruitment. The contest had 3 rounds of coding, and the level was comparable to CodeForces Medium-Hard.\n\nSince I cleared the contest rounds, I was invited directly for the Specialist interview. The interviewer was a senior architect. He didn't ask me basic definitions. Instead, he gave me a real-world problem: 'Design a system to handle million requests per second'. We discussed Load Balancing, Caching, and Database Sharding. He also asked me to whiteboard a Dynamic Programming solution for the Knapsack problem variant.\n\nIt was grueling but satisfying. You need deep knowledge of Data Structures and System Design.",
                    content = "I secured the 'Power Programmer' role via HackWithInfy. It's a completely different ball game compared to the regular recruitment. The contest had 3 rounds of coding, and the level was comparable to CodeForces Medium-Hard.\n\nSince I cleared the contest rounds, I was invited directly for the Specialist interview. The interviewer was a senior architect. He didn't ask me basic definitions. Instead, he gave me a real-world problem: 'Design a system to handle million requests per second'. We discussed Load Balancing, Caching, and Database Sharding. He also asked me to whiteboard a Dynamic Programming solution for the Knapsack problem variant.\n\nIt was grueling but satisfying. You need deep knowledge of Data Structures and System Design.",
                    helpfulCount = 34,
                    brief = "CSE, Top Coder.",
                    applicationProcess = "HackWithInfy Contest.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Coding Contest", "3 questions"),
                        ProcessStep("Round 2: Interview", "Code optimization")
                    ),
                    technicalQuestions = listOf(
                        "Dynamic Programming approach for Knapsack.",
                        "Detect cycle in a graph."
                    ),
                    behavioralQuestions = listOf(
                        "Why Power Programmer?"
                    ),
                    mistakes = listOf(
                        "Suboptimal solution for 3rd question."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("CodeChef", "LeetCode"),
                        "Focus" to listOf("Competitive Programming")
                    ),
                    finalAdvice = listOf(
                        "Participate in HackWithInfy for higher packages."
                    )
                ),
                InterviewExperience(
                    userName = "Meera S",
                    userRole = "Systems Engineer",
                    isUserVerified = true,
                    difficulty = "Easy",
                    date = "3 weeks ago",
                    isSelected = true,
                    workMode = "Hybrid",
                    candidateType = "fresher",
                    myExperience = "I went through the standard campus recruitment process. The online test had sections for Reasoning, Mathematical Ability, and Verbal. The unique part was the 'Pseudocode' section where you have to trace the output of code logic.\n\nThe interview was very friendly. The panel asked me to introduce myself and talk about my final year project. They asked basic questions like 'What is an Interface?', 'Difference between SQL and NoSQL', and 'Explain SDLC'. It was more of a hygiene check to see if I had the potential to learn.",
                    content = "I went through the standard campus recruitment process. The online test had sections for Reasoning, Mathematical Ability, and Verbal. The unique part was the 'Pseudocode' section where you have to trace the output of code logic.\n\nThe interview was very friendly. The panel asked me to introduce myself and talk about my final year project. They asked basic questions like 'What is an Interface?', 'Difference between SQL and NoSQL', and 'Explain SDLC'. It was more of a hygiene check to see if I had the potential to learn.",
                    helpfulCount = 12,
                    brief = "B.Sc CS.",
                    applicationProcess = "On-campus.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Online Test", "Aptitude"),
                        ProcessStep("Round 2: HR/TR", "General")
                    ),
                    technicalQuestions = listOf(
                        "Self introduction."
                    ),
                    behavioralQuestions = listOf(
                        "Are you willing to work in night shifts?"
                    ),
                    mistakes = listOf(
                        "None."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("College training"),
                        "Focus" to listOf("Confidence")
                    ),
                    finalAdvice = listOf(
                        "Be confident."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "Can I re-apply if rejected?",
                    askedBy = "Mohan L",
                    date = "2 weeks ago",
                    answers = listOf(
                        Answer(
                            answererName = "Meera S",
                            answererRole = "Systems Engineer",
                            isVerifiedAlumni = true,
                            answerText = "There is a cooling period of 6 months before you can re-apply.",
                            date = "1 week ago"
                        )
                    )
                ),
                Question(
                    questionText = "Difference between SE and SES roles?",
                    askedBy = "Divya P",
                    date = "1 month ago",
                    answers = listOf(
                        Answer(
                            answererName = "Kavya M",
                            answererRole = "Specialist",
                            isVerifiedAlumni = true,
                            answerText = "SES (Specialist) has a higher package and works on niche technologies like AI, Cloud, and Big Data. SE is the standard role.",
                            date = "2 weeks ago"
                        )
                    )
                )
            )
        )
        "HCL" -> Company(
            name = "HCL",
            location = "Noida, India",
            sector = "IT Services",
            logoResId = R.drawable.hcl_logo,
            difficulty = "Medium",
            description = "Next-generation global technology company that helps enterprises reimagine their businesses.",
            websiteUrl = "https://hcltech.com",
            experiencesCount = 1,
            selectedCount = 1,
            examPattern = listOf(
                ExamSection("Aptitude", 15, "20 mins", "Medium"),
                ExamSection("Logical", 15, "20 mins", "Medium"),
                ExamSection("Technical", 20, "30 mins", "Medium")
            ),
            hiringProcess = listOf(
                ProcessStep("Online Test", "1 week"),
                ProcessStep("Technical Interview", "1 week")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Varun D",
                    userRole = "Software Engineer",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "3 weeks ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "I secured a pre-placement offer (PPO) through the HCL First Career program. The interview was purely based on the internship project I had submitted. I had built a MERN stack application for inventory management.\n\nThe interviewer went through my GitHub repository line-by-line. 'Why did you use Redux here?', 'How are you handling authentication?', 'Explain the JWT token flow'. It wasn't about solving new puzzles, but deeply understanding the code I had already written.\n\nThe HR round was very friendly and focused on company values and long-term goals.",
                    content = "I secured a pre-placement offer (PPO) through the HCL First Career program. The interview was purely based on the internship project I had submitted. I had built a MERN stack application for inventory management.\n\nThe interviewer went through my GitHub repository line-by-line. 'Why did you use Redux here?', 'How are you handling authentication?', 'Explain the JWT token flow'. It wasn't about solving new puzzles, but deeply understanding the code I had already written.\n\nThe HR round was very friendly and focused on company values and long-term goals.",
                    helpfulCount = 3,
                    brief = "B.Tech IT.",
                    applicationProcess = "On-campus placement.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Online Test", "Aptitude + CS Fundamentals"),
                        ProcessStep("Round 2: Technical Interview", "Project deep dive"),
                        ProcessStep("Round 3: HR Interview", "Behavioral")
                    ),
                    technicalQuestions = listOf(
                        "Explain the lifecycle of a React component.",
                        "What is middleware in Node.js?",
                        "Difference between SQL and NoSQL."
                    ),
                    behavioralQuestions = listOf(
                        "Describe a situation where you worked in a team."
                    ),
                    mistakes = listOf(
                        "Couldn't explain the database schema clearly."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("React Documentation", "GeeksForGeeks"),
                        "Focus" to listOf("Project details")
                    ),
                    finalAdvice = listOf(
                        "Know every line of code in your project."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "Do we get stipend during training?",
                    askedBy = "Karan J",
                    date = "4 days ago",
                    answers = listOf(
                        Answer(
                            answererName = "Varun D",
                            answererRole = "Software Engineer",
                            isVerifiedAlumni = true,
                            answerText = "Yes, HCL provides a stipend during the 6-month training period.",
                            date = "2 days ago"
                        )
                    )
                ),
                Question(
                    questionText = "Is the First Career program good?",
                    askedBy = "Simran K",
                    date = "1 week ago",
                    answers = listOf(
                        Answer(
                            answererName = "Alumni",
                            answererRole = "Senior Dev",
                            isVerifiedAlumni = true,
                            answerText = "It is great if you want a guaranteed job with training, but be aware of the fee structure initially involved.",
                            date = "3 days ago"
                        )
                    )
                )
            )
        )
        "Capgemini" -> Company(
            name = "Capgemini",
            location = "Paris, France",
            sector = "Consulting",
            logoResId = R.drawable.capgemini_logo,
            difficulty = "Medium",
            description = "Global leader in partnering with companies to transform and manage their business by harnessing the power of technology.",
            websiteUrl = "https://capgemini.com",
            experiencesCount = 3,
            selectedCount = 2,
            examPattern = listOf(
                ExamSection("Pseudocode", 25, "25 mins", "Medium"),
                ExamSection("English", 25, "25 mins", "Easy"),
                ExamSection("Game-based Aptitude", 4, "20 mins", "Medium"),
                ExamSection("Behavioral", 100, "20 mins", "Easy")
            ),
            hiringProcess = listOf(
                ProcessStep("Online Assessment", "1 week"),
                ProcessStep("Technical Interview", "1 week"),
                ProcessStep("HR Interview", "2 days")
            ),
            experiences = listOf(
                InterviewExperience(
                    userName = "Meera Nair",
                    userRole = "Analyst",
                    isUserVerified = true,
                    difficulty = "Medium",
                    date = "1 month ago",
                    isSelected = true,
                    workMode = "Onsite",
                    candidateType = "fresher",
                    myExperience = "Capgemini has recently introduced a 'Game-based Aptitude' round which is quite fun but tricky. It tests your memory, reaction time, and multitasking abilities. For example, there's a game where you have to remember the sequence of dots on a grid.\n\nAfter clearing the gaming round, there was a Pseudocode round. The technical interview was standardâ€”they asked about the projects mentioned in my resume. Since I knew SQL, they asked me to write queries for finding duplicate records. The HR interview was very casual, focusing on my hobbies and college life.",
                    content = "Capgemini has recently introduced a 'Game-based Aptitude' round which is quite fun but tricky. It tests your memory, reaction time, and multitasking abilities. For example, there's a game where you have to remember the sequence of dots on a grid.\n\nAfter clearing the gaming round, there was a Pseudocode round. The technical interview was standardâ€”they asked about the projects mentioned in my resume. Since I knew SQL, they asked me to write queries for finding duplicate records. The HR interview was very casual, focusing on my hobbies and college life.",
                    helpfulCount = 8,
                    brief = "B.Tech ECE.",
                    applicationProcess = "Off-campus.",
                    interviewRounds = listOf(
                        ProcessStep("Round 1: Introduction", "Resume screening"),
                        ProcessStep("Round 2: Game-based Aptitude", "Memory & Logic games"),
                        ProcessStep("Round 3: Technical/HR", "Combined interview")
                    ),
                    technicalQuestions = listOf(
                        "What is a Join? Types of Joins.",
                        "Explain Inheritance with an example."
                    ),
                    behavioralQuestions = listOf(
                        "Why IT field being an ECE student?"
                    ),
                    mistakes = listOf(
                        "None."
                    ),
                    preparationStrategy = mapOf(
                        "Resources" to listOf("YouTube channels for game aptitude"),
                        "Focus" to listOf("Speed and Accuracy")
                    ),
                    finalAdvice = listOf(
                        "Practice game-based aptitude tests online beforehand."
                    )
                )
            ),
            questions = listOf(
                Question(
                    questionText = "What if I fail the Game-based aptitude?",
                    askedBy = "Rohan B",
                    date = "1 week ago",
                    answers = listOf(
                        Answer(
                            answererName = "Meera Nair",
                            answererRole = "Analyst",
                            isVerifiedAlumni = true,
                            answerText = "It is an elimination round. You must clear it to proceed to the pseudocode round.",
                            date = "2 days ago"
                        )
                    )
                ),
                Question(
                    questionText = "How is the work culture?",
                    askedBy = "Sana F",
                    date = "2 weeks ago",
                    answers = listOf(
                        Answer(
                            answererName = "Alumni",
                            answererRole = "Senior Analyst",
                            isVerifiedAlumni = true,
                            answerText = "Capgemini is known for its 'People First' culture. Very collaborative and good work-life balance.",
                            date = "1 week ago"
                        )
                    )
                )
            )
        )
        else -> Company(
            name = name,
            location = "Global",
            sector = "Industry",
            logoResId = R.drawable.cognizant_logo, // Fallback
            difficulty = "Medium",
            description = "A leading company in its sector.",
            experiencesCount = 0,
            selectedCount = 0
        )
    }
}
