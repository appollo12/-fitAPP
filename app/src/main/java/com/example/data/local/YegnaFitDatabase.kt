package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserProfileEntity::class,
        WorkoutPlanEntity::class,
        ExerciseEntity::class,
        MealPlanEntity::class,
        ProgressLogEntity::class,
        PhysiqueRatingEntity::class,
        AiChatMessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class YegnaFitDatabase : RoomDatabase() {

    abstract fun yegnaFitDao(): YegnaFitDao

    companion object {
        @Volatile
        private var INSTANCE: YegnaFitDatabase? = null

        fun getDatabase(context: Context): YegnaFitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YegnaFitDatabase::class.java,
                    "yegnafit_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
