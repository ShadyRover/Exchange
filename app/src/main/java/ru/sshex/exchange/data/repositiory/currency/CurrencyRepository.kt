package ru.sshex.exchange.data.repositiory.currency

import io.reactivex.Observable
import ru.sshex.exchange.data.models.CurrencyModel
import java.math.BigDecimal

interface CurrencyRepository {
    fun requestCurrencyBy(baseCurrency: String, value: BigDecimal): Observable<CurrencyModel>
}