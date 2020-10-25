package com.project.currency_rates.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Utils {
    val gson = Gson()

    /**
     * convert a data class to a map
     *
     * @param T
     * @return
     */
    fun <T> T.serializeToMap(): Map<String, Any> {
        return convert()
    }

    /**
     * convert an object of type I to type O
     *
     * @param I
     * @param O
     * @return
     */
    inline fun <I, reified O> I.convert(): O {
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}