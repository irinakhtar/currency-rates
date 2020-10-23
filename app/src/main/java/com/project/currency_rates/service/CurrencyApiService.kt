package com.project.currency_rates.service

import com.project.currency_rates.model.CurrencyResponce
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET("/api/latest")
    fun getCurrencyResponce(@Query("access_key") access_key: String,
                  @Query("symbols") symbols: String): Call<CurrencyResponce>

}