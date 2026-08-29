package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.YegnaFitTheme
import com.example.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YegnaFitApp()
        }
    }
}

@Composable
fun YegnaFitApp(viewModel: MainViewModel = viewModel()) {
    val currentTab by viewModel.currentTab.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val todayExercises by viewModel.todayExercises.collectAsState()

    val isWorkoutOpen by viewModel.isWorkoutSessionOpen.collectAsState()
    val isSubscriptionOpen by viewModel.isSubscriptionModalOpen.collectAsState()
    val isBudgetOpen by viewModel.isBudgetCalculatorOpen.collectAsState()
    val budgetResultText by viewModel.budgetResultText.collectAsState()
    val isProgressOpen by viewModel.isProgressScreenOpen.collectAsState()
    val isChallengesOpen by viewModel.isChallengesOpen.collectAsState()
    val isNotificationsOpen by viewModel.isNotificationsOpen.collectAsState()
    val isSettingsOpen by viewModel.isSettingsOpen.collectAsState()
    val isDataExportOpen by viewModel.isDataExportOpen.collectAsState()
    val isPaymentOpen by viewModel.isPaymentOpen.collectAsState()
    val isOnboardingOpen by viewModel.isOnboardingOpen.collectAsState()

    val isAmharic = userProfile?.isAmharic ?: false

    YegnaFitTheme {
        if (isOnboardingOpen) {
            OnboardingScreen(
                isAmharic = isAmharic,
                onToggleLanguage = { viewModel.toggleLanguage() },
                onCompleteOnboarding = { viewModel.openOnboarding(false) }
            )
        } else {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    YegnaFitBottomNavigation(
                        currentTab = currentTab,
                        isAmharic = isAmharic,
                        onTabSelected = { viewModel.setTab(it) }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when {
                        isProgressOpen -> {
                            ProgressScreen(
                                viewModel = viewModel,
                                isAmharic = isAmharic,
                                onBack = { viewModel.openProgressScreen(false) }
                            )
                        }
                        isChallengesOpen -> {
                            ChallengesScreen(
                                isAmharic = isAmharic,
                                onBack = { viewModel.openChallenges(false) }
                            )
                        }
                        isNotificationsOpen -> {
                            NotificationsScreen(
                                isAmharic = isAmharic,
                                onBack = { viewModel.openNotifications(false) }
                            )
                        }
                        isSettingsOpen -> {
                            SettingsScreen(
                                viewModel = viewModel,
                                isAmharic = isAmharic,
                                onBack = { viewModel.openSettings(false) }
                            )
                        }
                        isDataExportOpen -> {
                            DataExportScreen(
                                isAmharic = isAmharic,
                                onBack = { viewModel.openDataExport(false) }
                            )
                        }
                        isPaymentOpen -> {
                            PaymentChapaScreen(
                                isAmharic = isAmharic,
                                onBack = { viewModel.openPayment(false) }
                            )
                        }
                        else -> {
                            when (currentTab) {
                                0 -> HomeScreen(
                                    viewModel = viewModel,
                                    isAmharic = isAmharic,
                                    onOpenWorkoutSession = { viewModel.openWorkoutSession(true) },
                                    onOpenPhysiqueCheck = { viewModel.setTab(2) },
                                    onOpenBudgetCalculator = { viewModel.openBudgetCalculator(true) },
                                    onNavigateToTimeline = { viewModel.setTab(1) },
                                    onNavigateToProgress = { viewModel.openProgressScreen(true) },
                                    onOpenOnboarding = { viewModel.openOnboarding(true) }
                                )
                                1 -> TimelineScreen(
                                    viewModel = viewModel,
                                    isAmharic = isAmharic,
                                    onOpenWorkoutSession = { viewModel.openWorkoutSession(true) }
                                )
                                2 -> CoachScreen(
                                    viewModel = viewModel,
                                    isAmharic = isAmharic
                                )
                                3 -> NutritionScreen(
                                    viewModel = viewModel,
                                    isAmharic = isAmharic,
                                    onOpenBudgetCalculator = { viewModel.openBudgetCalculator(true) }
                                )
                                4 -> ProfileScreen(
                                    viewModel = viewModel,
                                    isAmharic = isAmharic,
                                    onOpenSubscriptionModal = { viewModel.openSubscriptionModal(true) },
                                    onNavigateToProgress = { viewModel.openProgressScreen(true) },
                                    onOpenOnboarding = { viewModel.openOnboarding(true) },
                                    onOpenChallenges = { viewModel.openChallenges(true) },
                                    onOpenNotifications = { viewModel.openNotifications(true) },
                                    onOpenSettings = { viewModel.openSettings(true) },
                                    onOpenDataExport = { viewModel.openDataExport(true) },
                                    onOpenPayment = { viewModel.openPayment(true) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Workout Player Dialog
        if (isWorkoutOpen) {
            WorkoutSessionDialog(
                exercises = todayExercises,
                isAmharic = isAmharic,
                onDismiss = { viewModel.openWorkoutSession(false) },
                onToggleExercise = { id, done -> viewModel.toggleExerciseCompletion(id, done) }
            )
        }

        // Subscription Tier Management Modal
        if (isSubscriptionOpen) {
            SubscriptionModalDialog(
                currentTier = userProfile?.subscriptionTier ?: "PREMIUM",
                isAmharic = isAmharic,
                onDismiss = { viewModel.openSubscriptionModal(false) },
                onSelectTier = { tier -> viewModel.updateSubscriptionTier(tier) }
            )
        }

        // Budget Meal Generator Dialog
        if (isBudgetOpen) {
            BudgetCalculatorDialog(
                isAmharic = isAmharic,
                resultText = budgetResultText,
                onCalculate = { amount -> viewModel.calculateMealForBudget(amount) },
                onDismiss = { viewModel.openBudgetCalculator(false) }
            )
        }
    }
}
