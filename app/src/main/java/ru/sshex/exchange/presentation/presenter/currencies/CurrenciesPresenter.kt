package ru.sshex.exchange.presentation.presenter.currencies

import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.Disposable
import ru.sshex.exchange.data.models.CurrencyModel
import ru.sshex.exchange.di.DaggerComponentManager
import ru.sshex.exchange.domain.currency.CurrencyInteractor
import ru.sshex.exchange.extensions.async
import ru.sshex.exchange.presentation.model.CurrencyItem
import ru.sshex.exchange.presentation.presenter.core.BaseMvpPresenter
import ru.sshex.exchange.presentation.utils.CurrenciesInfo
import ru.sshex.exchange.ui.fragment.currencies.CurrenciesView
import java.math.BigDecimal
import javax.inject.Inject

@InjectViewState
class CurrenciesPresenter @Inject constructor(private val currencyInteractor: CurrencyInteractor) :
    BaseMvpPresenter<CurrenciesView>() {
    private var baseCurrencyCode = "EUR"
    private var baseCurrencyItem: CurrencyItem? = null
    private var currencyDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        subscribeToUpdates(baseCurrencyCode)
    }

    private fun subscribeToUpdates(baseCurrency: String, value: BigDecimal = BigDecimal.ONE) {
        baseCurrencyItem = CurrencyItem(
            code = baseCurrency,
            value = value,
            flagIconUrl = CurrenciesInfo.currencyImageMapper(baseCurrency),
            fullName = CurrenciesInfo.currencyNameMapper(baseCurrency)
        )
        currencyDisposable = currencyInteractor
            .subscribeUpdatesCurrencyList(baseCurrency, value)
            .async()
            .subscribe(::buildAdapter, ::onError)
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    private fun buildAdapter(currencyModel: CurrencyModel) {
        viewState.updateAdapter(currencyModel.baseCurrency, currencyModel.currenciesList)
    }

    override fun onDestroy() {
        super.onDestroy()
        DaggerComponentManager.removeComponent()
    }

    fun onChangeValue(currencyItem: CurrencyItem) {
        unsubscribe()
        subscribeToUpdates(currencyItem.code, currencyItem.value)
    }

    private fun unsubscribe() {
        if (currencyDisposable?.isDisposed == false) {
            currencyDisposable?.dispose()
        }
    }

    fun onCurrencyChosen(item: CurrencyItem) {
        unsubscribe()
        subscribeToUpdates(item.code, item.value)
    }
}