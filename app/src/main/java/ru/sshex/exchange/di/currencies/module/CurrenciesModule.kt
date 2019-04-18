package ru.sshex.exchange.di.currencies.module

import dagger.Module
import dagger.Provides
import ru.sshex.exchange.data.repositiory.currency.CurrencyRepository
import ru.sshex.exchange.data.repositiory.currency.CurrencyRepositoryImpl
import ru.sshex.exchange.data.repositiory.datasource.NetworkDataSource
import ru.sshex.exchange.data.repositiory.datasource.NetworkDataSourceImpl
import ru.sshex.exchange.di.scope.ForCurrencies
import ru.sshex.exchange.domain.currency.CurrencyInteractor
import ru.sshex.exchange.domain.currency.CurrencyInteractorImpl
import ru.sshex.exchange.network.Api

@Module
class CurrenciesModule {
    @Provides
    @ForCurrencies
    fun provideCurrencyRepository(networkDataSource: NetworkDataSource): CurrencyRepository =
        CurrencyRepositoryImpl(networkDataSource)

    @Provides
    @ForCurrencies
    fun provideCurrencyInteractor(currencyRepository: CurrencyRepository): CurrencyInteractor =
        CurrencyInteractorImpl(currencyRepository)

    @Provides
    @ForCurrencies
    fun provideNetworkDataSource(api: Api): NetworkDataSource = NetworkDataSourceImpl(api)
}