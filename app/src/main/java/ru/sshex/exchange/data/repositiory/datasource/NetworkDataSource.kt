package ru.sshex.exchange.data.repositiory.datasource

import io.reactivex.Observable
import ru.sshex.exchange.data.dto.CurrencyListResponse

interface NetworkDataSource {
    fun requestCurrencyRate(baseCurrency: String): Observable<CurrencyListResponse>
}