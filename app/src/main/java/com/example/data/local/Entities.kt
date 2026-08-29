package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Abel Tesfaye",
    val age: Int = 26,
    val heightCm: Float = 178f,
    val weightKg: Float = 74.5f,
    val fitnessGoal: String = "Muscle Growth & Athletic Definition",
    val activityLevel: String = "Moderate (3-4 days/week)",
    val availableEquipment: String = "Dumbbells, Bodyweight, Resistance Bands",
    val location: String = "Addis Ababa, Ethiopia",
    val dailyFoodBudgetEtb: Int = 150,
    val weeklyFoodBudgetEtb: Int = 1050,
    val dietaryPreferences: String = "Ethiopian Traditional, High Protein",
    val allergies: String = "None",
    val workoutSchedule: String = "Evening (6:00 PM)",
    val subscriptionTier: String = "PREMIUM", // FREE, STANDARD, PREMIUM
    val isAmharic: Boolean = false
)

@Entity(tableName = "workout_plans")
data class WorkoutPlanEntity(
    @PrimaryKey val id: String,
    val dayOfWeek: String, // Mon, Tue, Wed, Thu, Fri, Sat, Sun
    val title: String,
    val amharicTitle: String,
    val durationMin: Int,
    val totalExercises: Int,
    val completedExercises: Int,
    val status: String, // "Completed", "Adjusted", "Planned"
    val adjustmentReason: String? = null,
    val originalPlanSummary: String? = null,
    val isToday: Boolean = false
)

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val workoutPlanId: String,
    val name: String,
    val amharicName: String,
    val targetMuscle: String,
    val sets: Int,
    val reps: Int,
    val weightKg: Float = 0f,
    val isCompleted: Boolean = false,
    val guidanceTip: String
)

@Entity(tableName = "meal_plans")
data class MealPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayOfWeek: String,
    val mealType: String, // "Breakfast", "Lunch", "Dinner", "Snack"
    val title: String,
    val amharicTitle: String,
    val calories: Int,
    val proteinGrams: Int,
    val costEtb: Int,
    val tier: String, // "Economy", "Balanced", "Premium"
    val ingredients: String,
    val isCompleted: Boolean = false
)

@Entity(tableName = "progress_logs")
data class ProgressLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateString: String,
    val weightKg: Float,
    val workoutsCompleted: Int,
    val caloriesBurned: Int,
    val waterIntakeMl: Int,
    val stepsCount: Int
)

@Entity(tableName = "physique_ratings")
data class PhysiqueRatingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val aestheticScore: Int, // 1 to 10
    val highlight1: String,
    val highlight2: String,
    val growthArea1: String,
    val growthArea2: String,
    val postureCheck: String,
    val actionableTip: String,
    val imageUri: String? = null,
    val disclaimerText: String
)

@Entity(tableName = "ai_chat_messages")
data class AiChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val sender: String, // "user" or "coach"
    val message: String,
    val isContextCard: Boolean = false,
    val cardTitle: String? = null,
    val cardSubtitle: String? = null
)
