package com.simats.interviewassist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simats.interviewassist.utils.PreferenceManager
import com.simats.interviewassist.ui.screens.onboarding.ConnectSucceedScreen
import com.simats.interviewassist.ui.screens.onboarding.ExperiencesScreen
import com.simats.interviewassist.ui.screens.onboarding.WelcomeScreen
import com.simats.interviewassist.ui.screens.auth.LoginScreen
import com.simats.interviewassist.ui.screens.auth.ForgotPasswordScreen
import com.simats.interviewassist.ui.screens.auth.EmailSentSuccessScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniHomeScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniCompleteProfileScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniCompanyDetailsScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniExperienceDetailScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniSettingsScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniProfileScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniEditProfileScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniChangePasswordScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniPrivacySecurityScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniHelpSupportScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniPostsScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniAssistScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniShareExperienceScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniEditExperienceScreen
import com.simats.interviewassist.ui.screens.student.StudentHomeScreen
import com.simats.interviewassist.ui.screens.student.NotificationsScreen
import com.simats.interviewassist.ui.screens.student.StudentCreateAccountScreen
import com.simats.interviewassist.ui.screens.student.StudentCompleteProfileScreen
import com.simats.interviewassist.ui.screens.student.StudentSettingsScreen
import com.simats.interviewassist.ui.screens.student.StudentProfileScreen
import com.simats.interviewassist.ui.screens.student.StudentEditProfileScreen
import com.simats.interviewassist.ui.screens.student.StudentHelpSupportScreen
import com.simats.interviewassist.ui.screens.student.StudentPrivacySecurityScreen
import com.simats.interviewassist.ui.screens.student.StudentChangePasswordScreen
import com.simats.interviewassist.ui.screens.student.StudentMyQuestionsScreen
import com.simats.interviewassist.ui.screens.student.BecomeAlumniScreen
import com.simats.interviewassist.ui.screens.student.StudentSavedItemsScreen
import com.simats.interviewassist.ui.screens.student.StudentAskQuestionScreen
import com.simats.interviewassist.ui.screens.student.StudentAllCompaniesScreen
import com.simats.interviewassist.ui.screens.student.StudentCompanyDetailsScreen
import com.simats.interviewassist.ui.screens.student.StudentExperienceDetailScreen
import com.simats.interviewassist.ui.screens.student.Company
import com.simats.interviewassist.ui.screens.student.ExamSection
import com.simats.interviewassist.ui.screens.student.ProcessStep
import com.simats.interviewassist.ui.screens.student.getMockCompany
import com.simats.interviewassist.ui.screens.admin.AdminHomeScreen
import com.simats.interviewassist.ui.screens.admin.AdminDashboardScreen
import com.simats.interviewassist.ui.screens.admin.AdminReviewsScreen
import com.simats.interviewassist.ui.screens.admin.AdminSettingsScreen
import com.simats.interviewassist.ui.screens.admin.AdminChangePasswordScreen
import com.simats.interviewassist.ui.screens.admin.AdminHelpSupportScreen
import com.simats.interviewassist.ui.screens.admin.AdminPrivacySecurityScreen
import com.simats.interviewassist.ui.screens.admin.AdminUsersScreen
import com.simats.interviewassist.ui.screens.admin.AlumniRequestsScreen
import com.simats.interviewassist.ui.screens.admin.AdminProfileScreen
import com.simats.interviewassist.ui.screens.admin.AdminReportsScreen
import com.simats.interviewassist.ui.screens.admin.AdminCompanyDetailsScreen
import com.simats.interviewassist.ui.screens.admin.AdminExperienceDetailScreen
import com.simats.interviewassist.R

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Experiences : Screen("experiences")
    object ConnectSucceed : Screen("connect_succeed")
    object Login : Screen("login")
    object ForgotPassword : Screen("forgot_password")
    object EmailSentSuccess : Screen("email_sent_success")
    object StudentHome : Screen("student_home")
    object AlumniHome : Screen("alumni_home")
    object StudentSignup : Screen("student_signup?initialRole={initialRole}") {
        fun createRoute(initialRole: String) = "student_signup?initialRole=$initialRole"
    }
    object CompleteProfile : Screen("complete_profile/{role}") {
        fun createRoute(role: String) = "complete_profile/$role"
    }
    object StudentSettings : Screen("student_settings")
    object Notifications : Screen("notifications")
    object StudentProfile : Screen("student_profile")
    object StudentEditProfile : Screen("student_edit_profile")
    object StudentHelpSupport : Screen("student_help_support")
    object StudentPrivacySecurity : Screen("student_privacy_security")
    object StudentChangePassword : Screen("student_change_password")
    object StudentMyQuestions : Screen("student_my_questions")
    object BecomeAlumni : Screen("become_alumni")
    object StudentSavedItems : Screen("student_saved_items")
    object StudentAskQuestion : Screen("student_ask_question?companyName={companyName}") {
        fun createRoute(companyName: String) = "student_ask_question?companyName=$companyName"
    }
    object StudentAllCompanies : Screen("student_all_companies")
    object StudentCompanyDetails : Screen("student_company_details/{companyName}") {
        fun createRoute(companyName: String) = "student_company_details/$companyName"
    }
    object StudentExperienceDetail : Screen("student_experience_detail/{companyName}/{experienceId}") {
        fun createRoute(companyName: String, experienceId: String) = "student_experience_detail/$companyName/$experienceId"
    }
    object AlumniCompanyDetails : Screen("alumni_company_details/{companyName}") {
        fun createRoute(companyName: String) = "alumni_company_details/$companyName"
    }
    object AlumniExperienceDetail : Screen("alumni_experience_detail/{companyName}/{experienceId}") {
        fun createRoute(companyName: String, experienceId: String) = "alumni_experience_detail/$companyName/$experienceId"
    }
    object AlumniSettings : Screen("alumni_settings")
    object AlumniProfile : Screen("alumni_profile")
    object AlumniEditProfile : Screen("alumni_edit_profile")
    object AlumniChangePassword : Screen("alumni_change_password")
    object AlumniPrivacySecurity : Screen("alumni_privacy_security")
    object AlumniHelpSupport : Screen("alumni_help_support")
    object AlumniPosts : Screen("alumni_posts")
    object AlumniAssist : Screen("alumni_assist")
    object AlumniShareExperience : Screen("alumni_share_experience")
    object AlumniEditExperience : Screen("alumni_edit_experience/{companyName}/{experienceId}") {
        fun createRoute(companyName: String, experienceId: String) = "alumni_edit_experience/$companyName/$experienceId"
    }
    object AdminHome : Screen("admin_home")
    object AdminDashboard : Screen("admin_dashboard")
    object AdminReviews : Screen("admin_reviews")
    object AdminSettings : Screen("admin_settings")
    object AdminChangePassword : Screen("admin_change_password")
    object AdminHelpSupport : Screen("admin_help_support")
    object AdminPrivacySecurity : Screen("admin_privacy_security")
    object AdminUsers : Screen("admin_users")
    object AdminAlumniRequests : Screen("admin_alumni_requests")
    object AdminProfile : Screen("admin_profile")
    object AdminReports : Screen("admin_reports")
    object AdminCompanyDetails : Screen("admin_company_details/{companyName}") {
        fun createRoute(companyName: String) = "admin_company_details/$companyName"
    }
    object AdminExperienceDetail : Screen("admin_experience_detail/{companyName}/{experienceId}") {
        fun createRoute(companyName: String, experienceId: String) = "admin_experience_detail/$companyName/$experienceId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val preferenceManager = remember { PreferenceManager(context) }
    
    val startDestination = if (preferenceManager.isFirstTime()) Screen.Welcome.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNext = { navController.navigate(Screen.Experiences.route) },
                onSkip = {
                    preferenceManager.setOnboardingCompleted()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Experiences.route) {
            ExperiencesScreen(
                onNext = { navController.navigate(Screen.ConnectSucceed.route) },
                onSkip = {
                    preferenceManager.setOnboardingCompleted()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.ConnectSucceed.route) {
            ConnectSucceedScreen(
                onGetStarted = {
                    preferenceManager.setOnboardingCompleted()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onSkip = {
                    preferenceManager.setOnboardingCompleted()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onCreateAccount = { role -> 
                    navController.navigate(Screen.StudentSignup.createRoute(role))
                },
                onForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                onLoginSuccess = { role ->
                    when (role) {
                        "Student" -> navController.navigate(Screen.StudentHome.route)
                        "Alumni" -> navController.navigate(Screen.AlumniHome.route)
                        "Admin" -> navController.navigate(Screen.AdminHome.route)
                    }
                }
            )
        }
        composable(Screen.StudentHome.route) {
            StudentHomeScreen(
                onNavigateToSettings = { navController.navigate(Screen.StudentSettings.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToProfile = { navController.navigate(Screen.StudentProfile.route) },
                onNavigateToMyQuestions = { navController.navigate(Screen.StudentMyQuestions.route) },
                onNavigateToSaved = { navController.navigate(Screen.StudentSavedItems.route) },
                onNavigateToAskQuestion = { navController.navigate(Screen.StudentAskQuestion.createRoute("Cognizant")) },
                onNavigateToAllCompanies = { navController.navigate(Screen.StudentAllCompanies.route) },
                onNavigateToCompanyDetails = { companyName ->
                    navController.navigate(Screen.StudentCompanyDetails.createRoute(companyName))
                }
            )
        }
        composable(
            route = Screen.StudentCompanyDetails.route,
            arguments = listOf(navArgument("companyName") { type = NavType.StringType })
        ) { backStackEntry ->
            val companyName = backStackEntry.arguments?.getString("companyName") ?: ""
            StudentCompanyDetailsScreen(
                companyName = companyName,
                onBack = { navController.popBackStack() },
                onExperienceClick = { experienceId ->
                    navController.navigate(Screen.StudentExperienceDetail.createRoute(companyName, experienceId))
                },
                onNavigateToAskQuestion = { name ->
                    navController.navigate(Screen.StudentAskQuestion.createRoute(name))
                }
            )
        }

        composable(
            route = Screen.StudentExperienceDetail.route,
            arguments = listOf(
                navArgument("companyName") { type = NavType.StringType },
                navArgument("experienceId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val companyName = backStackEntry.arguments?.getString("companyName") ?: ""
            val experienceId = backStackEntry.arguments?.getString("experienceId") ?: ""
            StudentExperienceDetailScreen(
                companyName = companyName,
                experienceId = experienceId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StudentAllCompanies.route) {
            StudentAllCompaniesScreen(
                onBack = { navController.popBackStack() },
                onNavigateToCompanyDetails = { companyName ->
                    navController.navigate(Screen.StudentCompanyDetails.createRoute(companyName))
                }
            )
        }
        composable(Screen.StudentProfile.route) {
            StudentProfileScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.StudentHome.route) {
                        popUpTo(Screen.StudentHome.route) { inclusive = true }
                    }
                },
                onNavigateToSettings = { navController.navigate(Screen.StudentSettings.route) },
                onNavigateToEditProfile = { navController.navigate(Screen.StudentEditProfile.route) },
                onNavigateToHelpSupport = { navController.navigate(Screen.StudentHelpSupport.route) },
                onNavigateToMyQuestions = { navController.navigate(Screen.StudentMyQuestions.route) },
                onNavigateToBecomeAlumni = { navController.navigate(Screen.BecomeAlumni.route) },
                onNavigateToSaved = { navController.navigate(Screen.StudentSavedItems.route) },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.StudentSavedItems.route) {
            StudentSavedItemsScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.StudentHome.route) {
                        popUpTo(Screen.StudentHome.route) { inclusive = true }
                    }
                },
                onNavigateToProfile = { navController.navigate(Screen.StudentProfile.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }
        composable(
            route = Screen.StudentAskQuestion.route,
            arguments = listOf(navArgument("companyName") { 
                type = NavType.StringType
                defaultValue = "Cognizant"
            })
        ) { backStackEntry ->
            val companyName = backStackEntry.arguments?.getString("companyName") ?: "Google"
            StudentAskQuestionScreen(
                companyName = companyName,
                onBack = { navController.popBackStack() },
                onPostSuccess = { navController.popBackStack() }
            )
        }
        composable(Screen.BecomeAlumni.route) {
            BecomeAlumniScreen(
                onBack = { navController.popBackStack() },
                onComplete = { navController.popBackStack() }
            )
        }
        composable(Screen.StudentMyQuestions.route) {
            StudentMyQuestionsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StudentHelpSupport.route) {
            StudentHelpSupportScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StudentEditProfile.route) {
            StudentEditProfileScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StudentSettings.route) {
            StudentSettingsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToEditProfile = { navController.navigate(Screen.StudentEditProfile.route) },
                onNavigateToHelpSupport = { navController.navigate(Screen.StudentHelpSupport.route) },
                onNavigateToPrivacySecurity = { navController.navigate(Screen.StudentPrivacySecurity.route) },
                onNavigateToChangePassword = { navController.navigate(Screen.StudentChangePassword.route) },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.StudentChangePassword.route) {
            StudentChangePasswordScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StudentPrivacySecurity.route) {
            StudentPrivacySecurityScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AlumniHome.route) {
            AlumniHomeScreen(
                onNavigateToSettings = { navController.navigate(Screen.AlumniSettings.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToProfile = { navController.navigate(Screen.AlumniProfile.route) },
                onNavigateToPosts = { navController.navigate(Screen.AlumniPosts.route) },
                onNavigateToAssist = { navController.navigate(Screen.AlumniAssist.route) },
                onNavigateToShareExperience = { navController.navigate(Screen.AlumniShareExperience.route) },
                onNavigateToAllCompanies = { navController.navigate(Screen.StudentAllCompanies.route) },
                onNavigateToCompanyDetails = { companyName ->
                    navController.navigate(Screen.AlumniCompanyDetails.createRoute(companyName))
                }
            )
        }
        composable(
            route = Screen.AlumniCompanyDetails.route,
            arguments = listOf(navArgument("companyName") { type = NavType.StringType })
        ) { backStackEntry ->
            val companyName = backStackEntry.arguments?.getString("companyName") ?: ""
            AlumniCompanyDetailsScreen(
                companyName = companyName,
                onBack = { navController.popBackStack() },
                onExperienceClick = { id: String ->
                    navController.navigate(Screen.AlumniExperienceDetail.createRoute(companyName, id))
                },
                onNavigateToAskQuestion = { name: String ->
                    navController.navigate(Screen.StudentAskQuestion.createRoute(name))
                }
            )
        }
        composable(
            route = Screen.AlumniExperienceDetail.route,
            arguments = listOf(
                navArgument("companyName") { type = NavType.StringType },
                navArgument("experienceId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val companyName = backStackEntry.arguments?.getString("companyName") ?: ""
            val experienceId = backStackEntry.arguments?.getString("experienceId") ?: ""
            AlumniExperienceDetailScreen(
                companyName = companyName,
                experienceId = experienceId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AlumniProfile.route) {
            AlumniProfileScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.AlumniHome.route) {
                        popUpTo(Screen.AlumniHome.route) { inclusive = true }
                    }
                },
                onNavigateToAssist = { navController.navigate(Screen.AlumniAssist.route) },
                onNavigateToPosts = { navController.navigate(Screen.AlumniPosts.route) },
                onNavigateToSettings = { navController.navigate(Screen.AlumniSettings.route) },
                onNavigateToShareExperience = { navController.navigate(Screen.AlumniShareExperience.route) },
                onNavigateToEditProfile = { navController.navigate(Screen.AlumniEditProfile.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.AlumniHome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.AlumniPosts.route) {
            AlumniPostsScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.AlumniHome.route) {
                        popUpTo(Screen.AlumniHome.route) { inclusive = true }
                    }
                },
                onNavigateToAssist = { navController.navigate(Screen.AlumniAssist.route) },
                onNavigateToProfile = { navController.navigate(Screen.AlumniProfile.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSettings = { navController.navigate(Screen.AlumniSettings.route) },
                onNavigateToShareExperience = { navController.navigate(Screen.AlumniShareExperience.route) },
                onNavigateToEditPost = { companyName, experienceId ->
                    navController.navigate(Screen.AlumniEditExperience.createRoute(companyName, experienceId))
                }
            )
        }
        composable(Screen.AlumniAssist.route) {
            AlumniAssistScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.AlumniHome.route) {
                        popUpTo(Screen.AlumniHome.route) { inclusive = true }
                    }
                },
                onNavigateToPosts = { navController.navigate(Screen.AlumniPosts.route) },
                onNavigateToProfile = { navController.navigate(Screen.AlumniProfile.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSettings = { navController.navigate(Screen.AlumniSettings.route) },
                onNavigateToShareExperience = { navController.navigate(Screen.AlumniShareExperience.route) }
            )
        }
        composable(Screen.AlumniShareExperience.route) {
            AlumniShareExperienceScreen(
                onBack = { navController.popBackStack() },
                onContinue = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.AlumniEditExperience.route,
            arguments = listOf(
                navArgument("companyName") { type = NavType.StringType },
                navArgument("experienceId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val companyName = backStackEntry.arguments?.getString("companyName") ?: ""
            val experienceId = backStackEntry.arguments?.getString("experienceId") ?: ""
            AlumniEditExperienceScreen(
                companyName = companyName,
                experienceId = experienceId,
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable(Screen.AlumniSettings.route) {
            AlumniSettingsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToEditProfile = { navController.navigate(Screen.AlumniEditProfile.route) },
                onNavigateToHelpSupport = { navController.navigate(Screen.AlumniHelpSupport.route) },
                onNavigateToPrivacySecurity = { navController.navigate(Screen.AlumniPrivacySecurity.route) },
                onNavigateToChangePassword = { navController.navigate(Screen.AlumniChangePassword.route) },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.AlumniHome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.AlumniEditProfile.route) {
            AlumniEditProfileScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable(Screen.AlumniChangePassword.route) {
            AlumniChangePasswordScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AlumniPrivacySecurity.route) {
            AlumniPrivacySecurityScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AlumniHelpSupport.route) {
            AlumniHelpSupportScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onSendResetLink = { navController.navigate(Screen.EmailSentSuccess.route) }
            )
        }
        composable(Screen.EmailSentSuccess.route) {
            EmailSentSuccessScreen(
                onBackToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screen.StudentSignup.route,
            arguments = listOf(navArgument("initialRole") { 
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val initialRole = backStackEntry.arguments?.getString("initialRole")
            StudentCreateAccountScreen(
                initialRole = initialRole,
                onBack = { navController.popBackStack() },
                onContinue = { role ->
                    navController.navigate(Screen.CompleteProfile.createRoute(role))
                }
            )
        }
        composable(
            route = Screen.CompleteProfile.route,
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "Student"
            if (role == "Alumni") {
                AlumniCompleteProfileScreen(
                    onComplete = {
                        navController.navigate(Screen.AlumniHome.route) {
                            popUpTo(Screen.CompleteProfile.route) { inclusive = true }
                        }
                    },
                    onSkip = {
                        navController.navigate(Screen.AlumniHome.route) {
                            popUpTo(Screen.CompleteProfile.route) { inclusive = true }
                        }
                    }
                )
            } else {
                StudentCompleteProfileScreen(
                    onComplete = {
                        navController.navigate(Screen.StudentHome.route) {
                            popUpTo(Screen.CompleteProfile.route) { inclusive = true }
                        }
                    },
                    onSkip = {
                        navController.navigate(Screen.StudentHome.route) {
                            popUpTo(Screen.CompleteProfile.route) { inclusive = true }
                        }
                    }
                )
            }
        }
        composable(Screen.AdminHome.route) {
            AdminHomeScreen(
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSettings = { navController.navigate(Screen.AdminSettings.route) },
                onNavigateToProfile = { navController.navigate(Screen.AdminProfile.route) },
                onNavigateToDashboard = { navController.navigate(Screen.AdminDashboard.route) },
                onNavigateToReviews = { navController.navigate(Screen.AdminReviews.route) },
                onNavigateToUsers = { navController.navigate(Screen.AdminUsers.route) },
                onNavigateToCompanyDetails = { companyName ->
                    navController.navigate(Screen.AdminCompanyDetails.createRoute(companyName))
                }
            )
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                onNavigateToHome = { navController.navigate(Screen.AdminHome.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSettings = { navController.navigate(Screen.AdminSettings.route) },
                onNavigateToProfile = { navController.navigate(Screen.AdminProfile.route) },
                onNavigateToReviews = { navController.navigate(Screen.AdminReviews.route) },
                onNavigateToReports = { navController.navigate(Screen.AdminReports.route) },
                onNavigateToUsers = { navController.navigate(Screen.AdminUsers.route) },
                onNavigateToAlumniRequests = { navController.navigate(Screen.AdminAlumniRequests.route) }
            )
        }
        composable(Screen.AdminReviews.route) {
            AdminReviewsScreen(
                onNavigateToHome = { navController.navigate(Screen.AdminHome.route) },
                onNavigateToDashboard = { navController.navigate(Screen.AdminDashboard.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSettings = { navController.navigate(Screen.AdminSettings.route) },
                onNavigateToProfile = { navController.navigate(Screen.AdminProfile.route) },
                onNavigateToUsers = { navController.navigate(Screen.AdminUsers.route) }
            )
        }
        composable(Screen.AdminProfile.route) {
            AdminProfileScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Screen.AdminHome.route) },
                onNavigateToDashboard = { navController.navigate(Screen.AdminDashboard.route) },
                onNavigateToReviews = { navController.navigate(Screen.AdminReviews.route) },
                onNavigateToUsers = { navController.navigate(Screen.AdminUsers.route) },
                onNavigateToSettings = { navController.navigate(Screen.AdminSettings.route) },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.AdminSettings.route) {
            AdminSettingsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToChangePassword = { navController.navigate(Screen.AdminChangePassword.route) },
                onNavigateToHelpSupport = { navController.navigate(Screen.AdminHelpSupport.route) },
                onNavigateToPrivacySecurity = { navController.navigate(Screen.AdminPrivacySecurity.route) },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.AdminChangePassword.route) {
            AdminChangePasswordScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminHelpSupport.route) {
            AdminHelpSupportScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminPrivacySecurity.route) {
            AdminPrivacySecurityScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminUsers.route) {
            AdminUsersScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Screen.AdminHome.route) },
                onNavigateToDashboard = { navController.navigate(Screen.AdminDashboard.route) },
                onNavigateToReviews = { navController.navigate(Screen.AdminReviews.route) },
                onNavigateToSettings = { navController.navigate(Screen.AdminSettings.route) },
                onNavigateToProfile = { navController.navigate(Screen.AdminProfile.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }
        composable(Screen.AdminAlumniRequests.route) {
            AlumniRequestsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Screen.AdminHome.route) },
                onNavigateToDashboard = { navController.navigate(Screen.AdminDashboard.route) },
                onNavigateToReviews = { navController.navigate(Screen.AdminReviews.route) },
                onNavigateToUsers = { navController.navigate(Screen.AdminUsers.route) },
                onNavigateToSettings = { navController.navigate(Screen.AdminSettings.route) }
            )
        }
        composable(Screen.AdminReports.route) {
            AdminReportsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.AdminCompanyDetails.route,
            arguments = listOf(navArgument("companyName") { type = NavType.StringType })
        ) { backStackEntry ->
            val companyName = backStackEntry.arguments?.getString("companyName") ?: ""
            AdminCompanyDetailsScreen(
                companyName = companyName,
                onBack = { navController.popBackStack() },
                onNavigateToExperienceDetail = { experienceId ->
                    navController.navigate(Screen.AdminExperienceDetail.createRoute(companyName, experienceId))
                }
            )
        }
        composable(
            route = Screen.AdminExperienceDetail.route,
            arguments = listOf(
                navArgument("companyName") { type = NavType.StringType },
                navArgument("experienceId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val companyName = backStackEntry.arguments?.getString("companyName") ?: ""
            val experienceId = backStackEntry.arguments?.getString("experienceId") ?: ""
            AdminExperienceDetailScreen(
                companyName = companyName,
                experienceId = experienceId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
