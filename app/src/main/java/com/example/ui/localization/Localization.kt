package com.example.ui.localization

object Localization {

    enum class Language {
        ENGLISH, AMHARIC
    }

    fun get(key: String, isAmharic: Boolean): String {
        val lang = if (isAmharic) Language.AMHARIC else Language.ENGLISH
        return strings[key]?.get(lang) ?: key
    }

    private val strings = mapOf(
        "app_name" to mapOf(Language.ENGLISH to "የኛFit", Language.AMHARIC to "የኛFit"),
        "slogan" to mapOf(
            Language.ENGLISH to "Your plan changes as you change.",
            Language.AMHARIC to "በእርስዎ ለውጥ ላይ የተመሰረተ የአካል ብቃት እና የአመጋገብ ዕቅድ"
        ),
        "good_morning" to mapOf(Language.ENGLISH to "Good morning,", Language.AMHARIC to "መልካም ጠዋት፣"),
        "todays_mission" to mapOf(Language.ENGLISH to "TODAY'S MISSION", Language.AMHARIC to "የዛሬው ዋና ተልዕኮ"),
        "start_workout" to mapOf(Language.ENGLISH to "Start Workout", Language.AMHARIC to "ስልጠና ጀምር"),
        "day_progress" to mapOf(Language.ENGLISH to "DAY PROGRESS", Language.AMHARIC to "የዕለቱ እድገት"),
        "workout" to mapOf(Language.ENGLISH to "Workout", Language.AMHARIC to "ስልጠና"),
        "nutrition" to mapOf(Language.ENGLISH to "Nutrition", Language.AMHARIC to "አመጋገብ"),
        "steps" to mapOf(Language.ENGLISH to "Steps", Language.AMHARIC to "እርምጃዎች"),
        "ai_coach_insight" to mapOf(Language.ENGLISH to "AI COACH INSIGHT", Language.AMHARIC to "የአርቴፊሻል ኢንቴሊጀንስ አሰልጣኝ አስተያየት"),
        "quick_actions" to mapOf(Language.ENGLISH to "QUICK ACTIONS", Language.AMHARIC to "ፈጣን ድርጊቶች"),
        "log_meal" to mapOf(Language.ENGLISH to "Log Meal", Language.AMHARIC to "ምግብ መዝግብ"),
        "log_weight" to mapOf(Language.ENGLISH to "Log Weight", Language.AMHARIC to "ክብደት መዝግብ"),
        "water" to mapOf(Language.ENGLISH to "Water", Language.AMHARIC to "ውሃ መዝግብ"),
        "physique_check" to mapOf(Language.ENGLISH to "AI Physique Check", Language.AMHARIC to "የሰውነት ቅርፅ ግምገማ"),
        "view_details" to mapOf(Language.ENGLISH to "View Details >", Language.AMHARIC to "ዝርዝሩንይዩ >"),
        "adaptive_timeline" to mapOf(Language.ENGLISH to "Adaptive Timeline", Language.AMHARIC to "አዳፕቲቭ የጊዜ መስመር"),
        "schedule_adjusted" to mapOf(Language.ENGLISH to "Schedule Adjusted", Language.AMHARIC to "የስልጠና ፕሮግራም ተስተካክሏል"),
        "recovery_focused" to mapOf(Language.ENGLISH to "Recovery Focused", Language.AMHARIC to "እረፍት እና ማገገም ላይ ያተኮረ"),
        "workout_completed" to mapOf(Language.ENGLISH to "Workout Completed", Language.AMHARIC to "ስልጠና ተጠናቋል"),
        "upcoming_workout" to mapOf(Language.ENGLISH to "Upcoming Workout", Language.AMHARIC to "የሚቀጥለው ስልጠና"),
        "what_changed" to mapOf(Language.ENGLISH to "WHAT CHANGED", Language.AMHARIC to "ምን ተለወጠ?"),
        "why_this_change" to mapOf(Language.ENGLISH to "WHY THIS CHANGE?", Language.AMHARIC to "ለምን ይህ ለውጥ ተደረገ?"),
        "impact_on_week" to mapOf(Language.ENGLISH to "IMPACT ON YOUR WEEK", Language.AMHARIC to "በሳምንትዎ ላይ ያለው ተፅዕኖ"),
        "ai_coach" to mapOf(Language.ENGLISH to "AI Coach", Language.AMHARIC to "የአይአይ አሰልጣኝ"),
        "talk_to_coach" to mapOf(Language.ENGLISH to "Talk to Coach", Language.AMHARIC to "ከአሰልጣኙ ጋር ተወያይ"),
        "todays_nutrition" to mapOf(Language.ENGLISH to "Today's Nutrition", Language.AMHARIC to "የዛሬው አመጋገብ"),
        "meal_plan" to mapOf(Language.ENGLISH to "Meal Plan", Language.AMHARIC to "የምግብ ዕቅድ"),
        "week_budget" to mapOf(Language.ENGLISH to "WEEK BUDGET", Language.AMHARIC to "የሳምንት የበጀት ዕቅድ"),
        "progress" to mapOf(Language.ENGLISH to "Progress", Language.AMHARIC to "እድገት"),
        "profile" to mapOf(Language.ENGLISH to "Profile", Language.AMHARIC to "የግል መገለጫ"),
        "manage_subscription" to mapOf(Language.ENGLISH to "Manage Subscription >", Language.AMHARIC to "የደንበኝነት ምዝገባን አስተዳድር >"),
        "language" to mapOf(Language.ENGLISH to "Language / ቋንቋ", Language.AMHARIC to "ቋንቋ / Language"),
        "switch_language" to mapOf(Language.ENGLISH to "Switch to Amharic (አማርኛ)", Language.AMHARIC to "ወደ እንግሊዝኛ ቀይር (English)"),
        "disclaimer_title" to mapOf(Language.ENGLISH to "AI Health & Fitness Disclaimer", Language.AMHARIC to "የአይአይ አካል ብቃት ማሳሰቢያ"),
        "disclaimer_text" to mapOf(
            Language.ENGLISH to "Ratings & recommendations are AI visual estimates for fitness tracking progress, not medical or professional diagnosis.",
            Language.AMHARIC to "ምክሮች እና ግምገማዎች ለአካል ብቃት እድገት መከታተያ የሚረዱ የአይአይ ግምቶች ናቸው፤ የህክምና ምርመራ አይደሉም።"
        ),
        "economy" to mapOf(Language.ENGLISH to "Economy", Language.AMHARIC to "ኢኮኖሚ (ዝቅተኛ ወጪ)"),
        "balanced" to mapOf(Language.ENGLISH to "Balanced", Language.AMHARIC to "ተመጣጣኝ"),
        "premium" to mapOf(Language.ENGLISH to "Premium", Language.AMHARIC to "ፕሪሚየም")
    )
}
