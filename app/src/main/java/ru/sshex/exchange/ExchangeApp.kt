package ru.sshex.exchange

import android.app.Application
import ru.sshex.exchange.di.app.component.AppComponent
import ru.sshex.exchange.di.app.component.DaggerAppComponent

class ExchangeApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
       initDagger2()
    }

    private fun initDagger2() {
        appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)
    }
}