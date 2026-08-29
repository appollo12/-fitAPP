package com.example.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// MOSHI & RETROFIT DATA CLASSES FOR GEMINI REST API
@JsonClass(generateAdapter = true)
data class GeminiRequest(
    @Json(name = "contents") val contents: List<GeminiContent>,
    @Json(name = "generationConfig") val generationConfig: GeminiGenerationConfig? = null,
    @Json(name = "systemInstruction") val systemInstruction: GeminiContent? = null
)

@JsonClass(generateAdapter = true)
data class GeminiContent(
    @Json(name = "parts") val parts: List<GeminiPart>
)

@JsonClass(generateAdapter = true)
data class GeminiPart(
    @Json(name = "text") val text: String? = null,
    @Json(name = "inlineData") val inlineData: GeminiInlineData? = null
)

@JsonClass(generateAdapter = true)
data class GeminiInlineData(
    @Json(name = "mimeType") val mimeType: String,
    @Json(name = "data") val data: String // Base64 encoded image
)

@JsonClass(generateAdapter = true)
data class GeminiGenerationConfig(
    @Json(name = "temperature") val temperature: Float? = 0.4f,
    @Json(name = "responseMimeType") val responseMimeType: String? = null
)

@JsonClass(generateAdapter = true)
data class GeminiResponse(
    @Json(name = "candidates") val candidates: List<GeminiCandidate>?
)

@JsonClass(generateAdapter = true)
data class GeminiCandidate(
    @Json(name = "content") val content: GeminiContent?
)

// STRUCTURED DATA RESULT FOR AI PHYSIQUE & AESTHETIC RATING
@JsonClass(generateAdapter = true)
data class PhysiqueRatingResult(
    @Json(name = "aestheticScore") val aestheticScore: Int,
    @Json(name = "highlight1") val highlight1: String,
    @Json(name = "highlight2") val highlight2: String,
    @Json(name = "growthArea1") val growthArea1: String,
    @Json(name = "growthArea2") val growthArea2: String,
    @Json(name = "postureCheck") val postureCheck: String,
    @Json(name = "actionableTip") val actionableTip: String,
    @Json(name = "disclaimer") val disclaimer: String
)
