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
import com.project.currency_rates.util.Converter.serializeToMap
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : Activity() {
    val request = ServiceBuilder.buildService(CurrencyApiService::class.java)
    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateTotalCurrency(0)
        etBaseCurrency.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", s.toString())
                if(s.isEmpty()) {
                    etBaseCurrency.setHint("0")
                    updateTotalCurrency(0)
                }
                else {
                    updateTotalCurrency(s.toString().toLong())
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", s.toString())
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                    Log.d("onTextChanged", s.toString())
            }
        })
    }

    /**
     *
     */
    fun updateTotalCurrency(multiplier: Long) {
        val call = request.getCurrencyResponce(getString(R.string.access_key), getString(R.string.symbols))
        call.enqueue(object : Callback<CurrencyResponce>{
            override fun onResponse(call: Call<CurrencyResponce>, response: Response<CurrencyResponce>) {
                if (response.isSuccessful){
                    Log.d("Responce", response.message())
                    val currencyList = getString(R.string.symbols).split(",")
                    val rates = response.body()!!.rates
                    val currencyRateList = convertRatesObjectToArray(rates, currencyList, multiplier)
                    rvCurrencyList.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = RecyclerAdapter(currencyList, currencyRateList)
                    }
                }
            }
            override fun onFailure(call: Call<CurrencyResponce>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("ResError", t.message)
            }
        })
    }

    fun convertRatesObjectToArray(rates: Rate, currencies: List<String>, multiplier: Long) : ArrayList<Double>{
        var rateList = ArrayList<Double>()
        val rateObjectToMap = rates.serializeToMap()
        for(currency in currencies) {
            val rate: Double = rateObjectToMap.get(currency) as Double
            rateList.add(rate* multiplier)
        }
        return rateList
    }
}