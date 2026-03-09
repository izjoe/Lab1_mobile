package com.example.sentimentapp

import org.junit.Assert.assertEquals
import org.junit.Test

class SentimentAnalyzerTest {

    private val analyzer = SentimentAnalyzer()

    @Test
    fun analyze_positiveText_returnsPositive() {
        val result = analyzer.analyze("I am very happy today, it is a great day!")
        assertEquals(Sentiment.POSITIVE, result)
    }

    @Test
    fun analyze_negativeText_returnsNegative() {
        val result = analyzer.analyze("This is a bad and terrible experience.")
        assertEquals(Sentiment.NEGATIVE, result)
    }

    @Test
    fun analyze_neutralText_returnsNeutral() {
        val result = analyzer.analyze("The sky is blue and the grass is green.")
        assertEquals(Sentiment.NEUTRAL, result)
    }

    @Test
    fun analyze_mixedText_returnsCorrectSentiment() {
        // "happy" (+1), "great" (+1), "bad" (-1) = score 1 -> POSITIVE
        val result = analyzer.analyze("I am happy and great, despite the bad weather.")
        assertEquals(Sentiment.POSITIVE, result)
    }
}