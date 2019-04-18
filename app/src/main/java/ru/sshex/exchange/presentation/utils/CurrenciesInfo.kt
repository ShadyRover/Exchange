package ru.sshex.exchange.presentation.utils

import ru.sshex.exchange.R

object CurrenciesInfo {

    fun currencyImageMapper(currency: String): String {
        return "https://www.countryflags.io/${currency.take(2)}/shiny/64.png"
    }

    fun currencyNameMapper(currency: String): Int {
        return when (currency) {
            "AUD" -> R.string.aud_full_name
            "BGN" -> R.string.bgn_full_name
            "BRL" -> R.string.brl_full_name
            "CAD" -> R.string.cad_full_name
            "CHF" -> R.string.chf_full_name
            "CNY" -> R.string.cny_full_name
            "CZK" -> R.string.czk_full_name
            "DKK" -> R.string.dkk_full_name
            "EUR" -> R.string.eur_full_name
            "GBP" -> R.string.gbp_full_name
            "HKD" -> R.string.hkd_full_name
            "HRK" -> R.string.hrk_full_name
            "HUF" -> R.string.huf_full_name
            "IDR" -> R.string.idr_full_name
            "ILS" -> R.string.ils_full_name
            "INR" -> R.string.inr_full_name
            "ISK" -> R.string.isk_full_name
            "JPY" -> R.string.jpy_full_name
            "KRW" -> R.string.krw_full_name
            "MXN" -> R.string.mxn_full_name
            "MYR" -> R.string.myr_full_name
            "NOK" -> R.string.nok_full_name
            "NZD" -> R.string.nzd_full_name
            "PHP" -> R.string.php_full_name
            "PLN" -> R.string.pln_full_name
            "RON" -> R.string.ron_full_name
            "RUB" -> R.string.rub_full_name
            "SEK" -> R.string.sek_full_name
            "SGD" -> R.string.sgd_full_name
            "THB" -> R.string.thb_full_name
            "TRY" -> R.string.try_full_name
            "USD" -> R.string.usd_full_name
            "ZAR" -> R.string.zar_full_name
            else -> R.string.unknown_currency
        }
    }
}