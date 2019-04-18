package ru.sshex.exchange.domain.currency

import io.reactivex.Observable
import ru.sshex.exchange.data.models.CurrencyModel
import java.math.BigDecimal

interface CurrencyInteractor {
    fun subscribeUpdatesCurrencyList(baseCurrency: String, value: BigDecimal): Observable<CurrencyModel>
}