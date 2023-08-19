package uz.star.mytaxi.presentation.screens.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.star.mytaxi.presentation.MainActivity
import uz.star.mytaxi.utils.extensions.showLog
import uz.star.mytaxi.utils.helpers.ResourceProvider

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:30
 **/

abstract class BaseScreen<T : ViewBinding>(@LayoutRes layout: Int, vbFactory: (View) -> T) : Fragment(layout) {

    open val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    val resourceProvider = ResourceProvider

    val binding: T by viewBinding(vbFactory)

    protected abstract val viewModel: BaseViewModel?

    open val recyclerViewIdList: List<Int> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateScreen()
    }

    override fun onStart() {
        super.onStart()
        onStartScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        hideLoader()

        loadCountryColors()
        loadViews()
        loadObservers()
        loadDefaultObservers()
    }

    open fun onCreateScreen() {}
    open fun onDestroyScreenUI() {}
    open fun onStartScreen() {}
    open fun loadViews() {}
    open fun loadCountryColors() {}
    open fun loadObservers() {}

    private fun loadDefaultObservers() {
        viewModel?.loader?.observe(this, loaderObserver)
        viewModel?.errorHandler?.observe(this, errorObserver)
    }

    private val loaderObserver = Observer<Boolean> { showLoader ->
        if (showLoader) showLoader() else hideLoader()
    }

    open fun showLoader() {
        (requireActivity() as? MainActivity)?.showLoader()
    }

    open fun hideLoader() {
        (requireActivity() as? MainActivity)?.hideLoader()
    }

    open val errorObserver = Observer<Throwable> { throwable ->

    }

    open fun safeNavigate(@IdRes resId: Int) {
        try {
            navController.navigate(resId)
        } catch (e: Exception) {
            showLog("ERROR IN NAVIGATION = ${e.message}")
        }
    }

    open fun safeNavigate(resId: NavDirections) {
        try {
            navController.navigate(resId)
        } catch (e: Exception) {
            showLog("ERROR IN NAVIGATION = ${e.message}")
        }
    }

    open fun closeScreen() {
        if (navController.previousBackStackEntry == null)
            requireActivity().finish()
        else
            navController.navigateUp()
    }

    override fun onDestroyView() {
        onDestroyScreenUI()
        for (id in recyclerViewIdList) {
            val t = view?.findViewById<View>(id)
            if (t is RecyclerView) t.adapter = null else if (t is ViewPager2) t.adapter = null
        }
        super.onDestroyView()
    }

}