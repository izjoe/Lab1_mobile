package com.example.sentimentapp

enum class Sentiment {
    POSITIVE, NEGATIVE, NEUTRAL
}

class SentimentAnalyzer {
    fun analyze(text: String): Sentiment {
        val positiveWords = listOf("happy", "great", "excellent", "good", "wonderful", "amazing", "love")
        val negativeWords = listOf("sad", "bad", "terrible", "awful", "horrible", "hate", "angry")

        val words = text.lowercase().split(Regex("\\s+"))
        var score = 0
        for (word in words) {
            if (positiveWords.contains(word)) score++
            if (negativeWords.contains(word)) score--
        }

        return when {
            score > 0 -> Sentiment.POSITIVE
            score < 0 -> Sentiment.NEGATIVE
            else -> Sentiment.NEUTRAL
        }
    }
}