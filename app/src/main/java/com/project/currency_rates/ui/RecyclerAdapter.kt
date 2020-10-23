package com.project.currency_rates.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.currency_rates.R

class RecyclerAdapter(val currency: List<String>, val rate: List<Double>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rate_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currency.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(currency.get(position), rate.get(position))
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val countryFlag: ImageView = itemView.findViewById(R.id.ivFlagImage)
        private val countryName: TextView = itemView.findViewById(R.id.tvCurrencyShortName)
        private val countryFullname: TextView = itemView.findViewById(R.id.tvCurrencyFullName)
        private val currencyRate: TextView = itemView.findViewById(R.id.rateText)

        fun bind(currency : String, rate: Double) {
            countryName.text = currency
            currencyRate.text = rate.toString()
        }
    }

}