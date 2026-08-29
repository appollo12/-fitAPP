package com.example.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.*
import com.example.data.remote.PhysiqueRatingResult
import com.example.data.repository.YegnaFitRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: YegnaFitRepository

    val userProfile: StateFlow<UserProfileEntity?>
    val workoutPlans: StateFlow<List<WorkoutPlanEntity>>
    val todayExercises: StateFlow<List<ExerciseEntity>>
    val mealPlans: StateFlow<List<MealPlanEntity>>
    val physiqueRatings: StateFlow<List<PhysiqueRatingEntity>>
    val chatMessages: StateFlow<List<AiChatMessageEntity>>

    // Navigation & Modal States
    private val _currentTab = MutableStateFlow(0) // 0: Home, 1: Timeline, 2: Coach, 3: Nutrition, 4: Profile
    val currentTab = _currentTab.asStateFlow()

    private val _isWorkoutSessionOpen = MutableStateFlow(false)
    val isWorkoutSessionOpen = _isWorkoutSessionOpen.asStateFlow()

    private val _isPhysiqueRatingOpen = MutableStateFlow(false)
    val isPhysiqueRatingOpen = _isPhysiqueRatingOpen.asStateFlow()

    private val _isAnalyzingPhysique = MutableStateFlow(false)
    val isAnalyzingPhysique = _isAnalyzingPhysique.asStateFlow()

    private val _latestPhysiqueResult = MutableStateFlow<PhysiqueRatingResult?>(null)
    val latestPhysiqueResult = _latestPhysiqueResult.asStateFlow()

    private val _isSubscriptionModalOpen = MutableStateFlow(false)
    val isSubscriptionModalOpen = _isSubscriptionModalOpen.asStateFlow()

    private val _isBudgetCalculatorOpen = MutableStateFlow(false)
    val isBudgetCalculatorOpen = _isBudgetCalculatorOpen.asStateFlow()

    private val _budgetResultText = MutableStateFlow("")
    val budgetResultText = _budgetResultText.asStateFlow()

    private val _selectedMealTier = MutableStateFlow("All") // "All", "Economy", "Balanced", "Premium"
    val selectedMealTier = _selectedMealTier.asStateFlow()

    private val _isProgressScreenOpen = MutableStateFlow(false)
    val isProgressScreenOpen = _isProgressScreenOpen.asStateFlow()

    private val _isChallengesOpen = MutableStateFlow(false)
    val isChallengesOpen = _isChallengesOpen.asStateFlow()

    private val _isNotificationsOpen = MutableStateFlow(false)
    val isNotificationsOpen = _isNotificationsOpen.asStateFlow()

    private val _isSettingsOpen = MutableStateFlow(false)
    val isSettingsOpen = _isSettingsOpen.asStateFlow()

    private val _isDataExportOpen = MutableStateFlow(false)
    val isDataExportOpen = _isDataExportOpen.asStateFlow()

    private val _isPaymentOpen = MutableStateFlow(false)
    val isPaymentOpen = _isPaymentOpen.asStateFlow()

    private val _isOnboardingOpen = MutableStateFlow(true)
    val isOnboardingOpen = _isOnboardingOpen.asStateFlow()

    private val _layoutVariant = MutableStateFlow("Editorial") // "Editorial", "Bento", "Minimal", "Focus"
    val layoutVariant = _layoutVariant.asStateFlow()

    init {
        val database = YegnaFitDatabase.getDatabase(application)
        repository = YegnaFitRepository(database.yegnaFitDao())

        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }

        userProfile = repository.userProfile.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfileEntity()
        )

        workoutPlans = repository.workoutPlans.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )

        todayExercises = repository.getExercisesForPlan("thu_16").stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )

        mealPlans = repository.mealPlans.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )

        physiqueRatings = repository.physiqueRatings.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )

        chatMessages = repository.chatMessages.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
    }

    fun setTab(index: Int) {
        _currentTab.value = index
        _isProgressScreenOpen.value = false
    }

    fun openProgressScreen(open: Boolean) {
        _isProgressScreenOpen.value = open
    }

    fun openChallenges(open: Boolean) {
        _isChallengesOpen.value = open
    }

    fun openNotifications(open: Boolean) {
        _isNotificationsOpen.value = open
    }

    fun openSettings(open: Boolean) {
        _isSettingsOpen.value = open
    }

    fun openDataExport(open: Boolean) {
        _isDataExportOpen.value = open
    }

    fun openPayment(open: Boolean) {
        _isPaymentOpen.value = open
    }

    fun openOnboarding(open: Boolean) {
        _isOnboardingOpen.value = open
    }

    fun setLayoutVariant(variant: String) {
        _layoutVariant.value = variant
    }

    fun openWorkoutSession(open: Boolean) {
        _isWorkoutSessionOpen.value = open
    }

    fun openPhysiqueRating(open: Boolean) {
        _isPhysiqueRatingOpen.value = open
    }

    fun openSubscriptionModal(open: Boolean) {
        _isSubscriptionModalOpen.value = open
    }

    fun openBudgetCalculator(open: Boolean) {
        _isBudgetCalculatorOpen.value = open
    }

    fun selectMealTier(tier: String) {
        _selectedMealTier.value = tier
    }

    fun toggleExerciseCompletion(exerciseId: Int, currentCompleted: Boolean) {
        viewModelScope.launch {
            repository.toggleExerciseCompletion(exerciseId, !currentCompleted, "thu_16")
            val currentExercises = todayExercises.value
            val completedCount = currentExercises.count { if (it.id == exerciseId) !currentCompleted else it.isCompleted }
            val status = if (completedCount == currentExercises.size) "Completed" else "Adjusted"
            repository.updateWorkoutProgress("thu_16", completedCount, status)
        }
    }

    fun toggleMealCompletion(mealId: Int, currentCompleted: Boolean) {
        viewModelScope.launch {
            repository.toggleMealCompletion(mealId, !currentCompleted)
        }
    }

    fun toggleLanguage() {
        viewModelScope.launch {
            val currentAmharic = userProfile.value?.isAmharic ?: false
            repository.updateLanguage(!currentAmharic)
        }
    }

    fun updateSubscriptionTier(tier: String) {
        viewModelScope.launch {
            repository.updateSubscriptionTier(tier)
            _isSubscriptionModalOpen.value = false
        }
    }

    fun sendMessageToCoach(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            val isAmharic = userProfile.value?.isAmharic ?: false
            repository.sendChatMessage(text, isAmharic)
        }
    }

    fun analyzePhysiquePhoto(bitmap: Bitmap?) {
        viewModelScope.launch {
            _isAnalyzingPhysique.value = true
            val isAmharic = userProfile.value?.isAmharic ?: false
            val result = repository.analyzePhysiquePhoto(bitmap, isAmharic)
            _latestPhysiqueResult.value = result
            _isAnalyzingPhysique.value = false
        }
    }

    fun calculateMealForBudget(budgetEtb: Int) {
        viewModelScope.launch {
            val isAmharic = userProfile.value?.isAmharic ?: false
            val result = com.example.data.remote.YegnaFitAiEngine.getMealsForBudget(budgetEtb, isAmharic)
            _budgetResultText.value = result
        }
    }
}
