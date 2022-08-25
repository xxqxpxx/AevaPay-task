package com.aevapay.android.task.base

import androidx.lifecycle.ViewModel
import com.aevapay.android.task.network.ErrorManager

abstract class BaseViewModel() : ViewModel() {
    fun getStatusCode(throwable: Throwable): Int {
        return ErrorManager.getCode(throwable = throwable)
    }
}