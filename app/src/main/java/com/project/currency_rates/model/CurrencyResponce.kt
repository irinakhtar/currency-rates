package com.project.currency_rates.model

data class CurrencyResponce(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    val rates: Rate
)

data class Rate(
    val EUR: Double,
    val JPY: Double,
    val GBP: Double,
    val AUD: Double,
    val CAD: Double,
    val CHF: Double,
    val CNY: Double,
    val SEK: Double,
    val NZD: Double
)
