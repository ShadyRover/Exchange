package ru.sshex.exchange.di

import ru.sshex.exchange.di.currencies.component.CurrenciesComponent
import ru.sshex.exchange.di.currencies.component.DaggerCurrenciesComponent

object DaggerComponentManager {

    private var currencyComponent: CurrenciesComponent? = null

    fun hasCurrenciesComponent(): Boolean {
        return currencyComponent != null
    }

    fun createCurrenciesComponent(): CurrenciesComponent {
        currencyComponent = DaggerCurrenciesComponent.create()
        return currencyComponent as CurrenciesComponent
    }

    fun getCurrencyListComponent() = currencyComponent
        ?: throw IllegalStateException("Component not created yet!")

    fun removeComponent() {
        currencyComponent = null
    }
}