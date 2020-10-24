package com.project.currency_rates.model

data class CurrencyResponce(
    var success: Boolean,
    var timestamp: Int,
    var base: String,
    var date: String,
    var rates: Rate
)

data class Rate(
    var EUR: Double,
    var JPY: Double,
    var GBP: Double,
    var AUD: Double,
    var CAD: Double,
    var CHF: Double,
    var CNY: Double,
    var SEK: Double,
    var NZD: Double
)
