package ru.sshex.exchange.di.currencies.component

import dagger.Component
import ru.sshex.exchange.di.app.module.NetworkModule
import ru.sshex.exchange.di.currencies.module.CurrenciesModule
import ru.sshex.exchange.di.scope.ForCurrencies
import ru.sshex.exchange.presentation.presenter.currencies.CurrenciesPresenter

@ForCurrencies
@Component(modules = [CurrenciesModule::class, NetworkModule::class])
interface CurrenciesComponent {
    fun providePresenter(): CurrenciesPresenter
}