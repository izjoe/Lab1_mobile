package com.example.sentimentapp

data class Quote(val text: String, val author: String)

class QuoteGenerator {
    private val quotes = listOf(
        Quote("The only way to do great work is to love what you do.", "Steve Jobs"),
        Quote("Innovation distinguishes between a leader and a follower.", "Steve Jobs"),
        Quote("Your time is limited, so don't waste it living someone else's life.", "Steve Jobs"),
        Quote("Stay hungry, stay foolish.", "Steve Jobs"),
        Quote("The way to get started is to quit talking and begin doing.", "Walt Disney"),
        Quote("If you can dream it, you can do it.", "Walt Disney"),
        Quote("It's kind of fun to do the impossible.", "Walt Disney"),
        Quote("The best way to predict the future is to invent it.", "Alan Kay")
    )

    fun getRandomQuote(): Quote {
        return quotes.random()
    }
}