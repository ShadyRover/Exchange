package ru.sshex.exchange.presentation.model

import androidx.annotation.StringRes
import java.math.BigDecimal

data class CurrencyItem(val code: String, @StringRes val fullName: Int, val flagIconUrl: String, val value: BigDecimal)