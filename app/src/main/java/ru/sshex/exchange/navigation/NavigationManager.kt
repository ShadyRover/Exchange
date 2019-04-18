package ru.sshex.exchange.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

private const val ROOT_TAG = "root_tag"

class NavigationManager(private val fragmentManager: FragmentManager, private val container: Int) {

	init {
		fragmentManager.addOnBackStackChangedListener {
			navigationListener?.invoke()
		}
	}

	val isRootFragmentVisible: Boolean
		get() = fragmentManager.backStackEntryCount <= 1


	/**
	 * Listener interface for navigation events.
	 */
	private var navigationListener: (() -> Unit)? = null


	/**
	 * Displays the next fragment
	 *
	 * @param fragment
	 */
	fun open(fragment: Fragment) {
		openFragment(fragment, addToBackStack = true, isRoot = false)

	}

	private fun openFragment(fragment: Fragment, addToBackStack: Boolean, isRoot: Boolean) {
		val fragTransaction = fragmentManager.beginTransaction()

		if (isRoot)
			fragTransaction.replace(container, fragment, ROOT_TAG)
		else
			fragTransaction.replace(container, fragment)

		if (addToBackStack)
			fragTransaction.addToBackStack(fragment.toString())
		fragTransaction.commit()
	}

	/**
	 * pops every fragment and starts the given fragment as a new one.
	 *
	 * @param fragment
	 */
	fun openAsRoot(fragment: Fragment) {
		popEveryFragment()
		openFragment(fragment, addToBackStack = false, isRoot = true)
	}


	/**
	 * Pops all the queued fragments
	 */
	private fun popEveryFragment() {
		fragmentManager.popBackStackImmediate(ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
	}


	fun navigateBack(): Boolean {
		return if (fragmentManager.backStackEntryCount == 0) {
			false
		} else {
			fragmentManager.popBackStackImmediate()
			true
		}
	}


}