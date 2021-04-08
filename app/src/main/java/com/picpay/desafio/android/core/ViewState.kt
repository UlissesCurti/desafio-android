package com.picpay.desafio.android.core

data class ViewState<out T>(val status: Status, val data: T? = null) {

    companion object {
        fun <T> cache(data: T?): ViewState<T> {
            return ViewState(Status.CACHE, data)
        }

        fun <T> success(data: T?): ViewState<T> {
            return ViewState(Status.SUCCESS, data)
        }

        fun <T> error(): ViewState<T> {
            return ViewState(Status.ERROR)
        }

        fun <T> loading(): ViewState<T> {
            return ViewState(Status.LOADING)
        }
    }

    enum class Status {
        CACHE,
        SUCCESS,
        LOADING,
        ERROR
    }
}