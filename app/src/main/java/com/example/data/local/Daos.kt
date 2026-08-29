package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface YegnaFitDao {

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfileEntity)

    // Workout Plans
    @Query("SELECT * FROM workout_plans ORDER BY id ASC")
    fun getAllWorkoutPlans(): Flow<List<WorkoutPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutPlans(plans: List<WorkoutPlanEntity>)

    @Query("UPDATE workout_plans SET completedExercises = :completed, status = :status WHERE id = :planId")
    suspend fun updateWorkoutProgress(planId: String, completed: Int, status: String)

    // Exercises
    @Query("SELECT * FROM exercises WHERE workoutPlanId = :planId")
    fun getExercisesForWorkout(planId: String): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("UPDATE exercises SET isCompleted = :isCompleted WHERE id = :exerciseId")
    suspend fun toggleExerciseCompletion(exerciseId: Int, isCompleted: Boolean)

    // Meal Plans
    @Query("SELECT * FROM meal_plans ORDER BY id ASC")
    fun getAllMealPlans(): Flow<List<MealPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlans(meals: List<MealPlanEntity>)

    @Query("UPDATE meal_plans SET isCompleted = :isCompleted WHERE id = :mealId")
    suspend fun toggleMealCompletion(mealId: Int, isCompleted: Boolean)

    // Progress Logs
    @Query("SELECT * FROM progress_logs ORDER BY id DESC")
    fun getProgressLogs(): Flow<List<ProgressLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgressLog(log: ProgressLogEntity)

    // Physique Ratings
    @Query("SELECT * FROM physique_ratings ORDER BY timestamp DESC")
    fun getPhysiqueRatings(): Flow<List<PhysiqueRatingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhysiqueRating(rating: PhysiqueRatingEntity)

    // AI Chat History
    @Query("SELECT * FROM ai_chat_messages ORDER BY timestamp ASC")
    fun getChatMessages(): Flow<List<AiChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: AiChatMessageEntity)
}
