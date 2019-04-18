package ru.sshex.exchange.domain.currency

import io.reactivex.Observable
import ru.sshex.exchange.data.models.CurrencyModel
import ru.sshex.exchange.data.repositiory.currency.CurrencyRepository
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyInteractorImpl @Inject constructor(private val currencyRepository: CurrencyRepository) :
    CurrencyInteractor {
    override fun subscribeUpdatesCurrencyList(baseCurrency: String, value: BigDecimal): Observable<CurrencyModel> =
        currencyRepository.requestCurrencyBy(baseCurrency, value)
}