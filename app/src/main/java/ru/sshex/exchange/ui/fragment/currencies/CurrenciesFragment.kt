package ru.sshex.exchange.ui.fragment.currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_currencies.*
import ru.sshex.exchange.R
import ru.sshex.exchange.di.DaggerComponentManager
import ru.sshex.exchange.presentation.model.CurrencyItem
import ru.sshex.exchange.presentation.presenter.currencies.CurrenciesPresenter
import ru.sshex.exchange.presentation.view.core.BaseMvpFragment
import ru.sshex.exchange.ui.fragment.currencies.adapter.CurrenciesAdapter

class CurrenciesFragment : BaseMvpFragment(), CurrenciesView {
    override val layoutRes: Int
        get() = R.layout.fragment_currencies

    private lateinit var adapter: CurrenciesAdapter

    @InjectPresenter
    lateinit var presenter: CurrenciesPresenter

    @ProvidePresenter
    fun providePresenter() = DaggerComponentManager.getCurrencyListComponent().providePresenter()

    companion object {
        fun newInstance(): Fragment {
            return CurrenciesFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null || !DaggerComponentManager.hasCurrenciesComponent()) {
            DaggerComponentManager.createCurrenciesComponent()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CurrenciesAdapter(
            valueChangeListener = { value -> presenter.onChangeValue(value) },
            clickListener = { item, _ ->
                scrollTop()
                presenter.onCurrencyChosen(item)
            }
        )
        currencyRecyclerView.adapter = adapter
    }

    private fun scrollTop() {
        (currencyRecyclerView.layoutManager as? LinearLayoutManager)?.scrollToPosition(0)
    }

    override fun updateAdapter(baseCurrencyItem: CurrencyItem, currenciesList: Map<String, CurrencyItem>) {
        adapter.updateAdapter(baseCurrencyItem, currenciesList as LinkedHashMap<String, CurrencyItem>)
    }
}