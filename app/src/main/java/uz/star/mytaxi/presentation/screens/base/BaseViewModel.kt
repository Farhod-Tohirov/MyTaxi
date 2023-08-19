package uz.star.mytaxi.presentation.screens.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.star.mytaxi.utils.extensions.showLog
import uz.star.mytaxi.utils.helpers.SingleLiveEvent
import kotlin.coroutines.CoroutineContext

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:31
 **/

abstract class BaseViewModel : ViewModel() {
    val loader = MutableLiveData(false)
    private var customLoader: MutableLiveData<Boolean>? = null

    val errorHandler = SingleLiveEvent<Throwable>()

    private val uiContext: CoroutineContext = Dispatchers.Main
    private val ioContext: CoroutineContext = Dispatchers.IO

    protected fun tryLoadData(
        handler: CoroutineExceptionHandler = baseHandler,
        shouldShowLoader: Boolean = true,
        loaderLiveData: MutableLiveData<Boolean>? = null,
        block: suspend () -> Unit
    ) = viewModelScope.launch(uiContext + handler) {
        try {
            customLoader = loaderLiveData
            if (shouldShowLoader) if (loaderLiveData != null) loaderLiveData.value = true else showLoading()
            block()
            if (loaderLiveData != null) loaderLiveData.value = false else hideLoading()
        } catch (e: Exception) {
            throw e
        }
    }

    protected suspend fun onUIContext(block: suspend () -> Unit) {
        withContext(uiContext) { block() }
    }

    protected suspend fun onIOContext(block: suspend () -> Unit) {
        withContext(ioContext) { block() }
    }

    private fun showLoading() {
        loader.value = true
    }

    private fun hideLoading() {
        customLoader?.value = false
        loader.value = false
    }

    private val baseHandler = CoroutineExceptionHandler { _, throwable ->
        hideLoading()
        showLog("ERROR IN VIEW MODEL = $throwable")
        errorHandler.value = throwable
    }
}