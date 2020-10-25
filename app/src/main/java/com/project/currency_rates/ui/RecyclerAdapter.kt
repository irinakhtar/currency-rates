package com.project.currency_rates.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.currency_rates.R

/**
 * RecycleView adapter for list of currencies with rates
 *
 * @property currency - List of currencies, Short name of currencies
 * @property rate - List of currency rates
 */
class RecyclerAdapter(private val currency: List<String>, private val rate: List<Double>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rate_item_list, parent, false)
        return ViewHolder(view)
    }

    /**
     * total items in adapter
     *
     * @return count of item in integer value
     */
    override fun getItemCount(): Int {
        return currency.size
    }

    /**
     * data collection and apply a rotating rendering of visible data applied to this viewholder
     *
     * @param holder - viewholder for each view
     * @param position - position of view in viewholder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(currency.get(position), rate.get(position))
    }

    /**
     * viewholder
     *
     * @property view
     */
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val countryFlag: ImageView = itemView.findViewById(R.id.ivFlagImage)
        private val countryShortName: TextView = itemView.findViewById(R.id.tvCurrencyShortName)
        private val countryFullname: TextView = itemView.findViewById(R.id.tvCurrencyFullName)
        private val currencyRate: TextView = itemView.findViewById(R.id.tvRateText)

        fun bind(currency : String, rate: Double) {
            setFlagImageWithCurrencyName(currency)
            countryShortName.text = currency
            currencyRate.text = String.format("%.2f", rate)
        }

       private fun setFlagImageWithCurrencyName(currency: String){
            if(currency.equals("JPY")) {
                countryFlag.setImageResource(R.drawable.japan_flag_icon)
                countryFullname.setText(R.string.currency_jpy_title)
            } else if(currency.equals("GBP")) {
                countryFlag.setImageResource(R.drawable.united_kingdom_flag_icon)
                countryFullname.setText(R.string.currency_gbp_title)
            } else if(currency.equals("AUD")) {
                countryFlag.setImageResource(R.drawable.australia_flag_icon)
                countryFullname.setText(R.string.currency_aud_title)
            } else if(currency.equals("CAD")) {
                countryFlag.setImageResource(R.drawable.canada_flag_icon)
                countryFullname.setText(R.string.currency_cad_title)
            } else if(currency.equals("CHF")) {
                countryFlag.setImageResource(R.drawable.switzerland_flag_icon)
                countryFullname.setText(R.string.currency_chf_title)
            } else if(currency.equals("CNY")) {
                countryFlag.setImageResource(R.drawable.china_flag_icon)
                countryFullname.setText(R.string.currency_cny_title)
            } else if(currency.equals("SEK")) {
                countryFlag.setImageResource(R.drawable.sweden_flag_icon)
                countryFullname.setText(R.string.currency_sek_title)
            } else if(currency.equals("NZD")) {
                countryFlag.setImageResource(R.drawable.new_zealand_flag_icon)
                countryFullname.setText(R.string.currency_nzd_title)
            }
        }
    }

}