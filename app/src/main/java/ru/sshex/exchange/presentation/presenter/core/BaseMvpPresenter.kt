package ru.sshex.exchange.presentation.presenter.core

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseMvpPresenter<V : MvpView> : MvpPresenter<V>() {

	private val compositeDisposable = CompositeDisposable()

	override fun onDestroy() {
		compositeDisposable.dispose()
	}

	protected fun Disposable.connect() {
		compositeDisposable.add(this)
	}
}