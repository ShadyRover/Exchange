package ru.sshex.exchange.domain.currency

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import ru.sshex.exchange.data.dto.CurrencyListResponse
import ru.sshex.exchange.data.models.CurrencyModel
import ru.sshex.exchange.data.repositiory.currency.CurrencyRepository
import ru.sshex.exchange.data.repositiory.currency.CurrencyRepositoryImpl
import ru.sshex.exchange.data.repositiory.datasource.NetworkDataSource
import ru.sshex.exchange.presentation.model.CurrencyItem
import java.math.BigDecimal
import java.util.*

class CurrencyInteractorImplTest {

    private var repository: CurrencyRepository = mock()
    private lateinit var interactor: CurrencyInteractor
    private val baseCurrency = "EUR"
    private val bigDecimalValue = BigDecimal.valueOf(55)
    @Before
    fun setup() {
        interactor = CurrencyInteractorImpl(repository)
    }

    @Test
    fun requestRates() {
        whenever(repository.requestCurrencyBy(baseCurrency, bigDecimalValue)).thenReturn(
            Observable.just(
                getMockResult()
            )
        )
        val observer = interactor.subscribeUpdatesCurrencyList(baseCurrency, bigDecimalValue).test()
        observer.awaitTerminalEvent()
        Mockito.verify(repository).requestCurrencyBy(baseCurrency, bigDecimalValue)
        observer.assertNoErrors()
    }

    @Test
    fun checkCalculations() {
        val networkDataSource: NetworkDataSource = mock()
        whenever(networkDataSource.requestCurrencyRate(baseCurrency)).thenReturn(mockCurrencyListResponse())
        val repo: CurrencyRepository = CurrencyRepositoryImpl(networkDataSource)
        val observer = repo.requestCurrencyBy(baseCurrency, bigDecimalValue).take(1).test()
        observer.awaitTerminalEvent()
        observer.values().first().let {
            assertEquals(it.currenciesList["AUD"]?.value,BigDecimal.valueOf(1.621).multiply(bigDecimalValue))
            assertEquals(it.currenciesList["BGN"]?.value,BigDecimal.valueOf(1.9614).multiply(bigDecimalValue))
            assertEquals(it.currenciesList["BRL"]?.value,BigDecimal.valueOf(4.8054).multiply(bigDecimalValue))
            assertEquals(it.currenciesList["CAD"]?.value,BigDecimal.valueOf(1.5382).multiply(bigDecimalValue))
        }
        observer.assertValueCount(1)
        observer.assertNoErrors()
    }

    private fun mockCurrencyListResponse(): Observable<CurrencyListResponse> =
        Observable.just(
            CurrencyListResponse(
                Date(), "EUR", mapOf(
                    "AUD" to "1.621",
                    "BGN" to "1.9614",
                    "BRL" to "4.8054",
                    "CAD" to "1.5382"
                ) as LinkedHashMap<String, String>
            )
        )

    private fun getMockedMap(): Map<String, CurrencyItem> {
        return mapOf(
            "EUR" to CurrencyItem("EUR", 0x1, "link_eur", "1".toBigDecimal()),
            "USD" to CurrencyItem("USD", 0x2, "link", "2.123456".toBigDecimal()),
            "CHF" to CurrencyItem("CHF", 0x3, "link", "3.4567".toBigDecimal()),
            "AUD" to CurrencyItem("AUD", 0x4, "link", "0.987654321".toBigDecimal()),
            "GBR" to CurrencyItem("GBR", 0x5, "link", "1.001".toBigDecimal())
        )
    }


    private fun getMockResult() =
        CurrencyModel(
            CurrencyItem("EUR", 0x1, "link_eur", bigDecimalValue),
            getMockedMap()
        )
}