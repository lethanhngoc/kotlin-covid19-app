package com.example.kotlin_covid19_app.ui.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.ui.adapter.viewholder.ErrorStateItem
import io.reactivex.disposables.CompositeDisposable
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    protected val TAG = javaClass.simpleName
    protected val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        with(compositeDisposable) {
            clear()
            dispose()
        }
        super.onCleared()
    }

    fun handleThrowable(throwable: Throwable): BaseViewItem {
        return when(throwable) {
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is IOException -> {
                ErrorStateItem(R.string.connection_error_title, R.string.connection_error_description)
            }
            else -> ErrorStateItem(R.string.general_error_title, R.string.general_error_description)
        }
    }
}