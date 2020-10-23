package com.project.currency_rates.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converter {
    val gson = Gson()

    //convert a data class to a map
    fun <T> T.serializeToMap(): Map<String, Any> {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}