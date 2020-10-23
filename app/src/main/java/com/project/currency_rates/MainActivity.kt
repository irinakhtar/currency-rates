package com.project.currency_rates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.currency_rates.model.CurrencyResponce
import com.project.currency_rates.model.Rate
import com.project.currency_rates.service.CurrencyApiEndPoint
import com.project.currency_rates.service.ServiceBuilder
import com.project.currency_rates.ui.RecyclerAdapter
import com.project.currency_rates.util.Converter
import com.project.currency_rates.util.Converter.serializeToMap
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val request = ServiceBuilder.buildService(CurrencyApiEndPoint::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateTotalCurrency(0)
        input_euro.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", s.toString())
                if(s.isEmpty()) {
                    input_euro.setHint("0")
                    updateTotalCurrency(0)
                }
                else {
                    updateTotalCurrency(Integer.parseInt(s.toString()))
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

    fun updateTotalCurrency(multiplier: Int) {
        val call = request.getCurrencyResponce(getString(R.string.access_key), getString(R.string.symbols))
        call.enqueue(object : Callback<CurrencyResponce>{
            override fun onResponse(call: Call<CurrencyResponce>, response: Response<CurrencyResponce>) {
                if (response.isSuccessful){
                    Log.d("Responce", response.message())
                    val currencies = getString(R.string.symbols).split(",")
                    val rate = response.body()!!.rates
                    val rateObjectToMap = rate.serializeToMap()
                    var currencyrate = ArrayList<Double>()
                    for(currency in currencies) {
                        val rate: Double = rateObjectToMap.get(currency) as Double
                        currencyrate.add(rate* multiplier)
                    }
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = RecyclerAdapter(currencies, currencyrate)
                    }

                }
            }
            override fun onFailure(call: Call<CurrencyResponce>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("ResError", t.message)
            }
        })
    }
}