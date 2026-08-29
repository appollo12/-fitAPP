package com.example.data.repository

import android.graphics.Bitmap
import com.example.data.local.*
import com.example.data.remote.PhysiqueRatingResult
import com.example.data.remote.YegnaFitAiEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class YegnaFitRepository(private val dao: YegnaFitDao) {

    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()
    val workoutPlans: Flow<List<WorkoutPlanEntity>> = dao.getAllWorkoutPlans()
    val mealPlans: Flow<List<MealPlanEntity>> = dao.getAllMealPlans()
    val progressLogs: Flow<List<ProgressLogEntity>> = dao.getProgressLogs()
    val physiqueRatings: Flow<List<PhysiqueRatingEntity>> = dao.getPhysiqueRatings()
    val chatMessages: Flow<List<AiChatMessageEntity>> = dao.getChatMessages()

    fun getExercisesForPlan(planId: String): Flow<List<ExerciseEntity>> {
        return dao.getExercisesForWorkout(planId)
    }

    suspend fun toggleExerciseCompletion(exerciseId: Int, isCompleted: Boolean, planId: String) {
        dao.toggleExerciseCompletion(exerciseId, isCompleted)
    }

    suspend fun updateWorkoutProgress(planId: String, completedCount: Int, status: String) {
        dao.updateWorkoutProgress(planId, completedCount, status)
    }

    suspend fun toggleMealCompletion(mealId: Int, isCompleted: Boolean) {
        dao.toggleMealCompletion(mealId, isCompleted)
    }

    suspend fun savePhysiqueRating(rating: PhysiqueRatingEntity) {
        dao.insertPhysiqueRating(rating)
    }

    suspend fun sendChatMessage(userText: String, isAmharic: Boolean): String {
        val userMsg = AiChatMessageEntity(sender = "user", message = userText)
        dao.insertChatMessage(userMsg)

        val profile = userProfile.firstOrNull()
        val contextStr = profile?.let {
            "Name: ${it.name}, Goal: ${it.fitnessGoal}, Budget: ${it.dailyFoodBudgetEtb} ETB/day, Equipment: ${it.availableEquipment}"
        } ?: "Standard Ethiopian user profile"

        val coachReply = YegnaFitAiEngine.generateCoachResponse(userText, contextStr, isAmharic)
        val coachMsg = AiChatMessageEntity(sender = "coach", message = coachReply)
        dao.insertChatMessage(coachMsg)

        return coachReply
    }

    suspend fun analyzePhysiquePhoto(bitmap: Bitmap?, isAmharic: Boolean): PhysiqueRatingResult {
        val result = YegnaFitAiEngine.analyzePhysiquePhoto(bitmap, isAmharic)
        val entity = PhysiqueRatingEntity(
            aestheticScore = result.aestheticScore,
            highlight1 = result.highlight1,
            highlight2 = result.highlight2,
            growthArea1 = result.growthArea1,
            growthArea2 = result.growthArea2,
            postureCheck = result.postureCheck,
            actionableTip = result.actionableTip,
            disclaimerText = result.disclaimer
        )
        dao.insertPhysiqueRating(entity)
        return result
    }

    suspend fun updateLanguage(isAmharic: Boolean) {
        val current = dao.getUserProfile().firstOrNull() ?: UserProfileEntity()
        dao.insertOrUpdateProfile(current.copy(isAmharic = isAmharic))
    }

    suspend fun updateSubscriptionTier(tier: String) {
        val current = dao.getUserProfile().firstOrNull() ?: UserProfileEntity()
        dao.insertOrUpdateProfile(current.copy(subscriptionTier = tier))
    }

    // SEED INITIAL DATA FOR YEGNAFIT APP MATCHING UI GUIDE
    suspend fun seedInitialDataIfEmpty() {
        if (dao.getUserProfile().firstOrNull() == null) {
            dao.insertOrUpdateProfile(UserProfileEntity())
        }

        if (dao.getAllWorkoutPlans().firstOrNull().isNullOrEmpty()) {
            val plans = listOf(
                WorkoutPlanEntity(
                    id = "thu_16",
                    dayOfWeek = "Thu, 16 May",
                    title = "Upper Body",
                    amharicTitle = "የሰውነት በላይኛው ክፍል ስልጠና",
                    durationMin = 25,
                    totalExercises = 7,
                    completedExercises = 3,
                    status = "Adjusted",
                    adjustmentReason = "You had a busy day, so I adjusted your workout to fit your time.",
                    originalPlanSummary = "Original: Upper Body 45 min • 7 exercises",
                    isToday = true
                ),
                WorkoutPlanEntity(
                    id = "mon_13",
                    dayOfWeek = "Mon, 13 May",
                    title = "Upper Body",
                    amharicTitle = "የሰውነት በላይኛው ክፍል",
                    durationMin = 45,
                    totalExercises = 7,
                    completedExercises = 7,
                    status = "Completed",
                    isToday = false
                ),
                WorkoutPlanEntity(
                    id = "tue_14",
                    dayOfWeek = "Tue, 14 May",
                    title = "Meal Logged",
                    amharicTitle = "ምግብ ተመዝግቧል",
                    durationMin = 0,
                    totalExercises = 0,
                    completedExercises = 0,
                    status = "Completed",
                    isToday = false
                ),
                WorkoutPlanEntity(
                    id = "wed_15",
                    dayOfWeek = "Wed, 15 May",
                    title = "Recovery Focused",
                    amharicTitle = "እረፍት እና ማገገም",
                    durationMin = 0,
                    totalExercises = 0,
                    completedExercises = 0,
                    status = "Completed",
                    adjustmentReason = "7h 35m sleep logged",
                    isToday = false
                ),
                WorkoutPlanEntity(
                    id = "fri_17",
                    dayOfWeek = "Fri, 17 May",
                    title = "Lower Body",
                    amharicTitle = "የሰውነት ታችኛው ክፍል",
                    durationMin = 40,
                    totalExercises = 6,
                    completedExercises = 0,
                    status = "Planned",
                    isToday = false
                )
            )
            dao.insertWorkoutPlans(plans)

            // Exercises for today's workout
            val exercises = listOf(
                ExerciseEntity(workoutPlanId = "thu_16", name = "Warm Up", amharicName = "የማሞቂያ እንቅስቃሴ", targetMuscle = "Full Body", sets = 1, reps = 5, isCompleted = true, guidanceTip = "5 min light jogging / arm circles"),
                ExerciseEntity(workoutPlanId = "thu_16", name = "Push Ups", amharicName = "ፑሽ አፕ", targetMuscle = "Chest & Triceps", sets = 3, reps = 12, isCompleted = true, guidanceTip = "Keep core tight and elbow 45 degrees"),
                ExerciseEntity(workoutPlanId = "thu_16", name = "Dumbbell Row", amharicName = "ደወል ረድፍ (Dumbbell Row)", targetMuscle = "Back & Lats", sets = 3, reps = 10, isCompleted = true, guidanceTip = "Pull elbow toward hip with controlled negative"),
                ExerciseEntity(workoutPlanId = "thu_16", name = "Shoulder Press", amharicName = "የትክሻ ፕሬስ", targetMuscle = "Deltoids", sets = 3, reps = 10, isCompleted = false, guidanceTip = "Press straight up without arching lower back"),
                ExerciseEntity(workoutPlanId = "thu_16", name = "Tricep Dip", amharicName = "ትራይሴፕ ዲፕ", targetMuscle = "Triceps", sets = 2, reps = 12, isCompleted = false, guidanceTip = "Lower until elbows reach 90 degrees"),
                ExerciseEntity(workoutPlanId = "thu_16", name = "Incline Dumbbell Press", amharicName = "ዘንበል ያለ ደረት ፕሬስ", targetMuscle = "Upper Chest", sets = 3, reps = 10, isCompleted = false, guidanceTip = "Set bench to 30 degrees for upper shelf focus"),
                ExerciseEntity(workoutPlanId = "thu_16", name = "Face Pulls", amharicName = "የፊት ጉተታ (Face Pulls)", targetMuscle = "Rear Delts & Posture", sets = 3, reps = 15, isCompleted = false, guidanceTip = "Pull band toward forehead with elbows high")
            )
            dao.insertExercises(exercises)
        }

        if (dao.getAllMealPlans().firstOrNull().isNullOrEmpty()) {
            val meals = listOf(
                MealPlanEntity(dayOfWeek = "Thu, 16 May", mealType = "Breakfast", title = "Firfir with Eggs", amharicTitle = "ፍርፍር ከእንቁላል ጋር", calories = 420, proteinGrams = 22, costEtb = 42, tier = "Economy", ingredients = "Injera, Berbere, 2 Eggs, Oil", isCompleted = true),
                MealPlanEntity(dayOfWeek = "Thu, 16 May", mealType = "Lunch", title = "Shiro Wot with Injera", amharicTitle = "ሽሮ ወጥ ከእንጀራ ጋር", calories = 520, proteinGrams = 24, costEtb = 55, tier = "Balanced", ingredients = "Shiro powder, Garlic, Injera, Salad", isCompleted = true),
                MealPlanEntity(dayOfWeek = "Thu, 16 May", mealType = "Dinner", title = "Misir Wot & Veggie Salad", amharicTitle = "ምስር ወጥ እና የሰላጣ አትክልት", calories = 480, proteinGrams = 20, costEtb = 50, tier = "Economy", ingredients = "Red Lentils, Onions, Tomato, Injera", isCompleted = false),
                MealPlanEntity(dayOfWeek = "Thu, 16 May", mealType = "Snacks", title = "Banana & Peanut Butter", amharicTitle = "ሙዝ ከለውዝ ቅቤ ጋር", calories = 150, proteinGrams = 6, costEtb = 18, tier = "Economy", ingredients = "1 Local Banana, 1 tbsp Peanut butter", isCompleted = true)
            )
            dao.insertMealPlans(meals)
        }

        if (dao.getChatMessages().firstOrNull().isNullOrEmpty()) {
            val messages = listOf(
                AiChatMessageEntity(
                    sender = "coach",
                    message = "Great job staying consistent this week! I'm your YegnaFit AI Coach. How can I help adapt your fitness or Ethiopian meal plan today?",
                    isContextCard = true,
                    cardTitle = "WORKOUT ADJUSTMENT",
                    cardSubtitle = "I've increased today's last set slightly based on your performance."
                )
            )
            dao.insertChatMessage(messages.first())
        }
    }
}
