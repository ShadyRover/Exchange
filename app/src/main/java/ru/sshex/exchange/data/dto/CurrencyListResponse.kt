package ru.sshex.exchange.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.LinkedHashMap

data class CurrencyListResponse(
		@SerializedName("date") val date: Date,
		@SerializedName("base") val defaultCurrency: String,
		@SerializedName("rates") val ratesMap: LinkedHashMap<String, String>
)