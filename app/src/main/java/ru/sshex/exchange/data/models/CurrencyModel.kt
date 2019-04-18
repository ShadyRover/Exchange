package ru.sshex.exchange.data.models

import ru.sshex.exchange.presentation.model.CurrencyItem

data class CurrencyModel(val baseCurrency: CurrencyItem, val currenciesList: Map<String, CurrencyItem>)