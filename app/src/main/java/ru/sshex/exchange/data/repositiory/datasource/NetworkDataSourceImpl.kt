package ru.sshex.exchange.data.repositiory.datasource

import io.reactivex.Observable
import ru.sshex.exchange.data.dto.CurrencyListResponse
import ru.sshex.exchange.network.Api
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(private val api: Api) : NetworkDataSource {
    override fun requestCurrencyRate(baseCurrency: String): Observable<CurrencyListResponse> =
        api.getCurrencyRate(baseCurrency)
}