package ru.sshex.exchange.ui.fragment.currencies

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.sshex.exchange.presentation.model.CurrencyItem

interface CurrenciesView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateAdapter(baseCurrencyItem: CurrencyItem, currenciesList: Map<String, CurrencyItem>)
}