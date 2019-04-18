package ru.sshex.exchange.data.repositiory.currency

import io.reactivex.Observable
import ru.sshex.exchange.data.models.CurrencyModel
import ru.sshex.exchange.data.repositiory.datasource.NetworkDataSource
import ru.sshex.exchange.presentation.model.CurrencyItem
import ru.sshex.exchange.presentation.utils.CurrenciesInfo
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val TIMEOUT = 1L

class CurrencyRepositoryImpl @Inject constructor(private val networkDataSource: NetworkDataSource) :
    CurrencyRepository {

    override fun requestCurrencyBy(baseCurrency: String, value: BigDecimal): Observable<CurrencyModel> {
        return Observable.interval(0, TIMEOUT, TimeUnit.SECONDS)
            .flatMap { networkDataSource.requestCurrencyRate(baseCurrency) }
            .distinctUntilChanged()
            .map {
                it.ratesMap.mapValues { entry ->
                    CurrencyItem(
                        entry.key,
                        CurrenciesInfo.currencyNameMapper(entry.key),
                        CurrenciesInfo.currencyImageMapper(entry.key),
                        entry.value.toBigDecimal().multiply(value)
                    )
                }
            }
            .map {
                CurrencyModel(
                    CurrencyItem(
                        baseCurrency, CurrenciesInfo.currencyNameMapper(baseCurrency),
                        CurrenciesInfo.currencyImageMapper(baseCurrency), value
                    ), it
                )
            }
    }
}