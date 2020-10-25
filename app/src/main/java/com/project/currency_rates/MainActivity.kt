package com.project.currency_rates

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.currency_rates.model.CurrencyResponce
import com.project.currency_rates.model.Rate
import com.project.currency_rates.service.CurrencyApiService
import com.project.currency_rates.service.ServiceBuilder
import com.project.currency_rates.ui.RecyclerAdapter
import com.project.currency_rates.util.Utils.serializeToMap
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : Activity() {
    private val request = ServiceBuilder.buildService(CurrencyApiService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateTotalCurrency(DEFAULT_MULTIPLIER_VALUE)
        editBaseCurrency()
    }

    /**
     * Implementation of base currency edit text
     * Triggered when used has changed a rate of a base currency
     */
    private fun editBaseCurrency() {
        etBaseCurrency.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", s.toString())
                if (s.isEmpty()) {
                    etBaseCurrency.hint = "0"
                    updateTotalCurrency(DEFAULT_MULTIPLIER_VALUE)
                } else {
                    updateTotalCurrency(s.toString().toLong())
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", s.toString())
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                Log.d("onTextChanged", s.toString())
            }
        })
    }

    /**
     * Updates rate values in currenciesList according to the new value of a base currency
     * @param multiplier - given input value for base currency
     */
    private fun updateTotalCurrency(multiplier: Long) {
        val call =
            request.getCurrencyResponce(getString(R.string.access_key), getString(R.string.symbols))
        call.enqueue(object : Callback<CurrencyResponce> {
            override fun onResponse(
                call: Call<CurrencyResponce>,
                response: Response<CurrencyResponce>
            ) {
                if (response.isSuccessful) {
                    Log.d("Response", response.message())
                    val currencyList = getString(R.string.symbols).split(",")
                    val rates = response.body()!!.rates
                    val currencyRateList =
                        convertRatesObjectToArray(rates, currencyList, multiplier)
                    currencyRecyclerVie(currencyList, currencyRateList)
                }
            }

            override fun onFailure(call: Call<CurrencyResponce>, t: Throwable) {
                Toast.makeText(this@MainActivity, R.string.error_network_connection_text, Toast.LENGTH_LONG).show()
                Log.d("ResError", t.message)
            }
        })
    }

    /**
     * Add adapter for recyclerview and apply
     *
     * @param currencyList - is a List of String, Currency short name list
     * @param currencyRateList - is a ArrayList of Double value, rates of currencies
     */
    private fun currencyRecyclerVie(
        currencyList: List<String>,
        currencyRateList: ArrayList<Double>
    ) {
        rvCurrencyList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = RecyclerAdapter(currencyList, currencyRateList)
        }
    }

    /**
     * convert the object of rates to arraylist for recyclerview adapter
     *
     * @param rates - is a Data Class, all currencies rate values
     * @param currencies - is a List of String, all currencies short names
     * @param multiplier - is a Long value, given input base value in euro
     * @return ArrayList of rates
     */
    private fun convertRatesObjectToArray(
        rates: Rate,
        currencies: List<String>,
        multiplier: Long
    ): ArrayList<Double> {
        var rateList = ArrayList<Double>()
        val rateObjectToMap = rates.serializeToMap()
        for (currency in currencies) {
            val rate: Double = rateObjectToMap.get(currency) as Double
            rateList.add(rate * multiplier)
        }
        return rateList
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause", "onPause Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onPause", "onPause Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "onDestroy Called")
        MainActivity::finish
    }
}