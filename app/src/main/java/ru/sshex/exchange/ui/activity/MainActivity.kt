package ru.sshex.exchange.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.sshex.exchange.R
import ru.sshex.exchange.navigation.NavigationManager
import ru.sshex.exchange.ui.fragment.currencies.CurrenciesFragment

class MainActivity : AppCompatActivity() {

    private val navigationManager: NavigationManager =
        NavigationManager(fragmentManager = supportFragmentManager, container = R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigationManager.openAsRoot(CurrenciesFragment.newInstance())
        }
    }
}