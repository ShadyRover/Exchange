package ru.sshex.exchange.di.app.component

import dagger.Component
import ru.sshex.exchange.ExchangeApp
import ru.sshex.exchange.di.app.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: ExchangeApp)
}