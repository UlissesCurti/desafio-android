package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.core.ViewState
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.UserUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val useCase: UserUseCase) : ViewModel() {

    private val disposable by lazy { CompositeDisposable() }

    private val _usersLiveData = MutableLiveData<ViewState<List<User>>>()
    val usersLiveData: LiveData<ViewState<List<User>>>
        get() = _usersLiveData

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    fun loadUsers() {
        _usersLiveData.value = ViewState.loading()

        disposable.add(
            useCase.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ users ->
                    _usersLiveData.value = ViewState.success(users)
                }, {
                    _usersLiveData.value = ViewState.error()
                })
        )
    }
}