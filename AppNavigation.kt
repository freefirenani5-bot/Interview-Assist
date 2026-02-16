package com.simats.interviewassist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simats.interviewassist.ui.screens.onboarding.ConnectSucceedScreen
import com.simats.interviewassist.ui.screens.onboarding.ExperiencesScreen
import com.simats.interviewassist.ui.screens.onboarding.WelcomeScreen
import com.simats.interviewassist.ui.screens.auth.LoginScreen
import com.simats.interviewassist.ui.screens.auth.ForgotPasswordScreen
import com.simats.interviewassist.ui.screens.auth.EmailSentSuccessScreen
import com.simats.interviewassist.ui.screens.alumni.AlumniHomeScreen
import com.simats.interviewassist.ui.screens.student.StudentHomeScreen
import com.simats.interviewassist.ui.screens.student.StudentNotificationsScreen
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

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Experiences : Screen("experiences")
    object ConnectSucceed : Screen("connect_succeed")
    object Login : Screen("login")
    object ForgotPassword : Screen("forgot_password")
    object EmailSentSuccess : Screen("email_sent_success")
    object StudentHome : Screen("student_home")
    object AlumniHome : Screen("alumni_home")
    object StudentSignup : Screen("student_signup")
    object CompleteProfile : Screen("complete_profile/{role}") {
        fun createRoute(role: String) = "complete_profile/$role"
    }
    object StudentSettings : Screen("student_settings")
    object StudentNotifications : Screen("student_notifications")
    object StudentProfile : Screen("student_profile")
    object StudentEditProfile : Screen("student_edit_profile")
    object StudentHelpSupport : Screen("student_help_support")
    object StudentPrivacySecurity : Screen("student_privacy_security")
    object StudentChangePassword : Screen("student_change_password")
    object StudentMyQuestions : Screen("student_my_questions")
    object BecomeAlumni : Screen("become_alumni")
    object StudentSavedItems : Screen("student_saved_items")
    object StudentAskQuestion : Screen("student_ask_question")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNext = { navController.navigate(Screen.Experiences.route) },
                onSkip = {
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
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.ConnectSucceed.route) {
            ConnectSucceedScreen(
                onGetStarted = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onCreateAccount = { navController.navigate(Screen.StudentSignup.route) },
                onForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                onLoginSuccess = { role ->
                    when (role) {
                        "Student" -> navController.navigate(Screen.StudentHome.route)
                        "Alumni" -> navController.navigate(Screen.AlumniHome.route)
                    }
                }
            )
        }
        composable(Screen.StudentHome.route) {
            StudentHomeScreen(
                onNavigateToSettings = { navController.navigate(Screen.StudentSettings.route) },
                onNavigateToNotifications = { navController.navigate(Screen.StudentNotifications.route) },
                onNavigateToProfile = { navController.navigate(Screen.StudentProfile.route) },
                onNavigateToMyQuestions = { navController.navigate(Screen.StudentMyQuestions.route) },
                onNavigateToSaved = { navController.navigate(Screen.StudentSavedItems.route) },
                onNavigateToAskQuestion = { navController.navigate(Screen.StudentAskQuestion.route) }
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
                onNavigateToMyQuestions = { navController.navigate(Screen.StudentAskQuestion.route) },
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
                onNavigateToNotifications = { navController.navigate(Screen.StudentNotifications.route) }
            )
        }
        composable(Screen.StudentAskQuestion.route) {
            StudentAskQuestionScreen(
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
        composable(Screen.StudentNotifications.route) {
            StudentNotificationsScreen(
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
            AlumniHomeScreen(onBack = { navController.popBackStack() })
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
        composable(Screen.StudentSignup.route) {
            StudentCreateAccountScreen(
                onBack = { navController.popBackStack() },
                onContinue = { role ->
                    navController.navigate(Screen.CompleteProfile.createRoute(role))
                }
            )
        }
        composable(
            route = Screen.CompleteProfile.route,
            arguments = listOf(androidx.navigation.navArgument("role") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "Student"
            StudentCompleteProfileScreen(
                onComplete = {
                    when (role) {
                        "Student" -> navController.navigate(Screen.StudentHome.route)
                        "Alumni" -> navController.navigate(Screen.AlumniHome.route)
                    }
                },
                onSkip = {
                    when (role) {
                        "Student" -> navController.navigate(Screen.StudentHome.route)
                        "Alumni" -> navController.navigate(Screen.AlumniHome.route)
                    }
                }
            )
        }
    }
}
